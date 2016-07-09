import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class Database {
	  //variable for Openshift connection
    //private static String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    //private static String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    //private static String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";

//variables for Kami's local connection
    private static String DBUSERNAME = "myUser";
    private static String DBPASSWORD = "myPass";
    private static String DBURL = "jdbc:mysql://localhost/north_pole";
    
	
	private static Connection connection = null;
	
	public Database() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		if(connection == null) {
			connection = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
		}
	}
	
	public boolean exists(String table, List<String> columns, List<String> values) throws SQLException {
		String query = "SELECT COUNT(*) as count FROM " + table + " WHERE ";
		for(int i = 0; i < columns.size() && i < values.size(); i++) {
			query += columns.get(i) + " = " + values.get(i) + " ";
		}
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		int x = rs.getInt("count");
		if(x > 0) {
			return true;
		}
		return false;
	}
	
	public int find(String table, List<String> columns, List<String> values) throws SQLException {
		String query = "SELECT id, COUNT(*) as count FROM " + table + " WHERE ";
		for(int i = 0; i < columns.size() && i < values.size(); i++) {
			query += columns.get(i) + " = " + values.get(i) + " ";
		}
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		int id = rs.getInt("id");
		int count = rs.getInt("count");
		if(count > 0) {
			return id;
		}
		return 0;
	}
	
	public ResultSet query(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}
	
	public Statement getStatement() throws SQLException{
		return connection.createStatement();
	}
	
	public PreparedStatement getPreparedStatement(String query) throws SQLException{
		return connection.prepareStatement(query);
	}
	
}
