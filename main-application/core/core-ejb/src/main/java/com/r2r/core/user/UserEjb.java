package com.r2r.core.user;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.UsuarioInfo;
import com.r2r.core.db.entity.UsuarioPuesto;
import com.r2r.core.db.entity.UsuarioTitulo;
import com.r2r.core.db.entity.common.Status;
import com.r2r.core.db.entity.common.UModif;
import com.r2r.core.db.exception.DatabaseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.joda.time.LocalDateTime;

/**
 *
 * @author
 */
@Stateless
public class UserEjb extends EntityDaoEjb {

    public List<Usuario> getUsuariosActivos() {
        return entityManager.createQuery("SELECT NEW com.r2r.core.db.entity.Usuario(s.id, s.nombre, s.APaterno, s.AMaterno, s.password) FROM Usuario s WHERE s.estatus = :estatus", Usuario.class)
                .setParameter("estatus", Status.ENABLED)
                .getResultList();
    }

    public void disableUsuario(Integer idUsuario) throws DatabaseException {
        UsuarioInfo usuario = entityManager.getReference(UsuarioInfo.class, idUsuario);
        usuario.getUsuario().setEstatus(Status.DISABLED);
        entityManager.merge(usuario);
    }

    public void disableUsuario(Integer idUsuario, Usuario user) throws DatabaseException {
        UsuarioInfo usuario = entityManager.getReference(UsuarioInfo.class, idUsuario);
        usuario.getUsuario().setEstatus(Status.DISABLED);
        usuario.setUModif(new UModif(user));
        entityManager.merge(usuario);
    }

    public void enableUsuario(Integer idUsuario, Usuario user) throws DatabaseException {
        UsuarioInfo usuario = entityManager.getReference(UsuarioInfo.class, idUsuario);
        usuario.getUsuario().setEstatus(Status.ENABLED);
        usuario.setUModif(new UModif(user));
        entityManager.merge(usuario);
    }

    public void saveNewUsuario(UsuarioInfo usuario, Usuario user) throws DatabaseException {
        usuario.setUModif(new UModif(user));
        saveNewEntity(usuario.getUsuario());
        saveNewEntity(usuario);
    }

    public void editUsuario(UsuarioInfo usuario) throws DatabaseException {
        editEntity(usuario);
    }

    public void editUsuario(UsuarioInfo usuario, Usuario user) throws DatabaseException {
        usuario.setUModif(new UModif(user));
        editEntity(usuario.getUsuario());
        editEntity(usuario);
    }

    public UsuarioInfo getUserForEdit(Integer idUser) {
        try {
            return entityManager.createQuery("SELECT s FROM UsuarioInfo s JOIN FETCH s.usuario JOIN FETCH s.usuarioPuesto LEFT JOIN FETCH s.usuarioTitulo WHERE s.id = :id", UsuarioInfo.class)
                    .setParameter("id", idUser)
                    .getSingleResult();
        } catch (NoResultException nr) {
            return null;
        } 
    }

    public void changePasswordUsuario(Integer idUser, String password, Usuario userLastModif) throws DatabaseException {
        UsuarioInfo usuarioTemp = entityManager.getReference(UsuarioInfo.class, idUser);
        usuarioTemp.getUsuario().setPassword(new BasicPasswordEncryptor().encryptPassword(password));
        usuarioTemp.setUModif(new UModif(userLastModif, LocalDateTime.now()));
        entityManager.merge(usuarioTemp);
    }

    public List<UsuarioPuesto> getUserRanks() {
        return entityManager.createQuery("SELECT ur FROM UsuarioPuesto ur WHERE ur.estatus=:status", UsuarioPuesto.class)
                .setParameter("status", Status.ENABLED)
                .getResultList();
    }

    public List<UsuarioTitulo> getUserTitles() {
        return entityManager.createQuery("SELECT ut FROM UsuarioTitulo ut  WHERE ut.estatus=:status", UsuarioTitulo.class)
                .setParameter("status", Status.ENABLED)
                .getResultList();
    }
}
