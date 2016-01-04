/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Categoriaroles;

/**
 *
 * @author sturm
 */
@Stateless
public class CategoriarolesFacade extends AbstractFacade<Categoriaroles> implements CategoriarolesFacadeLocal {
    @PersistenceContext(unitName = "GestionProyectosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriarolesFacade() {
        super(Categoriaroles.class);
    }
    
}
