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
public class reserveService {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List dayoff_print() {
		String sql = "select doc.doc_id, doc.doc_phone,doc.doc_email, doc.doc_name, d_off.day_off " + 
				" from d_dayoff d_off, doctors doc " + 
				" where d_off.doc_id=doc.doc_id";
		List result = new ArrayList();
		
		setDataSource(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List extractData(ResultSet rs) throws SQLException{
				while(rs.next()) {
					DayoffDoctor dod = new DayoffDoctor();
					dod.setDoc_id(rs.getLong("doc_id"));
					dod.setDoc_phone(rs.getString("doc_phone"));
					dod.setDoc_email(rs.getString("doc_email"));
					dod.setDoc_name(rs.getString("doc_name"));
					dod.setDay_off(rs.getDate("day_off"));
					result.add(dod);
				}
				return result;
			}
		});
		return result;
	}
}
