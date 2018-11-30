package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TreatmentsTableCTRL implements TableCTRL{
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public static int today_treat_num = 0;
	
	public TreatmentsTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'treatments' table?
			ResultSet rs = CheckTableExist("TREATMENTS");
		
			
			
			//if there isn't 'treatment' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("TREATMENTS 테이블에 접속하였습니다.");
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
	
	//Create 'treatments' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table treatments " +
					"( " +
					"treat_id number, " +
					"pat_id number not null, " +
					"doc_id number not null, " +
					"treat_contents varchar2(100) not null, " +
					"treat_date date not null, " +
					
					
					"primary key(treat_id, pat_id, doc_id), " + 
					"foreign key(pat_id) references patients(pat_id), " + 
					"foreign key(doc_id) references doctors(doc_id)" + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("TREATMENTS 테이블이 생성되었습니다.");
			InsertTreatments();
			
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
	private void InsertTreatments() {
		
		try {
			String sql = "insert into treatments values(181125023, 2345, 980312, '발목 골절', '2018-11-25')";
			stmt.executeUpdate(sql);

			sql = "insert into treatments values(181125100, 3545, 020403, '피부 트러블 치료', '2018-11-25')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181126056, 3424, 080543, '목 디스크로 MRI 촬영', '2018-11-26')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181127024, 7675, 050900, '중이염', '2018-11-27')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181128012, 4533, 000601, '장염', '2018-11-28')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181128001, 5546, 070576, '여드름 치료', '2018-11-28')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181129026, 4543, 050101, '위염', '2018-11-29')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181201102, 9768, 091001, '화상 치료', '2018-12-01')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181201003, 4234, 091001, '교통사고 외상치료', '2018-12-01')";
			stmt.executeUpdate(sql);
			
			sql = "insert into treatments values(181202087, 7643, 062019, '장염', '2018-12-02')";
			stmt.executeUpdate(sql);
			 	
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	
	//Insert values
	//date and next_date must be in "yyyy-MM-dd" format. (Example : date = "2018-03-12")
	public void Insert(int treat_id, int pat_id, int doc_id, String contents, String date) {
		try {
			String sql = "insert into treatments values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, treat_id);
			pstmt.setInt(2, pat_id);
			pstmt.setInt(3, doc_id);
			pstmt.setString(4, contents);
			
			
			pstmt.setString(5, date);

				
				
			pstmt.executeQuery();
			conn.commit();
				
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	//search treatments info
	public ResultSet GetTreatmentsInfo(String name) {
		try {

			String sql = "select e.chart_id, d.treat_contents, d.treat_date "
					+ "from charts e, treatments d, patients f "
					+ "where e.treat_id = d.treat_id and e.pat_id = f.pat_id and f.pat_name = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
		
			return pstmt.executeQuery();
			
		
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}

}
