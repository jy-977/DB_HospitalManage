package database;
import java.sql.*;

public class NurseTableCTRL implements TableCTRL {
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public NurseTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'nurse' table?
			ResultSet rs = CheckTableExist("NURSE");
		
			
			
			//if there isn't 'nurse' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("NURSE ���̺� �����Ͽ����ϴ�.");
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
	
	//Create 'nurse' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table nurse " +
					"( " +
					"nur_id number, " +
					"major_job varchar2(25) not null, " +
					"nur_name varchar2(20) not null, " +
					"nur_gen char not null, " +
					"nur_phone varchar2(15), " +
					"nur_email varchar2(50), " + 
					"nur_position varchar2(20) not null, " +
					
					"unique (nur_email), " +
					"primary key(nur_id) " + 
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("NURSE ���̺��� �����Ǿ����ϴ�.");
			InsertNurse();
			
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
	private void InsertNurse() {
		
		try {
			String sql = "insert into nurse values(050302, '�ܰ�', '������', 'F', '010-555-8751', 'key@google.com', '����ȣ��')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(050021, '����', '������', 'F', '016-333-8745', 'ysa@google.com', '����ȣ��')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(040089, '�Ǻΰ�', '������', 'M', '010-666-7646', 'sjw@naver.com', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(070605, '��缱��', '����ȭ', 'F', '010-333-4588', 'yjh@google.com', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(070804, '����', '���ϳ�', 'F', '010-222-1340', 'nhn@hanmail.com', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(071018, '�Ҿư�', '��ȭ��', 'F', '019-888-4116', 'khk@google.com', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(100356, '�Ҿư�', '�̼���', 'M', '010-777-1234', 'lsy@naver.com', '��ȣ��')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(104145, '�ܰ�', '����', 'M', '010-999-8520', 'kh@naver.com', '��ȣ��')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(120309, '�Ǻΰ�', '�ڼ���', 'M', '010-777-4996', 'psw@google.com', '��ȣ��')";
			stmt.executeUpdate(sql);
			
			sql = "insert into nurse values(130211, '�ܰ�', '�̼���', 'F', '010-222-3214', 'lsy2@hanmail.com', '��ȣ��')";
			stmt.executeUpdate(sql);
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	
	//Insert values
	public void Insert(int id, String major_job, String name, String sex, String phoneNum, String email, String position) {
		
		try {
			String sql = "insert into nurse values(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setString(2, major_job);
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
	
	public int GetNurID(String name) {
		try {
			
			String sql = "select nur_id from nurse where nur_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("NUR_ID");
				
			}
			
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return -1;
		}
	}
	
	
}
