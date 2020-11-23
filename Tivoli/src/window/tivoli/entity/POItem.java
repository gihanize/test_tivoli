/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sashenka
 */
@Entity
@Table(name="poitem")
@NamedQueries({
    @NamedQuery(name = "POByCompany", query = "SELECT p FROM POItem p WHERE p.companyId = :company") // u is a prefix for identify the user entitity class | 'uname' kiyala ekak pass karanawa eka usernameta equalda balanawa
})
public class POItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Integer id;
    private Integer quantity;
    private Integer tobeMan;
    private Integer manufactured;
    private  BigDecimal lineTotal;
    private transient BigDecimal unitPrice;
    private transient Integer unitWeight;
    private  Integer lineWeight;
    private  String itemDesc;
    
    
    @ManyToOne
    @JoinColumn (name="purchaseorder_id", nullable = true)
    private PurchaseOrder purchaseorderId;

    public PurchaseOrder getPurchaseorderId() {
        return purchaseorderId;
    }

    public void setPurchaseorderId(PurchaseOrder purchaseorderId) {
        this.purchaseorderId = purchaseorderId;
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
    
    @OneToOne
    @JoinColumn(name = "onprogress_id", nullable = true)
    private  OnProgress onprogressId;

    public OnProgress getOnprogressId() {
        return onprogressId;
    }

    public void setOnprogressId(OnProgress onprogressId) {
        this.onprogressId = onprogressId;
    }
    
    
    
    @ManyToOne
    @JoinColumn (name="item_id", nullable = true)
    private Item itemId;

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }
    
    
    

    public POItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTobeMan() {
        return tobeMan;
    }

    public void setTobeMan(Integer tobeMan) {
        this.tobeMan = tobeMan;
    }

    public Integer getManufactured() {
        return manufactured;
    }

    public void setManufactured(Integer manufactured) {
        this.manufactured = manufactured;
    }
    
    

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Integer getLineWeight() {
        return lineWeight;
    }

    public void setLineWeight(Integer lineWeight) {
        this.lineWeight = lineWeight;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Integer unitWeight) {
        this.unitWeight = unitWeight;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    
    
    

    @Override
    public String toString() {
        return "POItem{" + "id=" + id + ", quantity=" + quantity + ", lineTotal=" + lineTotal + ", lineWeight=" + lineWeight + '}';
    }
    
    
    
}
