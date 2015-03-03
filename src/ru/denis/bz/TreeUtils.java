/**
 * Класс для обслуживания дерева объектов
 */
package ru.denis.bz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author naumenko_ds
 */
public class TreeUtils {

    public TreeUtils() {
    }
    
    
    public TreeModel createNodes(){
        
        try{
                        
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корневой элемент");
            
            
            fillRazdelName(root);
            
            return new DefaultTreeModel(root);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }
    
    private void fillRazdelName(DefaultMutableTreeNode parent) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ResultSet res = null;
        
        try{
            DataBaseUtils du = DataBaseUtils.getInstance();
        
            con = du.getConnect();
            stmt = con.createStatement() ;
           
            String sqlTxt = "select id, name, parent from razdel where parent ";
            if(parent == null){
                sqlTxt = sqlTxt + " is null";
            }else{
                sqlTxt = sqlTxt + " = " + 2;
            }
            
            res = stmt.executeQuery(sqlTxt);
           
           
           
            while(res.next()){
                DefaultMutableTreeNode rri = new DefaultMutableTreeNode(res.getString("name"));
                
                parent.add(rri);
                
                fillRazdelName(rri);
            }
           
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
    }
    
}
