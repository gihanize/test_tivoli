/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.dao;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import window.tivoli.entity.Item;


/**
 *
 * @author Sashenka
 */
public class ItemDao {
    public static ObservableList<Item> getItems(){ // <Buyer> get the List objects as Buyer objects (Entity/model convertion)
     
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        
        Transaction trans = null;
        
        try {
            trans = s.beginTransaction();
            String hql = "from Item"; //Creating the query. Buyer = entity class name not the table name 
            Query q = s.createQuery(hql); //Execute the query  
            List<Item> lst = q.list(); //Add data into a list. Use list for multiple data. If single data, use uniqueResult()
            
            itemList = FXCollections.observableArrayList(lst); //parse the list into the observableList(buyerList)
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {s.close();}
        
        return itemList;
        
    }
}
