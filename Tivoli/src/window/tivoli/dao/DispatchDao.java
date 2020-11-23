/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.dao;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import window.tivoli.entity.Company;
import window.tivoli.entity.Dispatch;

/**
 *
 * @author Sashenka
 */
public class DispatchDao {
    public static ObservableList<Dispatch> getDispatch(){ // <Buyer> get the List objects as Buyer objects (Entity/model convertion)
     
        ObservableList<Dispatch> dispatchList = FXCollections.observableArrayList();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        
        Transaction trans = null;
        
        try {
            trans = s.beginTransaction();
            String hql = "from Dispatch"; //Creating the query. Buyer = entity class name not the table name 
            Query q = s.createQuery(hql); //Execute the query  
            List<Dispatch> lst = q.list(); //Add data into a list. Use list for multiple data. If single data, use uniqueResult()
            
            dispatchList = FXCollections.observableArrayList(lst); //parse the list into the observableList(buyerList)
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {s.close();}
        
        return dispatchList;
        
    }
    public static ObservableList<Dispatch> getDispatchByCompany(Company company) { //returns single value

        Dispatch dis = new Dispatch();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        ObservableList<Dispatch> lst = FXCollections.observableArrayList();

        Transaction trans = null;

        try {
            trans = s.beginTransaction();
            Query q = s.getNamedQuery("DispatchByCompany").setEntity("company", company);
            List<Dispatch> list = q.list();
            lst = FXCollections.observableArrayList(list);
        } catch (HibernateException e) {

        } finally {
            s.close();
        }

        return lst;
    }
    
}
