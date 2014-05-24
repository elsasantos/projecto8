/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.facades;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.aor.edcodisrt.entities.Snapshot;
import pt.uc.aor.edcodisrt.entities.UserApp;

/**
 *
 * @author Elsa
 */
@Stateless
public class SnapshotFacade extends AbstractFacade<Snapshot> {

    @PersistenceContext(unitName = "projecto8")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SnapshotFacade() {
        super(Snapshot.class);
    }

    public List<Snapshot> userSnapshot(UserApp user) {
        try {
            List<Snapshot> s = em.createNamedQuery("Snapshot.findByUser").setParameter("userApp", user).getResultList();
            return s;
        } catch (NullPointerException | IllegalStateException ex) {
            Logger.getLogger(SnapshotFacade.class.getName()).log(Level.SEVERE, null, ex);
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
