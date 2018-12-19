package com.example.DB;

import org.springframework.data.repository.CrudRepository;

public interface NurseRepository extends CrudRepository<Nurse, Long> {
	Nurse findByName(String name);
}
