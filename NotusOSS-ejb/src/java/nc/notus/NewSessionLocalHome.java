/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nc.notus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 *
 * @author Igor
 */
public interface NewSessionLocalHome extends EJBLocalHome {
    
    nc.notus.NewSessionLocal create()  throws CreateException;

}
