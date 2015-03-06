
package ru.denis.bz.ui.contener;

import ru.denis.bz.enums.ArticleType;

/**
 * Контейнер для хранения элемента статья.
 * @author naumenko_ds
 */
public class NodeArticle {
    
    private String name;
    private Integer id;
    private boolean isLeaf = true;
    private Integer sortNumber;
    private ArticleType articleType;
    private String note;

    public NodeArticle(String name, Integer id, Integer sortNumber, ArticleType articleType, String note) {
        this.name = name;
        this.id = id;
        this.sortNumber = sortNumber;
        this.articleType = articleType;
        this.note = note;
    }    
    

    public Integer getId() {
        return id;
    }

    public boolean getIsLeaf(){
        return isLeaf;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public String getNote() {
        return note;
    }
    
    
    
    @Override
    public String toString() {
        return name;
    }
    
    
}
