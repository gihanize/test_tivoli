/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Sashenka
 */
public class CommonDao {
    
    // saveData() function used to save data in the data base through hibernate
    public static void saveData(Object ob) {

        Session s = NewHibernateUtil.getSessionFactory().openSession(); //create new hibernate session
        Transaction trans = null; //declare the transaction, used null because needs to use in both try and catch blocks

        try {
            trans = s.beginTransaction(); //inetializing transaction through the session
            s.save(ob); //builtin hibernate for insert 
            trans.commit(); //commit transaction

        } catch (HibernateException ex) {
            trans.rollback();
        } finally {
            s.close(); //closing session for security purpose
        }
    }

    
    // updateData() function used to update data in the data base through hibernate
    public static void updateData(Object ob) {
        Session s = NewHibernateUtil.getSessionFactory().openSession(); //create new hibernate session
        Transaction trans = null; //declare the transaction, used null because needs to use in both try and catch blocks

        try {
            trans = s.beginTransaction(); //inetializing transaction through the session
            s.update(ob); //builtin hibernate for insert 
            trans.commit(); //commit transaction

        } catch (HibernateException ex) {
            trans.rollback();
        } finally {
            s.close(); //closing session for security purpose
        }

    }

    // deleteData() function used to delete data in the data base through hibernate
    public static void deleteData(Object ob) {
        Session s = NewHibernateUtil.getSessionFactory().openSession(); //create new hibernate session
        Transaction trans = null; //declare the transaction, used null because needs to use in both try and catch blocks

        try {
            trans = s.beginTransaction(); //inetializing transaction through the session
            s.delete(ob); //builtin hibernate for insert 
            trans.commit(); //commit transaction

        } catch (HibernateException ex) {
            trans.rollback();
        } finally {
            s.close(); //closing session for security purpose
        }
    }

    //save, if saved update
    public static void saveOrUpdateData(Object ob) {
        Session s = NewHibernateUtil.getSessionFactory().openSession(); //create new hibernate session
        Transaction trans = null; //declare the transaction, used null because needs to use in both try and catch blocks

        try {
            trans = s.beginTransaction(); //inetializing transaction through the session
            s.saveOrUpdate(ob); //builtin hibernate for insert 
            trans.commit(); //commit transaction

        } catch (HibernateException ex) {
            trans.rollback();
        } finally {
            s.close(); //closing session for security purpose
        }
    }
}
