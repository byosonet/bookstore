/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.components;

import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.common.EntityAbstract;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.jsf.JSFConstant;
import com.r2r.core.util.Tuple;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.persistence.metamodel.SingularAttribute;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import static org.primefaces.model.SortOrder.ASCENDING;
import static org.primefaces.model.SortOrder.DESCENDING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 * @param <E>
 */
public abstract class ListTableBase<E extends EntityAbstract> implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ListTableBase.class);

    @EJB
    private FilterTable filterTable;

    private final Class<E> entityClass;

    public abstract LazyBasicModel getLazyModel();

    public abstract void disableInternal() throws DatabaseException;

    public abstract void enableInternal() throws DatabaseException;

    public ListTableBase() {
        this(null);
    }

    public ListTableBase(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    protected QueryHelper<E> createQueryHelper() {
        return new QueryHelper<>();
    }

    public void disableEntity() {
        try {
            disableInternal();
            Messages.addInfo(JSFConstant.MAIN_MESSAGES_GROWL, "core.changesSaved");
        } catch (DatabaseException de) {
            Messages.addError(JSFConstant.MAIN_MESSAGES_GROWL, de.getLocalizedMessage());
        }
    }

    public void enableEntity() {
        try {
            enableInternal();
            Messages.addInfo(JSFConstant.MAIN_MESSAGES_GROWL, "core.changesSaved");
        } catch (DatabaseException de) {
            Messages.addError(JSFConstant.MAIN_MESSAGES_GROWL, de.getLocalizedMessage());
        }
    }

    public class LazyBasicModel extends LazyDataModel<E> {

        private String lastOrderField;

        private HashMap<String, E> mapVaules;

        private boolean filterOutdated;

        protected final HashMap<String, Tuple<SingularAttribute<? super E, String>, String>> stringFiltersMap;

        protected final HashMap<String, Tuple<SingularAttribute<? super E, ?>, SortOrder>> ordersMap;

        public LazyBasicModel(HashMap<String, Tuple<SingularAttribute<? super E, String>, String>> stringFiltersMap, HashMap<String, Tuple<SingularAttribute<? super E, ?>, SortOrder>> ordersMap) {
            this.stringFiltersMap = stringFiltersMap;
            this.ordersMap = ordersMap;
            setRowCount(filterTable.getEntitiesCount(createQueryHelper(), entityClass));
        }

        @Override
        public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            QueryHelper<E> queryHelper = createQueryHelper();
            for (String field : ordersMap.keySet()) {
                if (ordersMap.get(field).getB() != SortOrder.UNSORTED) {
                    queryHelper.addOrder(ordersMap.get(field).getA(), ordersMap.get(field).getB() == SortOrder.ASCENDING);
                }
            }
            for (String filterKey : stringFiltersMap.keySet()) {
                if (stringFiltersMap.get(filterKey).getB() != null) {
                    queryHelper.addLike(stringFiltersMap.get(filterKey).getA(), stringFiltersMap.get(filterKey).getB());
                }
            }
            List<E> listEntities = filterTable.search(first, pageSize, queryHelper, entityClass);
            mapVaules = new HashMap<>(listEntities.size());
            for (E entity : listEntities) {
                mapVaules.put(getRowKey(entity), entity);
            }
            if (filterOutdated) {
                setRowCount(filterTable.getEntitiesCount(createQueryHelper(), entityClass));
                filterOutdated = false;
            }
            return listEntities;
        }

        @Override
        public E getRowData(String rowKey) {
            return mapVaules.get(rowKey);
        }

        @Override
        public String getRowKey(E entity) {
            return entity.getId().toString();
        }

        public String getLastOrderField() {
            return lastOrderField;
        }

        public SortOrder getFieldOrder(String field) {
            try {
                return ordersMap.get(field).getB();
            } catch (NullPointerException exception) {
                LOGGER.error("You need the field: " + field);
                throw new NullPointerException();
            }
        }

        public void updateFieldOrder(String field) {
            if (lastOrderField != null && !lastOrderField.equals(field)) {
                ordersMap.get(lastOrderField).setB(SortOrder.UNSORTED);
            }
            switch (ordersMap.get(field).getB()) {
                case ASCENDING:
                    ordersMap.get(field).setB(SortOrder.DESCENDING);
                    break;
                case DESCENDING:
                    ordersMap.get(field).setB(SortOrder.UNSORTED);
                    break;
                default:
                    ordersMap.get(field).setB(SortOrder.ASCENDING);
            }
            lastOrderField = field;
        }

        public Tuple<SingularAttribute<? super E, String>, String> getFilterTuple(String field) {
            filterOutdated = true;
            return stringFiltersMap.get(field);
        }

        protected void createFilterAndOrder(String field, SingularAttribute<? super E, String> attributeString) {
            stringFiltersMap.put(field, new Tuple<SingularAttribute<? super E, String>, String>(attributeString, null));
            createOrder(field, attributeString);
        }

        protected void createOrder(String field, SingularAttribute<? super E, ?> attributeString) {
            ordersMap.put(field, new Tuple<SingularAttribute<? super E, ?>, SortOrder>(attributeString, SortOrder.UNSORTED));
        }
    }
}
