import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ChartTableCTRL implements TableCTRL{
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public ChartTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'charts' table?
			ResultSet rs = CheckTableExist("CHARTS");
		
			
			
			//if there isn't 'charts' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("CHARTS 테이블에 접속하였습니다.");
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
	
	//Create 'charts' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table charts " +
					"( " +
					"chart_id varchar2(20), " +
					"treat_id number not null, " +
					"doc_id number not null, " +
					"pat_id number not null, " +
					"nur_id number not null, " +
					"chart_contents varchar2(100) not null, " +
					
					"primary key(chart_id, treat_id, doc_id, pat_id), " + 
					"foreign key(treat_id, doc_id, pat_id) references treatments(treat_id, doc_id, pat_id), " + 
					"foreign key(nur_id) references nurse(nur_id)" + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("CHARTS 테이블이 생성되었습니다.");
			InsertChart();
			
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
	private void InsertChart() {
		
		try {
			String sql = "insert into charts values('s_181125023', 181125023, 980312, 2345, 050302, '깁스 및 소염진통제 처방')";
			stmt.executeUpdate(sql);

			sql = "insert into charts values('d_181125100', 181125100, 020403, 3545, 040089, '피부 감염 방지 주사')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('r_181126056', 181126056, 080543, 3424, 070605, '주사 처방')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('p_181127024', 181127024, 050900, 7675, 100356, '귓속 청소 및 약 처방')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('i_181128012', 181128012, 000601, 4533, 070804, '장염 입원치료')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('d_181128001', 181128001, 070576, 5546, 120309, '여드름 치료약 처방')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('i_181129026', 181129026, 050101, 4543, 070804, '위내시경')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('s_181201102', 181201102, 091001, 9768, 130211, '화상 크림약 처방')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('s_181201003', 181201003, 091001, 4234, 130211, '입원치료')";
			stmt.executeUpdate(sql);
			
			sql = "insert into charts values('p_181202087', 181202087, 062019, 7643, 071018, '장염 입원치료')";
			stmt.executeUpdate(sql);
			
			
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	
	//Insert into charts table
	public void Insert(String chartID, int treatID, int docID, int patID, int nurID, String contents) {
		try {
			String sql = "insert into charts values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chartID);
			pstmt.setInt(2, treatID);
			pstmt.setInt(3, docID);
			pstmt.setInt(4, patID);
			pstmt.setInt(5, nurID);
			pstmt.setString(6, contents);
			
			pstmt.executeQuery();
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	//Get chart information
	public ResultSet GetChartInfo(String chartID) {
		try {

			String sql = "select e.chart_id, d.pat_name, d.pat_jumin, d.pat_gen, f.doc_name, f.major_treat, g.nur_name, h.treat_date, h.treat_contents, e.chart_contents " + 
					"from charts e, patients d, doctors f, nurse g, treatments h " + 
					"where e.chart_id = ? and e.pat_id = d.pat_id and e.doc_id = f.doc_id and e.nur_id = g.nur_id and e.treat_id = h.treat_id ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chartID);
		
			return pstmt.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	
	
	
}
