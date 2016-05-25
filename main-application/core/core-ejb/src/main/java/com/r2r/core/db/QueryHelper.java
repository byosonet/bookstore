/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.db;

import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.util.Tuple;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Helps to build queries without the necessity of the {@link EntityManager}.
 * <p>
 * It's used outside {@link Stateless} beans to prepare the query and
 * encapsulate all the info.</p>
 * <p>
 * Some examples of the usage can be found in the class {@link ListTableBase},
 * where the {@code QueryHelper} is build, then is passed to {@link FilterTable}
 * where the object is read and the {@link TypedQuery} is created, avoiding
 * unsafe casts.</p>
 *
 * @author rKm <rekiem87@gmail.com>
 * @param <E> Root class of the query.
 */
public class QueryHelper<E extends EntityAbstract> {

    private final List<Tuple<SingularAttribute<? super E, ?>, ?>> listNotEqual = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, ?>, ?>> getListNotEqual() {
        return listNotEqual;
    }

    @SuppressWarnings("unchecked")
    public void addNotEqual(SingularAttribute<? super E, ?> attribute, Object value) {
        listNotEqual.add(new Tuple(attribute, value));
    }

    private final List<Tuple<SingularAttribute<? super E, String>, String>> listLikeString = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, String>, String>> getListLikeString() {
        return listLikeString;
    }

    @SuppressWarnings("unchecked")
    public void addLike(SingularAttribute<? super E, String> attribute, String value) {
        listLikeString.add(new Tuple(attribute, value));
    }

    private final List<Tuple<SingularAttribute<? super E, String>, String>> listLikeIgnoreCase = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, String>, String>> getListLikeIgnoreCase() {
        return listLikeIgnoreCase;
    }

    @SuppressWarnings("unchecked")
    public void addLikeIgnoreCase(SingularAttribute<? super E, String> attribute, String value) {
        listLikeIgnoreCase.add(new Tuple(attribute, value));
    }

    private final List<Tuple<SingularAttribute<? super E, String>, String>> listEqualString = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, String>, String>> getListEqualString() {
        return listEqualString;
    }

    private final List<Tuple<SingularAttribute<? super E, Integer>, Integer>> listEqualInteger = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, Integer>, Integer>> getListEqualInteger() {
        return listEqualInteger;
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, Integer> attribute, Integer value) {
        listEqualInteger.add(new Tuple(attribute, value));
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, Long> attribute, Long value) {
        listEqualInteger.add(new Tuple(attribute, value));
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, Short> attribute, Short value) {
        listEqualInteger.add(new Tuple(attribute, value));
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, String> attribute, String value) {
        listEqualString.add(new Tuple(attribute, value));
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, ? extends Enum> attribute, Enum value) {
        listEqualString.add(new Tuple(attribute, value));
    }

    private List<Tuple<SingularAttribute<? super E, ?>, Boolean>> listOrder = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, ?>, Boolean>> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Tuple<SingularAttribute<? super E, ?>, Boolean>> listOrder) {
        this.listOrder = listOrder;
    }

    @SuppressWarnings("unchecked")
    public void addOrder(SingularAttribute<? super E, ?> attribute, boolean asc) {
        listOrder.add(new Tuple(attribute, asc));
    }

    private final List<Tuple<SingularAttribute<? super E, ? extends EntityAbstract>, ? extends EntityAbstract>> listEqualJoin = new LinkedList<>();

    public List<Tuple<SingularAttribute<? super E, ? extends EntityAbstract>, ? extends EntityAbstract>> getListEqualJoin() {
        return listEqualJoin;
    }

    @SuppressWarnings("unchecked")
    public void addEqual(SingularAttribute<? super E, ? extends EntityAbstract> attribute, Object value) {
        listEqualJoin.add(new Tuple(attribute, value));
    }

    private final List<SingularAttribute<? super E, ? extends EntityAbstract>> listJoinFields = new LinkedList<>();

    public List<SingularAttribute<? super E, ? extends EntityAbstract>> getListJoinFields() {
        return listJoinFields;
    }

    private final List<CollectionAttribute<? super E, ? extends EntityAbstract>> listCollectionJoinFields = new LinkedList<>();

    public List<CollectionAttribute<? super E, ? extends EntityAbstract>> getListCollectionJoinFields() {
        return listCollectionJoinFields;
    }

    public void addJoin(SingularAttribute<? super E, ? extends EntityAbstract> attribute) {
        listJoinFields.add(attribute);
    }

    public void addJoin(List<SingularAttribute<? super E, ? extends EntityAbstract>> listAttributes) {
        listJoinFields.addAll(listAttributes);
    }

    public void addJoin(CollectionAttribute<? super E, ? extends EntityAbstract> attribute) {
        listCollectionJoinFields.add(attribute);
    }
}
