package com.example.DB;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SimpleController {
	@Autowired
	private PatientRepository patRepo;
	@Autowired
	private PatientService patSvc;
	private ArrayList patModelList;
	private List<String> patList = null;
	
	@RequestMapping("/chartsearching.html")
	public String CharList(Model model) {
		
		patList = patSvc.ShowAllPatient();
		System.out.println("patient in repo are: "+patRepo.findAll());
		System.out.println("patient list: "+ patList.toString());
		model.addAttribute("search",patList);
		model.addAttribute("patients",patRepo.findAll());
		return "chartsearching";
	}
	@RequestMapping("/reservation.html")
	public String Reservation(Model model) {
		return "reservation";
	}
}
