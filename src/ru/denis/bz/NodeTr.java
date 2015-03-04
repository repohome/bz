/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.bz;

/**
 *
 * @author naumenko_ds
 */
public class NodeTr {
    
    private String name;
    private Integer id;
    private boolean isLeaf;
    
    public NodeTr(String name, Integer id, boolean isLeaf) {
        this.name = name;
        this.id = id;
        this.isLeaf = isLeaf;
    }

    public Integer getId() {
        return id;
    }

    public boolean getIsLeaf(){
        return isLeaf;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
    
    
    
    
}
