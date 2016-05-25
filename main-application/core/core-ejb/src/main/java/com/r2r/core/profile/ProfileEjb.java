package com.r2r.core.profile;

import com.r2r.core.common.EntityDaoEjb;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.navegacion.Menu;
import com.r2r.core.db.entity.permisos.Perfil;
import com.r2r.core.db.entity.permisos.PerfilMenu;
import com.r2r.core.db.exception.DatabaseException;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Alan Arturo Hernandez Chichitz <alanarturohernandez@gmail.com>
 */
@Stateless
public class ProfileEjb extends EntityDaoEjb {

    public List<Menu> getPerfilMenu(Perfil perfil) {
        return entityManager.createQuery("SELECT m.menu FROM PerfilMenu m WHERE m.perfil = :perfil", Menu.class)
                .setParameter("perfil", perfil)
                .getResultList();
    }

    public List<Menu> getPerfilMenu(Integer id) {
        return entityManager.createQuery("SELECT m.menu FROM PerfilMenu m WHERE m.perfil.id = :id", Menu.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void removeMenus(Perfil perfil) {
        entityManager.createQuery("DELETE FROM PerfilMenu m WHERE m.perfil = :perfil", Menu.class)
                .setParameter("perfil", perfil)
                .executeUpdate();
    }

    public void saveProfile(Perfil perfil, Usuario usuario) throws DatabaseException {
        removeMenus(perfil);
        saveNewEntity(perfil, usuario);
    }

    public void editProfile(Perfil perfil, Usuario usuario) throws DatabaseException {
        removeMenus(perfil);
        for (PerfilMenu perfilMenu : perfil.getMenus()) {
            saveNewEntity(perfilMenu);
        }
        editEntity(perfil, usuario);
    }
}
