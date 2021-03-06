
package ru.denis.bz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для обслуживания соединений с базой данных
 * @author Denis
 */
public class DataBaseUtils {
    
    private static DataBaseUtils  instance;
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private String url = "";
    private Connection con = null;
    
    /**
     * Конструктор
     * @throws java.lang.Exception
     */
    public DataBaseUtils() throws Exception {
        
        
        try{
            // получаем каталог из которого запущенна программа 
            String pathBase = "c:/dataBaseJ/bz";
            this.url = "jdbc:derby:"+ pathBase +";create=true" ;
            
            // получаем конект
            getConnect();            
            
            // может быть какая то первоначальная инициализация
            ctRazdel();
            
            // создание таблицы с заметками
            ctArticles();
            
            
        }catch(Exception e){
            throw new Exception("Ошибка при открытии программы. " + e.getMessage());
        }
        
    }
    
    
    
    
    public static synchronized DataBaseUtils getInstance() throws Exception{
        if(instance == null){
            instance = new DataBaseUtils();
        }
        
        return instance;
    }
    
    public final synchronized Connection getConnect() throws Exception{
        if(this.con == null){
            Class.forName(driver) ;
            this.con = DriverManager.getConnection(url);
        }
        
        return this.con;
    }
    
    public void closeConnect(){
        try {
            Connection conL = getConnect( );
            
            conL.close();
        } catch (Exception ex) {
            Logger.getLogger(DataBaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    private void ctRazdel() throws Exception{
        Connection conL = null;
        String sql = "create table razdel (id int not null, name varchar(50) not null, note varchar(255), parent int, "+
                    " CONSTRAINT PK_RAZDEL PRIMARY KEY ( ID ) ) ";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'RAZDEL'";
        
        Statement stmt = null;
        ResultSet res = null;
        
        try{            
            conL = getConnect();
            
           stmt = conL.createStatement() ;
           
           res = stmt.executeQuery(checkSql);
           
           
           int cnt = 0;
           if(res.next()){
               cnt = Integer.parseInt(res.getString("cnt"));
           }           
            
           if(cnt < 1){
                stmt.executeUpdate(sql);
                conL.commit();
                
//                stmt.executeUpdate(sqlPK);
//                conL.commit();
                
                // заполнение разделов по умолчанию
                fillRazdel("insert into razdel(id, name, note, parent) values (1, 'Заметки программиста', 'Создан автоматически', null)");
                fillRazdel("insert into razdel(id, name, note, parent) values (2, 'ORACLE DB', 'Создан автоматически', 1)");
                fillRazdel("insert into razdel(id, name, note, parent) values (3, 'ExtJS', 'Создан автоматически', 1)");
                fillRazdel("insert into razdel(id, name, note, parent) values (4, 'LINUX', 'Создан автоматически', 1)");
                fillRazdel("insert into razdel(id, name, note, parent) values (5, 'Postgrees', 'Создан автоматически', 1)");
                fillRazdel("insert into razdel(id, name, note, parent) values (6, 'glassfish', 'Создан автоматически', 1)");
                fillRazdel("insert into razdel(id, name, note, parent) values (7, 'glassfish4.0', 'Создан автоматически', 6)");
                fillRazdel("insert into razdel(id, name, note, parent) values (8, 'Пароли', 'Создан автоматически для паролей', null)");
           } 
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы разделов");
        }finally{
            res.close();
            stmt.close();
        }
    }
    
    private void fillRazdel(String sql) throws Exception{
        Connection conL = null;
        
        try{
            
            conL = getConnect();
            
            Statement stmt = conL.createStatement() ;
            
            stmt.executeUpdate(sql);
            
            conL.commit();            
        
        }catch(Exception e){
            throw new Exception("Ошибка при заполнении таблицы разделов." + e.getMessage());
        }    
    }
    
    private void ctArticles() throws Exception{
        Connection conL = null;
        String sql = "create table ARTICLE (id int not null, name varchar(50) not null, note varchar(255), razdel int, sort_number int, type int)";
        String sqlFk = "ALTER TABLE ARTICLE " +
                       "ADD CONSTRAINT FK_ARTICLE_RAZDEL FOREIGN KEY " +
                        "( razdel ) REFERENCES RAZDEL ( ID )";
        String checkSql = "select count(*) cnt from SYS.SYSTABLES where tabletype = 'T' and tablename = 'ARTICLE'";
               
        
        Statement stmt = null;
        ResultSet res = null;
        
        try{
            
            conL = getConnect();
            
           stmt = conL.createStatement() ;
           
           res = stmt.executeQuery(checkSql);
           
           
           int cnt = 0;
           if(res.next()){
               cnt = Integer.parseInt(res.getString("cnt"));
           }
           
           
            
           if(cnt < 1){
                stmt.executeUpdate(sql);
                conL.commit();
                
                stmt.executeUpdate(sqlFk);
                conL.commit();
                
                // заполнение заметок по умолчанию
                // простые заметки
                fillArticles("insert into ARTICLE (id, name, note, razdel, sort_number, type) values (1, 'Заметка1', 'Заметка создана для проверки, сообщения.', 4, 1, 0)");
                
                // Замеки пароли
                fillArticles("insert into ARTICLE (id, name, note, razdel, sort_number, type) values (2, 'Пароль Калмыкии', 'Заметка создана для проверки, пароля.', 8, 1, 1)");
           }        
           
            
            
            
            
        
        }catch(Exception e){
            throw new Exception("Ошибка при создании таблицы заметок");
        }finally{
            res.close();
            stmt.close();
        }
    }
    
    private void fillArticles(String sql) throws Exception{
        Connection conL = null;
        
        try{
            
            conL = getConnect();
            
            Statement stmt = conL.createStatement() ;
            
            stmt.executeUpdate(sql);
            
            conL.commit();            
        
        }catch(Exception e){
            throw new Exception("Ошибка при заполнении таблицы заметок." + e.getMessage());
        }
    }
}
