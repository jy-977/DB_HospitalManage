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
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pat_id;
	@Column(name="pat_name")
	private String name;
	private String pat_gen;
	@Column(name="pat_jumin")
	private long jumin;
	private String pat_addr;
	private String pat_phone;
	private String pat_email;
	private String pat_job;

}
