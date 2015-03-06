/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.bz.enums;

/**
 *
 * @author naumenko_ds
 */
public enum ArticleType {
    DEF("Заметка с файлами"),
    TEXT_ONLY("только текст");// например для паролей
    
    private String name;

    public String getName() {
        return this.name;
    }    
    
    private ArticleType(String name){
        this.name = name;
    }
}
