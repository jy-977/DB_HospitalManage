import java.util.ArrayList;

public class Main {
	
		
	public static void main(String[] args) {
	
		
	
		MainCTRL mainCtrl = new MainCTRL();
	
		Main m = new Main();
		//m.test_insert(mainCtrl);
		//mainCtrl.DropAllTable();
		
		
		//mainCtrl.InsertTreatment_Chart_Appointment("�Ȼ��", 880305, "M", "������", "������", "2018-11-13", "2018-11-20", "��� ����", "ȣ����");
		
		
		ArrayList<String> arr = mainCtrl.SearchChart("s_181125023");
		m.print(arr);
		

		arr = mainCtrl.SearchDoctors("�ڸ��");
		m.print(arr);
		
		
		arr = mainCtrl.SearchPatients("�Ȼ��");
		m.print(arr);
		
		
		arr = mainCtrl.SearchTreatments("�Ȼ��");
		m.print(arr);
		
		
		arr = mainCtrl.SearchAppointments("������");
		m.print(arr);
		
		
		arr = mainCtrl.SearchDayOff();
		m.print(arr);
		
		

	}
	
	public void test_insert(MainCTRL mainCtrl) {
		
		int id = 123456789;
		int pat_id = 2345;
		int doc_id = 601;
		String treat_c = "�ƾ�";
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
