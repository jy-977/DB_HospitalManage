package com.example.DB;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Doctors {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long doc_id;
	@Column(name="major_treat")
	private String major;
	@Column(name="doc_name")
	private String name;
	private String doc_gen;
	private String doc_phone;
	private String doc_email;
	private String doc_position;
}
