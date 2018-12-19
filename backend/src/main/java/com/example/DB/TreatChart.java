package com.example.DB;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TreatChart {
	String chart_id;
	Date treat_date;
	String treat_contents;
	String chart_contents;
	String pat_name;
	long pat_jumin;
	String doc_name;
	String major_treat;
	String nur_name;
}
