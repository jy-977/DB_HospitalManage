package com.example.DB;

import org.springframework.data.repository.CrudRepository;

public interface DoctorsRepository extends CrudRepository<Doctors, Long> {
	Doctors findByNameAndMajor(String name, String major);
}
