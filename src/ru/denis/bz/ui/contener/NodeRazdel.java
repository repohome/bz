
package ru.denis.bz.ui.contener;

/**
 * Контейнер для хранения элемента раздела.
 * @author naumenko_ds
 */
public class NodeRazdel {
    
    private String name;
    private Integer id;
    private boolean isLeaf = false;
    private String note;
    
    public NodeRazdel(String name, Integer id, String note) {
        this.name = name;
        this.id = id;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public boolean getIsLeaf(){
        return isLeaf;
    }

    public String getNote() {
        return note;
    }
    
    
    
    @Override
    public String toString() {
        return name;
    }
    
    
    
    
    
    
}
