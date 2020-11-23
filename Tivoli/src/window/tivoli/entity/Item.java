/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Sashenka
 */
@Entity
@Table(name="item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Integer id;
    private String itemCode;
    private String itemDesc;
    private BigDecimal unitPrice;
    private Integer unitWeight;

    public Item() {
    }
    
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<POItem> poitemList;

    public List<POItem> getPoitemList() {
        return poitemList;
    }

    public void setPoitemList(List<POItem> poitemList) {
        this.poitemList = poitemList;
    }
    
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<DispatchNote> disNoteList;

    public List<DispatchNote> getDisNoteList() {
        return disNoteList;
    }

    public void setDisNoteList(List<DispatchNote> disNoteList) {
        this.disNoteList = disNoteList;
    }
    
    
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<OnProgress> onProgressList;
    
    
    
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
    private List<OnProgress> onProgList;

    public List<OnProgress> getOnProgList() {
        return onProgList;
    }

    public void setOnProgList(List<OnProgress> onProgList) {
        this.onProgList = onProgList;
    }
    
    @OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL)
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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
    
    

    @Override
    public String toString() {
        return itemCode;
    }
    
    
    
    
}
