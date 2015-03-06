package ru.denis.bz.ui.tree;

import java.awt.Component;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import ru.denis.bz.DataBaseUtils;
import ru.denis.bz.Main;
import ru.denis.bz.enums.ArticleType;
import ru.denis.bz.ui.contener.NodeArticle;
import ru.denis.bz.ui.contener.NodeRazdel;

import static ru.denis.bz.utils.StringUtils.isEmpty;

/**
 * дерерво для разделов.
 * @author naumenko_ds
 */
public class TreeRazdel extends JTree{

    public TreeRazdel() {
        this.setModel(createTreeModel());
        this.setRootVisible(false);
        //Enable tool tips.
        ToolTipManager.sharedInstance().registerComponent(this);
        
        URL imgURL = Main.class.getResource("images/document.png");
        ImageIcon leafImageArticle = new ImageIcon(imgURL);
        
        URL imgURLPass = Main.class.getResource("images/key.png");
        ImageIcon leafImagePass = new ImageIcon(imgURLPass);
        
        
        if (leafImageArticle != null && leafImagePass != null) {
            this.setCellRenderer(new MyTreeRenderer(leafImageArticle, leafImagePass));
        }else if(leafImageArticle != null && leafImagePass == null){
            this.setCellRenderer(new MyTreeRenderer(leafImageArticle, null));
        }else{
            this.setCellRenderer(new MyTreeRenderer());
        }
        
    
        
    }    
    
    // создание модели дерева
    private TreeModel createTreeModel(){
        try{                        
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(new NodeRazdel("...", 0, null));            
            
            fillRazdelName(root);
            
            return new DefaultTreeModel(root);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }    
    }
    
    
    // Заполняем разделы рекурсивно
    private void fillRazdelName(DefaultMutableTreeNode parent) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        
        try{
            DataBaseUtils du = DataBaseUtils.getInstance();
        
            con = du.getConnect();
            stmt = con.createStatement() ;
           
            Integer idParent = ((NodeRazdel)parent.getUserObject()).getId();
            
            String sqlTxt = "select id, name, parent, note from razdel where parent ";
            if(idParent == 0){
                sqlTxt = sqlTxt + " is null";
            }else{
                sqlTxt = sqlTxt + " = " + idParent;
            }
            
            res = stmt.executeQuery(sqlTxt);
           
           
           
            while(res.next()){
                NodeRazdel obji = new NodeRazdel(res.getString("name"), res.getInt("id"), res.getString("note"));
                DefaultMutableTreeNode rri = new DefaultMutableTreeNode(obji);
                
                parent.add(rri);
                
                // заполняем дочерние разделы
                fillRazdelName(rri);
                
                // Заполняем заметки если есть
                fillArticleName(rri);
            }
           
        }catch(Exception e){
            e.printStackTrace();
            
        }finally{
            stmt.close();
            res.close();
        }
        
    }
   
    // Заполняем для раздела заметки, у заметок вложенности нет
    private void fillArticleName(DefaultMutableTreeNode razdel) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        
        try{
            DataBaseUtils du = DataBaseUtils.getInstance();
        
            con = du.getConnect();
            stmt = con.createStatement() ;
           
            Integer idRazdel = ((NodeRazdel)razdel.getUserObject()).getId();
            
            String sqlTxt = "select id, name, razdel, note, type, sort_number from article where razdel = ";                       
            sqlTxt = sqlTxt + idRazdel;            
            
            res = stmt.executeQuery(sqlTxt);           
           
            while(res.next()){               
                
                NodeArticle obji = new NodeArticle(res.getString("name"), 
                                                   res.getInt("id"), 
                                                   res.getInt("sort_number"),
                                                   ArticleType.values()[(res.getInt("type"))],
                                                   res.getString("note"));
                
                DefaultMutableTreeNode rri = new DefaultMutableTreeNode(obji);
                
                razdel.add(rri);                
            }
           
        }catch(Exception e){
            e.printStackTrace();
            
        }finally{
            stmt.close();
            res.close();
        }
        
    }
    
    
    
    
    /**
     * Класс для отображения узлов дерева в зависимости от типа узла.
     */
    private class MyTreeRenderer extends DefaultTreeCellRenderer{
        
        private ImageIcon imageArticle = null;
        private ImageIcon imagePass = null;
        
        public MyTreeRenderer() {
            
        }       
        
        public MyTreeRenderer(ImageIcon imageArticle, ImageIcon imagePass) {
            if(imageArticle != null){
                this.imageArticle = imageArticle;
            }
            
            if(imagePass != null){
                this.imagePass = imagePass;
            }
        }        
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

            // проверяем, что используется стандартная модель
            if (!(value instanceof DefaultMutableTreeNode)) {
                // если нет, то используем стандартный объект
                return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            }

            Object data = ((DefaultMutableTreeNode)value).getUserObject();
            
            
            if(data instanceof NodeRazdel){// Если раздел              
                
                super.getTreeCellRendererComponent(tree, value, selected, true, false, row, hasFocus);                
                
                NodeRazdel nr = (NodeRazdel)data;
                
                String tip = (nr).getNote();
                
                if(isEmpty(tip)){
                    setToolTipText(null);
                }else{
                    setToolTipText(tip);
                }               
                
                return this;
            }else if(data instanceof NodeArticle){// если заметка
                super.getTreeCellRendererComponent(tree, value, selected, expanded, true, row, hasFocus);                                
                
                NodeArticle nar = (NodeArticle)data;                
                
                String tip = nar.getNote();
                
                if(isEmpty(tip)){
                    setToolTipText(null);
                }else{
                    setToolTipText(tip);
                }                
                
                if(nar.getArticleType().equals(ArticleType.DEF)){
                    this.setIcon(imageArticle);
                }else if(nar.getArticleType().equals(ArticleType.TEXT_ONLY)){
                    this.setIcon(imagePass);
                }                
                
                return this;
                
            }            
                        
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }
    }
}
