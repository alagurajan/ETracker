package com.source.etracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.source.etracker.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
