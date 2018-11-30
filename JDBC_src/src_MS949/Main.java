import java.util.ArrayList;

public class Main {
	
		
	public static void main(String[] args) {
	
		
	
		MainCTRL mainCtrl = new MainCTRL();
	
		Main m = new Main();
		//m.test_insert(mainCtrl);
		//mainCtrl.DropAllTable();
		
		
		//mainCtrl.InsertTreatment_Chart_Appointment("안상건", 880305, "M", "이태정", "김은영", "2018-11-13", "2018-11-20", "어딘가 아픔", "호해줌");
		
		
		ArrayList<String> arr = mainCtrl.SearchChart("s_181125023");
		m.print(arr);
		

		arr = mainCtrl.SearchDoctors("박명수");
		m.print(arr);
		
		
		arr = mainCtrl.SearchPatients("안상건");
		m.print(arr);
		
		
		arr = mainCtrl.SearchTreatments("안상건");
		m.print(arr);
		
		
		arr = mainCtrl.SearchAppointments("이태정");
		m.print(arr);
		
		
		arr = mainCtrl.SearchDayOff();
		m.print(arr);
		
		

	}
	
	public void test_insert(MainCTRL mainCtrl) {
		
		int id = 123456789;
		int pat_id = 2345;
		int doc_id = 601;
		String treat_c = "아야";
		String td = "2018-11-12";
		
		mainCtrl.treatmentCtrl.Insert(id, pat_id, doc_id, treat_c, td);
		 
	}
	
	
	
	public void print(ArrayList<String> arr) {
		
		String str = "";
		for (String s : arr) {
			str += s + "\t";
		}
		System.out.println(str);
	}
	
	
}
