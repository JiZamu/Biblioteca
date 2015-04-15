package databasehelper;
    
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper<T>{
    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/PRUEBAS";
    private static final String USER = "while";
    private static final String PASSWORD = "JiZa..";
    
    public int changeRegister(String consultation){
        Connection connection = null;
        Statement statement = null;
        int affectedRow = 0;
        
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            affectedRow = statement.executeUpdate(consultation);
        } catch(ClassNotFoundException e){
            System.out.println("En driver"+e.getMessage());
        } catch(SQLException e){
            System.out.println("En SQL"+e.getMessage());
        } finally{
            if(statement != null){
                try{
                    statement.close();
                } catch(SQLException e){
                
                }
            }
            
            if(connection != null){
                try{
                    connection.close();
                } catch(SQLException e){
                
                }
            }
        }
        
        return affectedRow;
    }
    
    public List<T> selectRecords(String consultation, Class valueClass){
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        
        List<T> list = new ArrayList<T>();
        
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
            rs = statement.executeQuery(consultation);
            
            while(rs.next()){
                T object= (T)Class.forName(valueClass.getName()).newInstance();
                Method[] methods = object.getClass().getDeclaredMethods();
                for(int i = 0; i < methods.length; i++){
                    if(methods[i].getName().startsWith("set")){
                        methods[i].invoke(object, rs.getString(methods[i].getName().substring(3)));
                    }
                    
                    if(object.getClass().getName().equals("java.lang.String")){
                        object = (T)rs.getString(1);
                    }
                    list.add(object);
                    
                    System.out.println(methods[i].getName());
                    
                }
            }
        } catch(ClassNotFoundException e){
            System.out.println("En driver "+e.getMessage());
        } catch(SQLException e){
            System.out.println("En SQL "+e.getMessage());
        } catch(Exception e){
            System.out.println("En object "+e.getMessage());
        } finally{
            if(rs != null){
                try{
                    rs.close();
                } catch(SQLException e){
                
                }
            }
            
            if(statement != null){
                try{
                    statement.close();
                } catch(SQLException e){
                
                }
            }
            
            if(connection != null){
                try{
                    connection.close();
                } catch(SQLException e){
                
                }
            }
        }
        
        return list;
    }
}