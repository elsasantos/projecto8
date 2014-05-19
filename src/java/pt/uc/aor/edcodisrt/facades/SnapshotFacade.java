/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.aor.edcodisrt.entities.Snapshot;

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

}
