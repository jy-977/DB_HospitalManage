import java.sql.*;

public class DoctorsTableCTRL implements TableCTRL{
	
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	public DoctorsTableCTRL(Connection conn) {
		
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'doctors' table?
			ResultSet rs = CheckTableExist("DOCTORS");
		
			
			
			//if there isn't 'doctors' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("DOCTORS 테이블에 접속하였습니다.");
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
	
	//Create 'doctors' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table doctors " +
					"( " +
					"doc_id number, " +
					"major_treat varchar2(25) not null, " +
					"doc_name varchar2(20) not null, " +
					"doc_gen char not null, " +
					"doc_phone varchar2(15), " +
					"doc_email varchar2(50), " + 
					"doc_position varchar2(20) not null, " +
					
					"unique (doc_email), " +
					"primary key(doc_id) " + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("DOCTORS 테이블이 생성되었습니다.");
			InsertDoctors();
			
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
	private void InsertDoctors() {
		
		try {
			String sql = "insert into doctors values(980312, '외과', '이태정', 'M', '010-333-1340', 'itj@naver.com', '과장')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(000601, '내과', '안성기', 'M', '011-222-0987', 'ask@google.com', '과장')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(001208, '외과', '김민종', 'M', '010-333-8743', 'kmj@naver.com', '과장')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(020403, '피부과', '이태서', 'M', '019-777-3764', 'its@hanmail.com', '과장')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(050900, '소아과', '김연아', 'F', '010-555-3746', 'kya@naver.com', '전문의')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(050101, '내과', '차태현', 'M', '011-222-7643', 'cth@naver.com', '전문의')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(062019, '소아과', '전지현', 'F', '010-999-1265', 'jjh@google.com', '전문의')";
			stmt.executeUpdate(sql);
		
			sql = "insert into doctors values(070576, '피부과', '홍길동', 'M', '016-333-7263', 'hgd@google.com', '전문의')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(080543, '방사선과', '유재석', 'M', '010-222-1263', 'yjs@google.com', '과장')";
			stmt.executeUpdate(sql);
			
			sql = "insert into doctors values(091001, '외과', '박명수', 'M', '010-555-3542', 'pms@naver.com', '전문의')";
			stmt.executeUpdate(sql);
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	public void Insert(int id, String major_treat, String name, String sex, String phoneNum, String email, String position) {
		
		try {
			String sql = "insert into doctors values(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setString(2, major_treat);
			pstmt.setString(3, name);
			pstmt.setString(4, sex);
			pstmt.setString(5, phoneNum);
			pstmt.setString(6, email);
			pstmt.setString(7, position);
			
			pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	//Get doctor ID
	public int GetDocID(String name) {
		
		try {
			String sql = "select doc_id from doctors where doc_name = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("DOC_ID");
			}
			
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return -1;
		}
		
	}
	
	//Get doctor's major treatment
	public String GetMajor(String name) {
		
		try {
			String sql = "select major_treat from doctors where doc_name = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getString("MAJOR_TREAT");
			}
			
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	//Search doctor's information
	public ResultSet GetDoctorInfo(String name) {
		try {
			
			String sql = "select doc_name, major_treat, doc_position, doc_id from doctors where doc_name = ?";
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
