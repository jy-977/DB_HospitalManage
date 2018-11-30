
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection dbConn;
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:oraknu";
			String user = "kdhong";
			String pass = "kdhong";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pass);
			
			System.out.println("Databas�� ����Ǿ����ϴ�.");
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB ����̹� �ε� ���� : "  + cnfe.toString());
			System.exit(1);
		} catch (SQLException sqle) {
			System.out.println("DB ���� ���� : " + sqle.toString());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return conn;
	}
}
