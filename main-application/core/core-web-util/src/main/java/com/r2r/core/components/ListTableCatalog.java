/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.components;

import com.r2r.core.db.QueryHelper;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.CatalogAbstract_;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.util.Tuple;
import java.util.HashMap;
import javax.persistence.metamodel.SingularAttribute;
import org.primefaces.model.SortOrder;

/**
 *
 * @author rKm <rekiem87@gmail.com>
 * @param <ID>
 * @param <C>
 */
public abstract class ListTableCatalog<ID, C extends CatalogAbstract<ID>> extends ListTableBase<C> {

    protected LazyCatalogModel lazyCatalogModel;

    private Status estatus;

    private ID id;

    public ListTableCatalog() {
        super(null);
    }

    public ListTableCatalog(Class<C> entityClass) {
        super(entityClass);
    }

    @Override
    public LazyCatalogModel getLazyModel() {
        return lazyCatalogModel;
    }

    public Status getEstatus() {
        return estatus;
    }

    public void setEstatus(Status estatus) {
        this.estatus = estatus;
    }

    @Override
    protected QueryHelper<C> createQueryHelper() {
        QueryHelper<C> queryHelper = new QueryHelper<>();
        if (estatus != null) {
            queryHelper.addEqual(CatalogAbstract_.estatus, estatus);
        }
        return queryHelper;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public class LazyCatalogModel extends LazyBasicModel {

        public LazyCatalogModel() {
            this(new HashMap<String, Tuple<SingularAttribute<? super C, String>, String>>(1), new HashMap<String, Tuple<SingularAttribute<? super C, ?>, SortOrder>>(1));
        }

        public LazyCatalogModel(HashMap<String, Tuple<SingularAttribute<? super C, String>, String>> stringFiltersMap, HashMap<String, Tuple<SingularAttribute<? super C, ?>, SortOrder>> ordersMap) {
            super(stringFiltersMap, ordersMap);
            createOrder("estatus", CatalogAbstract_.estatus);
        }
    }
}
