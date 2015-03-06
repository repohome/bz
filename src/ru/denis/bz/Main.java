package ru.denis.bz;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import ru.denis.bz.ui.MainFrame;
import ru.denis.bz.ui.MainWindow;
import ru.denis.bz.utils.AppConstants;

/**
 *
 * @author naumenko_ds
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        DataBaseUtils.getInstance();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainWindow().setVisible(true);                
            }
        });
        
        setTrayIcon();
    }
    
    // помещаем в трей значек
    private static void setTrayIcon() {
        if(! SystemTray.isSupported() ) {
          return;
        }

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem("Выйти");
        item.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
        trayMenu.add(item);

        URL imageURL = Main.class.getResource("images/calendar.png");

        ImageIcon icon = new ImageIcon(imageURL);
        
        
        TrayIcon trayIcon = new TrayIcon(icon.getImage(), AppConstants.APP_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
          tray.add(trayIcon);
        } catch (AWTException e) {
          e.printStackTrace();
        }

        trayIcon.displayMessage(AppConstants.APP_NAME, "Программа запущенна!",
                                TrayIcon.MessageType.INFO);
    }
    
}
