import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class D_DayoffTableCTRL implements TableCTRL{
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public D_DayoffTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'd_dayoff' table?
			ResultSet rs = CheckTableExist("D_DAYOFF");
		
			
			
			//if there isn't 'd_dayoff' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("D_DAYOFF 테이블에 접속하였습니다.");
			}
			
			rs.close();
		}
		catch(Exception e) {
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	
	
	//Check if there exist table
	@Override
	public ResultSet CheckTableExist(String tableName) {
		
		try {
			String sql = "select table_name from all_tab_comments where table_name = ?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableName);
		
			return pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
		
	
	}
	
	//Create 'd_dayoff' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table d_dayoff " +
					"( " +
					"doc_id number, " +
					"day_off date, " +
					
					"foreign key(doc_id) references doctors(doc_id)" + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("D_DAYOFF 테이블이 생성되었습니다.");
			InsertDayoff();
			
		}
		
		conn.commit();
		
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}	
	}
	
	
	//Close connections.
	@Override
	public void CloseConn() {
		try {
			conn.close();
			stmt.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	//Insert Values (Initialize).
	private void InsertDayoff() {
		
		try {
			String sql = "insert into d_dayoff values(980312, '2018-04-25')";
			stmt.executeUpdate(sql);

			sql = "insert into d_dayoff values(050900, '2018-12-25')";
			stmt.executeUpdate(sql);
			
			
			sql = "insert into d_dayoff values(050101, '2018-08-31')";
			stmt.executeUpdate(sql);
			
			sql = "insert into d_dayoff values(070576, '2018-11-11')";
			stmt.executeUpdate(sql);
			
			sql = "insert into d_dayoff values(091001, '2018-11-30')";
			stmt.executeUpdate(sql);
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	public ResultSet GetDayOff() {
		try {
			
			String sql = "select e.doc_name, d.day_off from doctors e, d_dayoff d where e.doc_id = d.doc_id";
			pstmt = conn.prepareStatement(sql);
			
			return pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	
}
