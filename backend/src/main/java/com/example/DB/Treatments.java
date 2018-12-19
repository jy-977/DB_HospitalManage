package com.example.DB;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@IdClass(TreatmentKey.class)
public class Treatments {
	@Id
	long treat_id;
	@Id
	long pat_id;
	@Id
	long doc_id;
	String treat_contents;
	Date treat_date;
	
}
