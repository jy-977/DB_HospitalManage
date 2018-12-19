package com.example.DB;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DayoffDoctor {
	Long doc_id;
	String doc_phone;
	String doc_email;
	String doc_name;
	Date day_off;
}
