/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.facades;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.aor.edcodisrt.entities.UserApp;

/**
 *
 * @author Elsa
 */
@Stateless
public class UserAppFacade extends AbstractFacade<UserApp> {

    @PersistenceContext(unitName = "projecto8")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAppFacade() {
        super(UserApp.class);
    }

    public UserApp findbyName(String name) {
        System.out.println("entrou no método");
        try {
            UserApp userlogado = (UserApp) em.createNamedQuery("UserApp.findByName").setParameter("name", name).getSingleResult();
            System.out.println("user encontrado na entitie: " + userlogado);
            return userlogado;
        } catch (NullPointerException | IllegalStateException ex) {
            Logger.getLogger(UserAppFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
