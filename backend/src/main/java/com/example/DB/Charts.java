package com.example.DB;

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
@IdClass(ChartsKey.class)
public class Charts {
	@Id
	String chart_id;
	@Id
	long treat_id;
	@Id
	long pat_id;
	@Id
	long doc_id;
	long nur_id;
	String chart_contents;
}

