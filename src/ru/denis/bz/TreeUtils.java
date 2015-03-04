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
                        
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(new NodeTr("Корневой элемент", 0, true));
            
            
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
           
            Integer idParent = ((NodeTr)parent.getUserObject()).getId();
            
            String sqlTxt = "select id, name, parent from razdel where parent ";
            if(idParent == 0){
                sqlTxt = sqlTxt + " is null";
            }else{
                sqlTxt = sqlTxt + " = " + idParent;
            }
            
            res = stmt.executeQuery(sqlTxt);
           
           
           
            while(res.next()){
                NodeTr obji = new NodeTr(res.getString("name"), res.getInt("id"), true);
                DefaultMutableTreeNode rri = new DefaultMutableTreeNode(obji);
                
                parent.add(rri);
                
                fillRazdelName(rri);
            }
           
        }catch(Exception e){
            e.printStackTrace();
            
        }
        
    }
    
}
