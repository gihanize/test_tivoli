/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window.tivoli.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="vehicle")
public class vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public vehicle() {
    }

    @OneToMany(mappedBy = "vehicleId" , fetch = FetchType.EAGER)
    private List<DispatchNote> disNoteList;

    public List<DispatchNote> getDisNoteList() {
        return disNoteList;
    }

    public void setDisNoteList(List<DispatchNote> disNoteList) {
        this.disNoteList = disNoteList;
    }
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
