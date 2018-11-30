
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
			
			System.out.println("Databas에 연결되었습니다.");
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 : "  + cnfe.toString());
			System.exit(1);
		} catch (SQLException sqle) {
			System.out.println("DB 접속 실패 : " + sqle.toString());
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return conn;
	}
}
