/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.Etapa;

/**
 *
 * @author sturm
 */
@Stateless
public class EtapaFacade extends AbstractFacade<Etapa> implements EtapaFacadeLocal {
    @PersistenceContext(unitName = "GestionProyectosPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapaFacade() {
        super(Etapa.class);
    }
    
}
