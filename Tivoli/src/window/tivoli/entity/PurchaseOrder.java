/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Sashenka
 */
@Entity
@Table(name="purchaseorder")


public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Integer id;
    private String poNumber;
    private Date date;
    private BigDecimal totalPrice; 
    private Integer totalWeight;

    public PurchaseOrder() {
    }
    
    @ManyToOne
    @JoinColumn (name = "company_id", nullable = true)
    private Company companyId; 

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }
    
    @OneToMany(mappedBy = "purchaseorderId", cascade = CascadeType.ALL)
    private List<POItem> poitemList;

    public List<POItem> getPoitemList() {
        return poitemList;
    }

    public void setPoitemList(List<POItem> poitemList) {
        this.poitemList = poitemList;
    }
    
    @OneToMany(mappedBy = "purchaseorderId", cascade = CascadeType.ALL)
    private List<DispatchNote> disNoteList;

    public List<DispatchNote> getDisNoteList() {
        return disNoteList;
    }

    public void setDisNoteList(List<DispatchNote> disNoteList) {
        this.disNoteList = disNoteList;
    }
    
    
    
    @OneToMany(mappedBy = "purchaseorderId", cascade = CascadeType.ALL)
    private List<OnProgress> onProgList;

    public List<OnProgress> getOnProgList() {
        return onProgList;
    }

    public void setOnProgList(List<OnProgress> onProgList) {
        this.onProgList = onProgList;
    }
    
    @OneToMany(mappedBy = "purchaseorderId", cascade = CascadeType.ALL)
    private List<Dispatch> dispatchList;

    public List<Dispatch> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(List<Dispatch> dispatchList) {
        this.dispatchList = dispatchList;
    }
    
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }
    
    

    @Override
    public String toString() {
        return poNumber;
    }
    
    
    
    
}
