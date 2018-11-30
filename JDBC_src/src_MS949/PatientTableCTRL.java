import java.sql.*;

public class PatientTableCTRL implements TableCTRL{
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	public PatientTableCTRL (Connection conn) {
		try {
			this.conn = conn;
			this.stmt = this.conn.createStatement();
			
			
			//check is there 'patients' table?
			ResultSet rs = CheckTableExist("PATIENTS");
		
			
			
			//if there isn't 'patients' table, then create that.
			if (!rs.next()) {
				CreateTable();
			}else {
				System.out.println("PATIENTS ���̺� �����Ͽ����ϴ�.");
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
	
	//Create 'patients' table.
	@Override
	public void CreateTable() {
		
		try {
		
		String sql = "create table patients " +
					"( " +
					"pat_id number, " +
					"pat_name varchar2(20) not null, " +
					"pat_gen char not null, " + 
					"pat_jumin number not null, " +
					"pat_addr varchar2(40) not null, " + 
					"pat_phone varchar2(15), " +
					"pat_email varchar2(50), " + 
					"pat_job varchar2(20) not null, " +
					
					"unique (pat_email), " +
					"primary key(pat_id) " +
					")";
		
		Statement stmt = conn.createStatement();
		
		int result = stmt.executeUpdate(sql);
	
		if (result == 0) {
			System.out.println("PATIENTS ���̺��� �����Ǿ����ϴ�.");
			InsertPatient();
			
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
	private void InsertPatient() {
		
		try {
			String sql = "insert into patients values(2345, '�Ȼ��', 'M', 880305, '����', '010-555-7845', 'ask@google.com', 'ȸ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(3545, '�輺��', 'M', 751124, '����', '010-333-7812', 'ksl@google.com', '�ڿ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(3424, '������', 'M', 560307, '�λ�', '010-888-4859', 'ljj@naver.com', 'ȸ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(7675, '�ֱ���', 'M', 910817, '����', '010-222-4847', 'cks@google.com', 'ȸ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(4533, '���Ѱ�', 'M', 680327, '����', '010-777-9630', 'jhk@knu.ac.kr', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(5546, '������', 'M', 841225, '�뱸', '016-777-0214', 'ywh@naver.com', '�ڿ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(4543, '������', 'M', 790905, '�λ�', '010-555-4187', 'cjj@google.com', 'ȸ���')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(9768, '������', 'F', 720427, '����', '010-888-3675', 'ljh@knu.ac.kr', '����')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(4234, '������', 'F', 980811, '����', '010-999-6541', 'onm@hanmail.com', '�л�')";
			stmt.executeUpdate(sql);
			
			sql = "insert into patients values(7643, '�ۼ���', 'M', 981103, '����', '010-222-5874', 'ssm@hanmail.com', '�л�')";
			stmt.executeUpdate(sql);
			
			conn.commit();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	
	//Insert values
	public void Insert(int pat_id, String name, String sex, int jumin, String addr, String phoneNum, String email, String position) {
		
		try {
			String sql = "insert into patients values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pat_id);
			pstmt.setString(2, name);
			pstmt.setString(3, sex);
			pstmt.setInt(4, jumin);
			pstmt.setString(5, addr);
			pstmt.setString(6, phoneNum);
			pstmt.setString(7, email);
			pstmt.setString(8, position);
			
			pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
		
	}
	
	
	//Search Patients info
	public ResultSet GetPatientsInfo(String name) {
		try {

			String sql = "select pat_name, pat_gen, pat_jumin from patients where pat_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
		
			return pstmt.executeQuery();
			
			
		
		
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	public int GetPatID(String name, int jumin) {
		try {
			
			String sql = "select pat_id from patients where pat_name = ? and pat_jumin = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, jumin);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("PAT_ID");
				
			}
			
			return -1;
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return -1;
		}
	}
	
}
