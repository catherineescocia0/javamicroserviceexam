package com.TechnicalTest.TechnicalExam.repository;

import com.TechnicalTest.TechnicalExam.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <CustomerModel, Long> {



}
