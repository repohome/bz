
package ru.denis.bz.ui.tree;

import javax.swing.JTree;

/**
 * Строка для поиска раздела или статьи в дереве.
 * @author Denis
 */
public class FindField extends javax.swing.JTextField{

    public FindField(JTree treeFind) {
        this.treeFind = treeFind;
        
        // инициализируем параметры при запуске
        initComponent();
    }
    
    private JTree treeFind;
    
    private void initComponent(){
        this.setText("Введите текст для поиска.");
    }
    
    
    
}
