package com.example.DB;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Nurse {
	@Id
	long nur_id;
	String major_job;
	@Column(name="nur_name")
	String name;
	String nur_gen;
	String nur_phone;
	String nur_email;
	String nur_position;
}
