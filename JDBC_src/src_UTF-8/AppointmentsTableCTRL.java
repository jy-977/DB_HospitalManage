import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppointmentsTableCTRL implements TableCTRL{

	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public AppointmentsTableCTRL(Connection conn) {
		
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'appointments' table?
			ResultSet rs = CheckTableExist("APPOINTMENTS");
		
			
			
			//if there isn't 'appointments' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("APPOINTMENTS 테이블에 접속하였습니다.");
			}
			
			rs.close();
		}
		catch(Exception e) {
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	
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

	@Override
	public void CreateTable() {
		
		try {
			
			String sql = "create table appointments " + 
					"( " + 
					"appoint_date date not null, " + 
					"treat_id number not null, " + 
					"doc_id number not null, " + 
					"pat_id number not null, " +
					
					"foreign key(treat_id, doc_id, pat_id) references treatments(treat_id, doc_id, pat_id) " + 
					")";
			
			Statement stmt = conn.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if (result == 0) {
				System.out.println("APPOINTMENTS 테이블이 생성되었습니다.");
				InsertAppoint();
				
			}
			
			conn.commit();
			
			}catch(Exception e){
				e.printStackTrace();
				System.err.println("sql error = " + e.getMessage());
			}	
		
	}

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
	private void InsertAppoint() {
		
		try {
			String sql = "insert into appointments values('2018-12-02', 181125023, 980312, 2345)";
			stmt.executeUpdate(sql);

			sql = "insert into appointments values('2018-12-02', 181125100, 020403, 3545)";
			stmt.executeUpdate(sql);
			
			sql = "insert into appointments values('2018-12-03', 181126056, 080543, 3424)";
			stmt.executeUpdate(sql);
			
			sql = "insert into appointments values('2018-12-04', 181127024, 050900, 7675)";
			stmt.executeUpdate(sql);
			
			sql = "insert into appointments values('2018-12-05', 181128001, 070576, 5546)";
			stmt.executeUpdate(sql);
			
			sql = "insert into appointments values('2018-12-06', 181129026, 050101, 4543)";
			stmt.executeUpdate(sql);
			
			sql = "insert into appointments values('2018-12-08', 181201102, 091001, 9768)";
			stmt.executeUpdate(sql);
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	public void Insert(String date, int treat_id, int doc_id, int pat_id) {
		
		try {
			String sql = "insert into appointments values(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, date);
			pstmt.setInt(2, treat_id);
			pstmt.setInt(3, doc_id);
			pstmt.setInt(4, pat_id);
			
			pstmt.executeQuery();
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	public ResultSet GetAppointInfo(String doc_name) {
		try {
			String sql = "select e.pat_name, d.treat_contents, c.appoint_date " +
					"from patients e, treatments d, appointments c, doctors f " + 
					"where e.pat_id = c.pat_id and d.treat_id = c.treat_id and c.doc_id = f.doc_id and f.doc_name = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, doc_name);
			
			return pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	
}
