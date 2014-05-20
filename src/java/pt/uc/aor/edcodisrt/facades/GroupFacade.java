/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.aor.edcodisrt.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.uc.aor.edcodisrt.entities.Groups;

/**
 *
 * @author Elsa
 */
@Stateless
public class GroupFacade extends AbstractFacade<Groups> {
    @PersistenceContext(unitName = "projecto8")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupFacade() {
        super(Groups.class);
    }

}
