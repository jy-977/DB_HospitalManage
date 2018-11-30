//This class is a controller that handles all aspects of information processing like select, insert, update, delete, etc.

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;


public class MainCTRL {
	Connection conn;
	PreparedStatement pstmt = null;
	DoctorsTableCTRL doctorCtrl;
	NurseTableCTRL nurestCtrl;
	PatientTableCTRL patientCtrl;
	TreatmentsTableCTRL treatmentCtrl;
	ChartTableCTRL chartCtrl;
	D_DayoffTableCTRL d_dayoffCtrl;
	N_DayoffTableCTRL n_dayoffCtrl;
	AppointmentsTableCTRL appointmentCtrl;
	
	public MainCTRL(){
		conn = DBConnection.getConnection();
		
		doctorCtrl = new DoctorsTableCTRL(conn);
		nurestCtrl = new NurseTableCTRL(conn);
		patientCtrl = new PatientTableCTRL(conn);
		treatmentCtrl = new TreatmentsTableCTRL(conn);
		chartCtrl = new ChartTableCTRL(conn);
		d_dayoffCtrl = new D_DayoffTableCTRL(conn);
		n_dayoffCtrl = new N_DayoffTableCTRL(conn);
		appointmentCtrl = new AppointmentsTableCTRL(conn);
	}
	
	//ArrayList contains information of Patients.
	//Order of information is a repetition of Name, Gender and Jumin.
	public ArrayList<String> SearchPatients(String name) {
		
		try {
			ResultSet rs = patientCtrl.GetPatientsInfo(name);
			ArrayList<String> patientsInfo = new ArrayList<String>();
			
			while(rs.next()) {
				patientsInfo.add(rs.getString("PAT_NAME"));
				patientsInfo.add(rs.getString("PAT_GEN"));
				patientsInfo.add(Integer.toString(rs.getInt("PAT_JUMIN")));
				
			}
			
			return patientsInfo;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	
	//ArrayList contains information of chart ID, contents of treatments and dates of treatments.
	//Order of information is a repetition of chart ID, Treatment Contents and Treatment Date
	public ArrayList<String> SearchTreatments(String name) {
		try {
			ResultSet rs = treatmentCtrl.GetTreatmentsInfo(name);
			ArrayList<String> treatmentInfo = new ArrayList<String>();
			
			while(rs.next()) {
				treatmentInfo.add(rs.getString("CHART_ID"));
				treatmentInfo.add(rs.getString("TREAT_CONTENTS"));
				treatmentInfo.add(rs.getDate("TREAT_DATE").toString());
				
			}
			
			return treatmentInfo;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	//ArrayList contains all the information of chart in detail.
	//Order of information is that chart ID, patient name, jumin, gender, doctor name, major treat, nurse name, treat date, treat contents and chart contents
	public ArrayList<String> SearchChart(String chartID){
		try {
			ResultSet rs = chartCtrl.GetChartInfo(chartID);
			ArrayList<String> chartInfo = new ArrayList<String>();
			
			while(rs.next()) {
				chartInfo.add(rs.getString("CHART_ID"));
				chartInfo.add(rs.getString("PAT_NAME"));
				chartInfo.add(Integer.toString(rs.getInt("PAT_JUMIN")));
				chartInfo.add(rs.getString("PAT_GEN"));
				chartInfo.add(rs.getString("DOC_NAME"));
				chartInfo.add(rs.getString("MAJOR_TREAT"));
				chartInfo.add(rs.getString("NUR_NAME"));
				chartInfo.add(rs.getDate("TREAT_DATE").toString());
				chartInfo.add(rs.getString("TREAT_CONTENTS"));
				chartInfo.add(rs.getString("CHART_CONTENTS"));
			}
			
			return chartInfo;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	//ArrayList contains the information of doctors simply.
	//The order of information is a repetition of doctor's name, major treatment, position and doctor ID
	public ArrayList<String> SearchDoctors(String doc_name){
		try {
			ResultSet rs = doctorCtrl.GetDoctorInfo(doc_name);
			ArrayList<String> docInfo = new ArrayList<String>();
			
			while(rs.next()) {
				docInfo.add(rs.getString("DOC_NAME"));
				docInfo.add(rs.getString("MAJOR_TREAT"));
				docInfo.add(rs.getString("DOC_POSITION"));
				docInfo.add(String.format("%06d", rs.getInt("DOC_ID")));
			}
			
			return docInfo;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
		
		
	}
	
	//ArrayList contains all the information of appointments.
	//The order of information is a repetition of patients's name, treatment contents and appointment date
	public ArrayList<String> SearchAppointments(String doc_name){
		try {
			ResultSet rs = appointmentCtrl.GetAppointInfo(doc_name);
			ArrayList<String> appInfo = new ArrayList<String>();
			
			while(rs.next()) {
				appInfo.add(rs.getString("PAT_NAME"));
				appInfo.add(rs.getString("TREAT_CONTENTS"));
				appInfo.add(rs.getDate("APPOINT_DATE").toString());
			}
			
			return appInfo;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
		
	}
	
	//ArrayList contains all the information of day off whose doctors and nurses.
	//The order of information is doctor's name, doctor's day off, nurse's name and nurse's day off.
	public ArrayList<String> SearchDayOff(){
		try {
			ArrayList<String> dayoffInfo = new ArrayList<String>();
			ResultSet rs = d_dayoffCtrl.GetDayOff();
			
			
			while(rs.next()) {
				dayoffInfo.add(rs.getString("DOC_NAME"));
				dayoffInfo.add(rs.getDate("DAY_OFF").toString());
			}
			
			rs = n_dayoffCtrl.GetDayOff();
			
			while(rs.next()) {
				dayoffInfo.add(rs.getString("NUR_NAME"));
				dayoffInfo.add(rs.getDate("DAY_OFF").toString());
			}
			
			return dayoffInfo;
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
			return null;
		}
	}
	
	
	//treat_date and next_date must be in "yyyy-MM-dd" format. (Example : date = "2018-03-12")
	@SuppressWarnings("static-access")
	public void InsertTreatment_Chart_Appointment(String pat_name, int pat_jumin, String pat_gen, String doc_name, String nur_name, String treat_date, String next_date, String treat_cont, String chart_cont) {
		try {
			
			//Get patient ID
			int pat_id = patientCtrl.GetPatID(pat_name, pat_jumin);
			
			//Get Doctor ID
			int doc_id = doctorCtrl.GetDocID(doc_name);
			
			//Make treat ID and insert into treatments table
			Date tID = new SimpleDateFormat("yyyy-MM-dd").parse(treat_date);
			String treat_id = new SimpleDateFormat("yyMMdd").format(tID);
			treat_id += String.format("%03d", treatmentCtrl.today_treat_num++);
			
			//Insert into treatment table
			treatmentCtrl.Insert(Integer.parseInt(treat_id), pat_id, doc_id, treat_cont, treat_date);
			
			//Get doctor's major
			String major = doctorCtrl.GetMajor(doc_name);
			
			//Set chart ID and default is 'F_'
			String chartID = "F_";
			

			switch(major) {
			
				case "외과" :
					chartID = "s_";
					break;
				case "소아과" :
					chartID = "p_";
					break;
				case "피부과" :
					chartID = "d_";
					break;
				case "방사선과" :
					chartID = "r_";
					break;
				case "내과" :
					chartID = "i_";
					break;
				}
				
			
			chartID += treat_id;
			
			//Get nurse ID
			int nur_id = nurestCtrl.GetNurID(nur_name);
			
			
			//Insert into charts table
			chartCtrl.Insert(chartID, Integer.parseInt(treat_id), doc_id, pat_id, nur_id, chart_cont);
			
			//Insert into appointments table
			if (!next_date.equals(""))
				appointmentCtrl.Insert(next_date, Integer.parseInt(treat_id), doc_id, pat_id);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
	}
	

	
	
	public void DropAllTable() {
		
		try {
			
			String sql = "drop table charts";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("CHARTS 테이블이 삭제되었습니다.");
			

			sql = "drop table appointments";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("APPOINTMENTS 테이블이 삭제되었습니다.");
			
			sql = "drop table treatments";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("TREATMENTS 테이블이 삭제되었습니다.");
			
			sql = "drop table n_dayoff";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("N_DAYOFF 테이블이 삭제되었습니다.");
			
			sql = "drop table d_dayoff";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("D_DAYOFF 테이블이 삭제되었습니다.");
			
			
			sql = "drop table patients";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("PATIENTS 테이블이 삭제되었습니다.");
			
			sql = "drop table doctors";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("DOCTORS 테이블이 삭제되었습니다.");
			
			sql = "drop table nurse";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeQuery();
			System.out.println("NURSE 테이블이 삭제되었습니다.");
			

			
			
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("sql error = " + e.getMessage());
		}
	}
	
	
}
