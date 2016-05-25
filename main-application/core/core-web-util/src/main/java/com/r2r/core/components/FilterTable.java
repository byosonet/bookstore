/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.components;

import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.util.Tuple;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author rKm <rekiem87@gmail.com>
 */
@Stateless
public class FilterTable {

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    public <E extends EntityAbstract<?>> Integer getEntitiesCount(QueryHelper<E> queryHelper, Class<E> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<E> r = cq.from(entityClass);
        cq.select(cb.count(r));
        Predicate[] predicates = makePredicates(queryHelper, cb, r);
        if (predicates.length > 0) {
            cq.where(predicates);
        }
        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public <E extends EntityAbstract<?>> List<E> search(int firstResult, int limit, QueryHelper<E> queryHelper, Class<E> entityClass) {
        return buildSearchQuery(queryHelper, entityClass).setFirstResult(firstResult).setMaxResults(limit).getResultList();
    }

    @SuppressWarnings("unchecked")
    private <E extends EntityAbstract<?>> Predicate[] makePredicates(QueryHelper<E> queryManagment, CriteriaBuilder cb, Root r) {
        List<Predicate> listPredicates = new LinkedList<>();
        // Do likes
        for (Tuple<SingularAttribute<? super E, String>, String> tuple : queryManagment.getListLikeString()) {
            listPredicates.add(cb.like(r.get(tuple.getA()), "%" + tuple.getB() + "%"));
        }
        for (Tuple<SingularAttribute<? super E, String>, String> tuple : queryManagment.getListLikeIgnoreCase()) {
            listPredicates.add(cb.like(cb.upper(r.get(tuple.getA())), "%" + tuple.getB().toUpperCase() + "%"));
        }
        // Hacer equals
        for (Tuple<SingularAttribute<? super E, String>, String> tuple : queryManagment.getListEqualString()) {
            listPredicates.add(cb.equal(r.get(tuple.getA()), tuple.getB()));
        }
        for (Tuple<SingularAttribute<? super E, Integer>, Integer> tuple : queryManagment.getListEqualInteger()) {
            listPredicates.add(cb.equal(r.get(tuple.getA()), tuple.getB()));
        }
        for (Tuple<SingularAttribute<? super E, ? extends EntityAbstract>, ? extends EntityAbstract> tuple : queryManagment.getListEqualJoin()) {
            listPredicates.add(cb.equal(r.get(tuple.getA()), tuple.getB()));
        }
        // Hacer not equals
        for (Tuple<SingularAttribute<? super E, ?>, ?> tuple : queryManagment.getListNotEqual()) {
            listPredicates.add(cb.notEqual(r.get(tuple.getA()), tuple.getB()));
        }
        Predicate[] predicates = new Predicate[listPredicates.size()];
        return listPredicates.toArray(predicates);
    }

    private <E extends EntityAbstract<?>> TypedQuery<E> buildSearchQuery(QueryHelper<E> queryHelper, Class<E> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> r = cq.from(entityClass);
        Predicate[] predicates = makePredicates(queryHelper, cb, r);
        if (predicates.length > 0) {
            cq.where(predicates);
        }
        // Agregar orden
        if (!queryHelper.getListOrder().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (Tuple<SingularAttribute<? super E, ?>, Boolean> tuple : queryHelper.getListOrder()) {
                orders.add(tuple.getB()
                        ? cb.asc(r.get(tuple.getA()))
                        : cb.desc(r.get(tuple.getA())));
            }
            cq.orderBy(orders);
        }
        if (queryHelper.getListJoinFields() != null) {
            for (SingularAttribute<? super E, ?> attribute : queryHelper.getListJoinFields()) {
                r.fetch(attribute);
            }
        }
        if (queryHelper.getListCollectionJoinFields() != null) {
            for (CollectionAttribute<? super E, ?> attribute : queryHelper.getListCollectionJoinFields()) {
                r.fetch(attribute);
            }
        }
        return entityManager.createQuery(cq);
    }
}
