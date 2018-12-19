package com.example.DB;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oracle.sql.DATE;

@Controller
public class SimpleController {
	@Autowired
	private PatientRepository patRepo;
	@Autowired
	private PatientService patSvc;
	@Autowired
	private TreatChartService tchartSvc;
	
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
	
	@RequestMapping("/chartInput.do")
	public String chartInputDo(@Valid TreatChart new_chart ,Model model) {
		System.out.println(new_chart);
		tchartSvc.insertChart(new_chart);
		return "redirect:/chartsearching.html";
	}
	
	@RequestMapping("/chartInput.html")
	public String ChartInput(@RequestParam long p, Model model) {
		Patient pat = patRepo.findById(p).get();
		System.out.println(pat);
		model.addAttribute("patient",pat);
		
		TreatChart new_chart = new TreatChart();
		model.addAttribute("new_chart",new_chart);
		return "chartInput";
	}
	
	@RequestMapping("/chartShowup.html")
	public String ChartShowup(@RequestParam String chart_id,Model model) {
		TreatChart chart = tchartSvc.showChart(chart_id);
		System.out.println(chart);
		model.addAttribute("chart", chart);
		return "chartShowup";
	}
	
	
	private List<TreatChart> tchartList = null;
	@RequestMapping("/chartList.html")
	public String ChartList(@RequestParam long p,Model model) {
		Patient pat = patRepo.findById(p).get();
		System.out.println(pat);
		model.addAttribute("patient",pat);
		
		tchartList = tchartSvc.treatChart(p);
		model.addAttribute("charts", tchartList);
		
		System.out.println(tchartList);
		return "chartList";
	}
	
	@Autowired
	private reserveService rsvService;
	private List<DayoffDoctor> rsvList = null;
	@RequestMapping("/calendar.html")
	public String Reservation(Model model) {
		rsvList = rsvService.dayoff_print();
		System.out.println("reservation list: " + rsvList.toString());
		model.addAttribute("dayoff_doc", rsvList);
		return "calendar";
	}
}
