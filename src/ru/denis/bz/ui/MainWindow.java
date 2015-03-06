package ru.denis.bz.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ru.denis.bz.ui.tree.FindField;
import ru.denis.bz.ui.tree.TreeRazdel;

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
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JPanel contents = new JPanel(); 
        
        treeRazdel = new TreeRazdel();
        findField = new FindField(treeRazdel);
        
        contents.add(findField);
        contents.add(treeRazdel);
        
        // добавляем компонент на панель
        getContentPane().add(contents);
        //getContentPane().add(new JScrollPane(treeRazdel));
        
       
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 400, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 300, Short.MAX_VALUE)
//        );
//
//        pack();
    }
}
