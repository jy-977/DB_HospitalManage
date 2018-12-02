package com.example.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public List ShowAllPatient() {
		String sql = "SELECT * FROM PATIENTS";
		List<String> patientList = new ArrayList();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List extractData(ResultSet rs) throws SQLException{
				while(rs.next()) {
					String pat_name = rs.getString("PAT_NAME");
					patientList.add(pat_name);
				}
				return patientList;
			}
		});
		return patientList;
	}
}
