package com.example.DB;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.DB.Patient;
 
@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {
        //Patient findByName(String pat_name);
	Patient findByNameAndJumin(String pat_name,long pat_jumin);
}