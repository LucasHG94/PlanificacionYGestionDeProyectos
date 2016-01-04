/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Informesemanal;

/**
 *
 * @author sturm
 */
@Local
public interface InformesemanalFacadeLocal {

    void create(Informesemanal informesemanal);

    void edit(Informesemanal informesemanal);

    void remove(Informesemanal informesemanal);

    Informesemanal find(Object id);

    List<Informesemanal> findAll();

    List<Informesemanal> findRange(int[] range);

    int count();
    
}
