package ru.denis.bz.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ru.denis.bz.ui.tree.FindField;
import ru.denis.bz.ui.tree.TreeRazdel;
import ru.denis.bz.utils.BoxLayoutUtils;
import ru.denis.bz.utils.GUITools;



/**
 * Основное окно приложения.
 * @author naumenko_ds
 */
public class MainWindow extends javax.swing.JFrame {
    
    public MainWindow() {
        initComponents();
        setSize(300, 800);
        setTitle("Заметки по работе");
        
        // по середине экрана
        java.awt.Dimension dim = getToolkit().getScreenSize();
        this.setLocation(dim.width/2 - this.getWidth()/2,
                dim.height/2 - this.getHeight()/2);
    }

    private javax.swing.JTree treeRazdel;
    private javax.swing.JTextField findField;
    private javax.swing.JButton jBFind;
    private javax.swing.JButton jBClear;
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel main = BoxLayoutUtils.createVerticalPanel();
        //main.setBorder( BorderFactory.createEmptyBorder(12,12,12,12)); 
        
        JPanel findPan = BoxLayoutUtils.createHorizontalPanel(); 
        JScrollPane treePan = new JScrollPane();
        
        
        
        
        treeRazdel = new TreeRazdel();
        treeRazdel.setSize(500, 500);
        //treeRazdel.setMaximumSize(new java.awt.Dimension(1200, 1200));
        
        
        findField = new FindField(treeRazdel);
        
        jBFind = new javax.swing.JButton();
        jBFind.setText("Фильтр");
        
        jBClear = new javax.swing.JButton();
        jBClear.setText("Очистить");
        
        
        
        treePan.setPreferredSize(new java.awt.Dimension(500, 500));       
        treePan.setViewportView(treeRazdel);
        
        
        findPan.add(findField);
        findPan.add(jBClear);
        
        
        treePan.add(treeRazdel);
        
        
        
        main.add(findPan);
        main.add(treePan);
        
        
        BoxLayoutUtils.setGroupAlignmentX( 
            new JComponent[] {findPan, treePan }, Component.LEFT_ALIGNMENT); 
            // б) центральное выравнивание надписей и текстовых полей
        BoxLayoutUtils.setGroupAlignmentY( 
            new JComponent[] { findField }, 
            Component.CENTER_ALIGNMENT); 
            // в) одинаковые размеры надписей к текстовым полям
            //GUITools.makeSameSize(new JComponent[] { nameLabel, passwrdLabel } ); 
            // г) стандартный вид для кнопок
        GUITools.createRecommendedMargin(new JButton[] { jBClear/*ok, cancel*/ } ); 
            // д) устранение "бесконечной" высоты текстовых полей
        GUITools.fixTextFieldSize(findField); 
        //GUITools.fixTreeFieldSize(treeRazdel); 
        
        
        
        getContentPane().add(main);
        pack();
    }
}
