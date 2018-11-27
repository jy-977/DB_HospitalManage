package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class N_DayoffTableCTRL implements TableCTRL{
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public N_DayoffTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'n_dayoff' table?
			ResultSet rs = CheckTableExist("N_DAYOFF");
		
			
			//if there isn't 'n_dayoff' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("N_DAYOFF 테이블에 접속하였습니다.");
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
	
	//Create 'n_dayoff' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table n_dayoff " +
					"( " +
					"nur_id number, " +
					"day_off date, " +
					
					"foreign key(nur_id) references nurse(nur_id)" + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("N_DAYOFF 테이블이 생성되었습니다.");
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
			String sql = "insert into n_dayoff values(050021, '2018-05-05')";
			stmt.executeUpdate(sql);

			sql = "insert into n_dayoff values(070605, '2018-06-25')";
			stmt.executeUpdate(sql);
			
			
			sql = "insert into n_dayoff values(071018, '2018-07-01')";
			stmt.executeUpdate(sql);
			
			sql = "insert into n_dayoff values(104145, '2018-10-23')";
			stmt.executeUpdate(sql);
			
			sql = "insert into n_dayoff values(120309, '2018-11-28')";
			stmt.executeUpdate(sql);
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	public ResultSet GetDayOff() {
		try {
			
			String sql = "select e.nur_name, d.day_off from nurse e, n_dayoff d where e.nur_id = d.nur_id";
			pstmt = conn.prepareStatement(sql);
			
			return pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
}
