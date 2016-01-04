/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Dedicacion;

/**
 *
 * @author sturm
 */
@Local
public interface DedicacionFacadeLocal {

    void create(Dedicacion dedicacion);

    void edit(Dedicacion dedicacion);

    void remove(Dedicacion dedicacion);

    Dedicacion find(Object id);

    List<Dedicacion> findAll();

    List<Dedicacion> findRange(int[] range);

    int count();
    
}
