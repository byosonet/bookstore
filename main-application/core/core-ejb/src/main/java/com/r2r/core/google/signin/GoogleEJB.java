/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.core.google.signin;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.UsuarioInfo;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.UsuarioPerfil;
import com.r2r.core.db.exception.DatabaseException;
import com.r2r.core.startup.CoreDataInitialization;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.joda.time.LocalDateTime;

/**
 *
 * @author Arturo
 */
@Stateless
public class GoogleEJB extends EntityDaoEjb {

    private Usuario usuario;

    @Inject
    private CoreDataInitialization cdi;

    @Inject
    private PuestoEJB puestoEJB;

    public Usuario mergeUser(Usuario usuario) throws DatabaseException {
        try {

            Usuario usuarioTemp = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login =:login", Usuario.class)
                    .setParameter("login", usuario.getLogin()).getSingleResult();
            usuario.setId(usuarioTemp.getId());
            usuario.setEstatus(usuarioTemp.getEstatus());
            usuario.setPassword(new BasicPasswordEncryptor().encryptPassword(usuario.getPassword()));
            editEntity(usuario);
        } catch (NoResultException nre) {
            saveNewEntity(usuario);

            UModif uModif = new UModif();
            uModif.setFechaUModif(LocalDateTime.now());
            uModif.setUsuarioUModif(cdi.getUserAdmin());

            UsuarioPerfil perfil = new UsuarioPerfil();
            perfil.setUsuario(usuario);
            perfil.setPerfil(new Perfil(1));
            saveNewEntity(perfil);

            UsuarioInfo info = new UsuarioInfo();
            info.setUsuarioPuesto(puestoEJB.putGetUsuarioGoogle());
            info.setUModif(uModif);
            info.setUsuario(usuario);
            saveNewEntity(info);
        }
        return usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
