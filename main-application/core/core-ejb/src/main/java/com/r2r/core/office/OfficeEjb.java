/*
 * DEVALUX CONFIDENTIAL
 *
 * [2014-2015] Devalux - All Rights Reserved.
 * ATTENTION: This file is subject to the terms and conditions defined
 * in file 'LICENSE.txt', which is part of this source code package.
 */
package com.r2r.core.office;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.navegacion.Oficina;
import com.r2r.core.db.exception.DatabaseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Singleton
public class OfficeEjb extends EntityDaoEjb {

    private Map<Integer, Oficina> oficinasMap;

    private List<Oficina> oficinas;

    @PostConstruct
    public void init() {
        reloadOffices();
    }

    public void disableOffice(Integer idOffice) throws DatabaseException {
        /*UsuarioInfo usuario = entityManager.getReference(UsuarioInfo.class, idUsuario);
         usuario.getUsuario().setEstatus(Status.DISABLED);
         entityManager.merge(usuario);*/
    }

    public void disableOffice(Integer idOffice, Usuario user) throws DatabaseException {

        UModif uModif = new UModif();
        uModif.setFechaUModif(LocalDateTime.now());
        uModif.setUsuarioUModif(user);
        Oficina oficina = entityManager.getReference(Oficina.class, idOffice);
        oficina.setUModif(uModif);
        oficina.setEstatus(Status.DISABLED);
        entityManager.merge(oficina);
    }

    public void enableOffice(Integer idOffice, Usuario user) throws DatabaseException {
        UModif uModif = new UModif();
        uModif.setFechaUModif(LocalDateTime.now());
        uModif.setUsuarioUModif(user);
        Oficina oficina = entityManager.getReference(Oficina.class, idOffice);
        oficina.setUModif(uModif);
        oficina.setEstatus(Status.ENABLED);
        entityManager.merge(oficina);
    }

    public List<Oficina> getOficinas() {
        return oficinas;
    }

    public List<Oficina> getOfficeFather(Oficina oficina) {
        if (oficina.getId() == null) {
            return entityManager.createQuery("SELECT o FROM Oficina o WHERE o.estatus=:status", Oficina.class)
                    .setParameter("status", Status.ENABLED)
                    .getResultList();
        } else {
            return entityManager.createQuery("SELECT o FROM Oficina o WHERE o.estatus=:status AND o !=:oficina", Oficina.class)
                    .setParameter("status", Status.ENABLED)
                    .setParameter("oficina", oficina)
                    .getResultList();
        }
    }

    public List<Usuario> getListUsuarios() {
        return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.estatus=:estatus", Usuario.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();
    }

    public Oficina getOficina(Integer id) {
        return oficinasMap.get(id);
    }

    @Lock(LockType.WRITE)
    public void reloadOffices() {
        oficinas = entityManager.createQuery("SELECT NEW com.r2r.core.db.entity.navegacion.Oficina(s.id, s.nombre, s.abreviatura, jo, d) FROM Oficina s JOIN FETCH s.direccion d JOIN FETCH d.delegacionMunicipio dm JOIN FETCH s.responsableOficina jo LEFT JOIN FETCH dm.ciudadMunicipio cm LEFT JOIN FETCH cm.ciudad WHERE s.estatus = " + Status.ENABLED.getFullPath() + " ORDER BY s.nombre", Oficina.class).getResultList();
        oficinasMap = new LinkedHashMap<>(oficinas.size());
        for (Oficina oficina : oficinas) {
            oficinasMap.put(oficina.getId(), oficina);
        }
    }

    public void saveNewOffice(Oficina oficina) throws DatabaseException {
        oficina.getDireccion().setUModif(oficina.getUModif());
        saveNewEntity(oficina);
        reloadOffices();
    }

    public void editOffice(Oficina office) throws DatabaseException {
        editEntity(office);
        reloadOffices();
    }

    public Oficina getOfficeForEdit(Integer idOffice) {
        return entityManager.createQuery("SELECT s FROM Oficina s JOIN FETCH s.direccion d JOIN FETCH d.delegacionMunicipio JOIN FETCH s.oficinaTipo JOIN FETCH s.oficinaPadre JOIN FETCH s.jefeOficina WHERE s.id = :id", Oficina.class)
                .setParameter("id", idOffice)
                .getSingleResult();
    }
}
