/*
 * DEVALUX CONFIDENTIAL
 *
 * [2012-2013] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.address;

import com.r2r.core.db.SystemConstant;
import com.r2r.core.db.entity.ColoniaLocalidad;
import com.r2r.core.db.entity.DelegacionMunicipio;
import com.r2r.core.db.entity.Estado;
import com.r2r.core.db.entity.common.Status;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Alan Hdez
 */
@Stateless
public class AddressEjb implements Serializable {

    @PersistenceContext(unitName = SystemConstant.PERSISTENCE_NAME)
    private EntityManager entityManager;

    public List<Estado> findActiveEstados() {
        return entityManager.createQuery("SELECT e FROM Estado e WHERE e.estatus=:estatus", Estado.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();
    }

    public List<ColoniaLocalidad> findActiveColoniaLocalidad() {
        return entityManager.createQuery("SELECT c FROM ColoniaLocalidad c WHERE c.estatus=:estatus", ColoniaLocalidad.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();
    }

    public List<ColoniaLocalidad> findActiveColoniaLocalidad(DelegacionMunicipio delegacionMunicipio) {
        return entityManager.createQuery("SELECT c FROM ColoniaLocalidad c WHERE c.estatus=:estatus AND c.delegacionMunicipio=:delegacionMunicipio", ColoniaLocalidad.class)
                .setParameter("estatus", Status.ENABLED)
                .setParameter("delegacionMunicipio", delegacionMunicipio)
                .getResultList();
    }

    public List<DelegacionMunicipio> findActiveDelegacionMunicipio() {
        return entityManager.createQuery("SELECT d FROM DelegacionMunicipio d WHERE d.estatus=:estatus", DelegacionMunicipio.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();
    }

    public List<DelegacionMunicipio> findActiveDelegacionMunicipio(Estado estado) {
        return entityManager.createQuery("SELECT d FROM DelegacionMunicipio d WHERE d.estatus=:estatus AND d.estado=:estado", DelegacionMunicipio.class)
                .setParameter("estatus", Status.ENABLED)
                .setParameter("estado", estado)
                .getResultList();
    }
//
//    public List<DelegacionMunicipio> findActiveTownshipByState(Estado estado) {
//        return entityManager.createNamedQuery(AddressQuery.FIND_ACTIVE_TOWNSHIP_BY_STATE, DelegacionMunicipio.class)
//                .setParameter("estado", estado)
//                .getResultList();
//    }
//
//    public List<ColoniaLocalidad> findActiveColonyBtTownship(DelegacionMunicipio delegacionMunicipio) {
//        return entityManager.createNamedQuery(AddressQuery.FIND_ACTIVE_COLONY_BY_TOWNSHIP, ColoniaLocalidad.class)
//                .setParameter("delegacionMunicipio", delegacionMunicipio)
//                .getResultList();
//    }
//
//    public List<CiudadMunicipio> findActiveCityTownships() {
//        return
//    }
}
