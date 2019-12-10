import java.sql.*;

// This class is responsible for:
// 1: taking credentials and correctly establishing a connection
// 2: executing querys and returning the ResultSet objects
// 3: closing an established connection
// This class also has several test functions for debugging.
class ConnectionHandler{
    String username, password, serverName, databaseName, databaseType, JdbcUrl;
    int port;    
    Connection conn;
    
    ConnectionHandler(){       
        //conn = null;
    }
    
    // Alternative constructor.
    ConnectionHandler(String username, String password, String serverName, int port, String databaseName, String databaseType){        
        this.username = username;
        this.password = password;
        this.serverName = serverName;
        this.port = port;
        this.databaseName = databaseName;
        this.databaseType = (databaseType).toLowerCase();
        //conn = null;
        
        createJdbcUrl();
    }
    
    // Alternative constructor.
    ConnectionHandler(String JdbcUrl, String databaseType){        
        this.JdbcUrl = JdbcUrl;
        this.databaseType = (databaseType).toLowerCase();
        
        //conn = null;
    }
    
    // Function for setting the connection properties/credentials -- I used this in combination with the default constructor in the Frame class and
    // subsequently called the createJdbcUrl() and establishConnection() functions.
    public void setConnectionProperties(String username, String password, String serverName, int port, String databaseName, String databaseType){
        this.username = username;
        this.password = password;
        this.serverName = serverName;
        this.port = port;
        this.databaseName = databaseName;
        this.databaseType = (databaseType).toLowerCase();
        
        //conn = null;
    }
    
    public void resetConnectionProperties(){
        this.username = null;
        this.password = null;
        this.serverName = null;
        this.port = 0;
        this.databaseName = null;
        this.databaseType = null;
        
        //conn = null;
    }
    
    // Test function
    public void printDataMembers(){        
        System.out.println("ConnectionHandler: data members list:");
        System.out.println(username);
        System.out.println(password);
        System.out.println(serverName);
        System.out.println(port);
        System.out.println(databaseName);
        System.out.println(databaseType);
    }
    
    public void createJdbcUrl(){
        JdbcUrl = "jdbc:mysql:" + "//" + serverName + ":" + port;
        
        if (!databaseName.equals(""))
            JdbcUrl += ("/" + databaseName);
    }
    
    // Test function
    public void printJdbcUrl(){
        System.out.println(JdbcUrl);
    }    
    
    public void establishConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");

        if (databaseType.equals("mysql"))
            conn = DriverManager.getConnection(JdbcUrl, username, password);
        
        else
            throw new Exception("ConnectionHandler: given databaseType = " + databaseType + " is not supported");
        
        System.out.println("ConnectionHandler: successfully established database connection for:");
        System.out.println("JdbcUrl = \"" + JdbcUrl + "\" | username = \"" + username + "\" | password = \"" + password + "\"");
    }
    
    public ResultSet performQuery(PreparedStatement pstatement) throws Exception{
        System.out.println("ConnectionHandler: performQuery: query = " + pstatement);
            
        ResultSet resultSet = pstatement.executeQuery();
                
        // printResultSet(resultSet);
        
        return resultSet;
    }

    public int performUpdateQuery(PreparedStatement pstatement) throws Exception{
        System.out.println("ConnectionHandler: performQuery: query = " + pstatement);
            
        int result = pstatement.executeUpdate();
                
        // printResultSet(resultSet);
        
        return result;
    }
    
    // Test function
    public ResultSet getAllUsers() throws Exception{
        String query = "SELECT * FROM users";
        
        ResultSet resultSet = null;
        
        Statement statement = conn.createStatement();
        
        resultSet = statement.executeQuery(query);
                
        printResultSet(resultSet);
        
        return resultSet;
    }

    // Test function
    public void insertUsers() throws Exception{
        ResultSet resultSet = null;
        
        Statement statement = conn.createStatement();
        
        for (int x = 0; x < 100; x++){
            String update = "INSERT INTO users (username, password) VALUES ('username_" + x +  "', 'password_" + x + "')";
            
            int updateResult = statement.executeUpdate(update);
            
            System.out.println(update);
            
            System.out.println("int updateResult = " + updateResult);
        }
    }      
    
    public void closeConnection() throws Exception{
        conn.close();
        
        //conn = null;
        
        System.out.println("ConnectionHandler: successfully closed database connection for:");        
        System.out.println("JdbcUrl = \"" + JdbcUrl + "\" | username = \"" + username + "\" | password = \"" + password + "\"");
    }
    
    // Test function
    public void printResultSet(ResultSet resultSet) throws Exception{
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        
        int numberOfColumns = resultSetMetaData.getColumnCount();
        
        while (resultSet.next()) {
            for (int i = 1; i <= numberOfColumns; i++) {
                if (i > 1)
                    System.out.print(" | ");
                
                String columnValue = resultSet.getString(i);
                
                System.out.print(resultSetMetaData.getColumnName(i) + ": " + columnValue);
            }
            
            System.out.println("");
        }
    }

    public Connection getConnection() {
        return conn;
    }
    
    // Test main function
    public static void main(String[] x){
        ConnectionHandler test = new ConnectionHandler("java_test_user", "pass", "127.0.0.1", 3306, "java_db_test", "MySQL"); // This is for a local database I have setup
        
        test.printDataMembers();
        test.createJdbcUrl();
        test.printJdbcUrl();
        
        //Connection conn = null;
        
        try{
            test.establishConnection();
            test.getAllUsers();
            // test.insertUsers(); 
            test.closeConnection();
        }
        
        catch (Exception e){            
            e.printStackTrace();
        }
	}
}