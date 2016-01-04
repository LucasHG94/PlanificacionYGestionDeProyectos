/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Datosconfigurables;

/**
 *
 * @author sturm
 */
@Local
public interface DatosconfigurablesFacadeLocal {

    void create(Datosconfigurables datosconfigurables);

    void edit(Datosconfigurables datosconfigurables);

    void remove(Datosconfigurables datosconfigurables);

    Datosconfigurables find(Object id);

    List<Datosconfigurables> findAll();

    List<Datosconfigurables> findRange(int[] range);

    int count();
    
}
