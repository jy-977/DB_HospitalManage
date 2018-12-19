package com.example.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class TreatChartService {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List treatChart(long pat_id) {
		String sql = "select c.chart_id, t.treat_date, t.treat_contents,c.chart_contents, p.pat_name, p.pat_jumin, d.doc_name, d.major_treat, n.nur_name " + 
				" from charts c, treatments t, doctors d, nurse n, patients p " + 
				" where c.treat_id=t.treat_id and c.doc_id=d.doc_id and c.nur_id=n.nur_id and c.pat_id=p.pat_id and c.pat_id=" + pat_id;
		List result = new ArrayList();
		setDataSource(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List extractData(ResultSet rs) throws SQLException{
				while(rs.next()) {
					TreatChart tc = new TreatChart();
					tc.setChart_contents(rs.getString("chart_contents"));
					tc.setChart_id(rs.getString("chart_id"));
					tc.setDoc_name(rs.getString("doc_name"));
					tc.setMajor_treat(rs.getString("major_treat"));
					tc.setNur_name(rs.getString("nur_name"));
					tc.setPat_jumin(rs.getLong("pat_jumin"));
					tc.setPat_name(rs.getString("pat_name"));
					tc.setTreat_contents(rs.getString("treat_contents"));
					tc.setTreat_date(rs.getDate("treat_date"));
					result.add(tc);
				}
				return result;
			}
		});
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public TreatChart showChart(String chart_id) {
		TreatChart result = new TreatChart();
		String sql = "select c.chart_id, t.treat_date, t.treat_contents,c.chart_contents, p.pat_name, p.pat_jumin, d.doc_name, d.major_treat, n.nur_name " + 
				" from charts c, treatments t, doctors d, nurse n, patients p " + 
				" where c.treat_id=t.treat_id and c.doc_id=d.doc_id and c.nur_id=n.nur_id and c.pat_id=p.pat_id and c.chart_id=" +"'"+chart_id+"'";
		setDataSource(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			public TreatChart extractData(ResultSet rs) throws SQLException{
					rs.next();
					result.setChart_contents(rs.getString("chart_contents"));
					result.setChart_id(rs.getString("chart_id"));
					result.setDoc_name(rs.getString("doc_name"));
					result.setMajor_treat(rs.getString("major_treat"));
					result.setNur_name(rs.getString("nur_name"));
					result.setPat_jumin(rs.getLong("pat_jumin"));
					result.setPat_name(rs.getString("pat_name"));
					result.setTreat_contents(rs.getString("treat_contents"));
					result.setTreat_date(rs.getDate("treat_date"));
				return result;
			}
		});
		return result;
	}
	@Autowired
	private PatientRepository patRepo;
	@Autowired
	private DoctorsRepository docRepo;
	@Autowired
	private TreatmentsRepository treatRepo;
	@Autowired
	private NurseRepository nurRepo;
	@Autowired
	private ChartsRepository chartRepo;
	
	public void insertChart(TreatChart chart) {
		/*
		 * input data: 
		 * 		chart_id, chart_contents,	<< charts table
		 * 		treat_contests,  			<< treatments table
		 * 		pat_name, pat_jumin, 		<< patients table
		 * 		doc_name, major_treat, 		<< doctors table
		 * 		nur_name					<< nurse table
		 * 
		 * charts table columns: chart_id, treat_id(PF), doc_id(PF), pat_id(PF), nur_id(F), chart_contents
		 * treatments table columns: treat_id, pat_id, doc_id, treat_contents, treat_date
		 * 
		 *  we have to add treatment to put the chart
		 *  
		 *  ---treatments---
		 *  treat_id = chart_id.substring(2)
		 *  pat_id = findByPat_nameAndPat_jumin(pat_name,pat_jumin)
		 *  doc_id = findByDoc_nameAndMajor_treat(doc_name, major_treat)
		 *  treate_date = new Date()
		 *  
		 *  ---charts---
		 *  chart_id = chart_id
		 *  treat_id = treat_id
		 *  doc_id = doc_id
		 *  pat_id = pat_id
		 *  nur_id = NurseRepository.findbyName(nur_name)
		 * 	chart_contents = chart_contents
		 * */
		Treatments new_treat = new Treatments();
		Patient temp_pat = patRepo.findByNameAndJumin(chart.getPat_name(), chart.getPat_jumin());
		Doctors temp_doc = docRepo.findByNameAndMajor(chart.getDoc_name(), chart.getMajor_treat());
		new_treat.setTreat_id(Long.parseLong(chart.getChart_id().substring(2)));
		new_treat.setPat_id(temp_pat.getPat_id());
		new_treat.setDoc_id(temp_doc.getDoc_id());
		new_treat.setTreat_date(new Date());
		new_treat.setTreat_contents(chart.getTreat_contents());
		treatRepo.save(new_treat);
		System.out.println("new_treatment record inserted= " +new_treat);
		Charts new_chart = new Charts();
		Nurse temp_nur = nurRepo.findByName(chart.getNur_name());
		new_chart.setChart_id(chart.getChart_id());
		new_chart.setTreat_id(new_treat.getTreat_id());
		new_chart.setDoc_id(temp_doc.getDoc_id());
		new_chart.setPat_id(temp_pat.getPat_id());
		new_chart.setNur_id(temp_nur.getNur_id());
		new_chart.setChart_contents(chart.getChart_contents());
		chartRepo.save(new_chart);
		System.out.println("new_chart record inserted= " +new_chart);
	}
}
