package com.example.DB;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pat_id;
	private String pat_name;
	private String pat_gen;
	private long pat_jumin;
	private String pat_addr;
	private String pat_phone;
	private String pat_email;
	private String pat_job;
	public String getPat_name() {
		return pat_name;
	}
	public void setPat_name(String pat_name) {
		this.pat_name = pat_name;
	}
	public long getPat_id() {
		return pat_id;
	}
	public void setPat_id(long pat_id) {
		this.pat_id = pat_id;
	}
	public String getPat_gen() {
		return pat_gen;
	}
	public void setPat_gen(String pat_gen) {
		this.pat_gen = pat_gen;
	}
	public long getPat_ssn() {
		return pat_jumin;
	}
	public void setPat_ssn(int pat_ssn) {
		this.pat_jumin = pat_ssn;
	}
	public String getPat_addr() {
		return pat_addr;
	}
	public void setPat_addr(String pat_addr) {
		this.pat_addr = pat_addr;
	}
	public String getPat_hp() {
		return pat_phone;
	}
	public void setPat_hp(String pat_hp) {
		this.pat_phone = pat_hp;
	}
	public String getPat_email() {
		return pat_email;
	}
	public void setPat_email(String pat_email) {
		this.pat_email = pat_email;
	}
	public String getPat_job() {
		return pat_job;
	}
	public void setPat_job(String pat_job) {
		this.pat_job = pat_job;
	}

}
