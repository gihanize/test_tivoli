/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sashenka
 */
@Entity
@Table(name = "onprogress")
public class OnProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String itemDesc;
    private Integer quantity;
    private Date date;

    public OnProgress() {
    }

    @ManyToOne
    @JoinColumn(name = "purchaseorder_id", nullable = true)
    private PurchaseOrder purchaseorderId;

    public PurchaseOrder getPurchaseorderId() {
        return purchaseorderId;
    }

    public void setPurchaseorderId(PurchaseOrder purchaseorderId) {
        this.purchaseorderId = purchaseorderId;
    }
    
    @OneToOne
    @JoinColumn(name = "poitem_id", nullable = true)
    private POItem poitemId;

    public POItem getPoitemId() {
        return poitemId;
    }

    public void setPoitemId(POItem poitemId) {
        this.poitemId = poitemId;
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
    
    

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = true)
    private Item itemId;

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
