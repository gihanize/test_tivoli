/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sashenka
 */
@Entity
@Table(name = "dispatch")
@NamedQueries({
    @NamedQuery(name = "DispatchByCompany", query = "SELECT d FROM Dispatch d WHERE d.companyId = :company") // u is a prefix for identify the user entitity class | 'uname' kiyala ekak pass karanawa eka usernameta equalda balanawa
})
public class Dispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String itemDesc;
    private Integer quantity;
    private Date date;
    private BigDecimal price;

    public Dispatch() {
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
    @JoinColumn(name = "item_id", nullable = true)
    private Item itemId;

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
}
