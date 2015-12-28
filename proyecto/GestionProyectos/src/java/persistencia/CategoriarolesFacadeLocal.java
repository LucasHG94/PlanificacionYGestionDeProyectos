/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Categoriaroles;

/**
 *
 * @author sturm
 */
@Local
public interface CategoriarolesFacadeLocal {

    void create(Categoriaroles categoriaroles);

    void edit(Categoriaroles categoriaroles);

    void remove(Categoriaroles categoriaroles);

    Categoriaroles find(Object id);

    List<Categoriaroles> findAll();

    List<Categoriaroles> findRange(int[] range);

    int count();
    
}
