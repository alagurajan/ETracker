package com.source.etracker.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.source.etracker.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
		Page<City> findByCountryId(Long countryId, Pageable pageable);
	    Optional<City> findByIdAndCountryId(Long id, Long CountryId);
	    List<City> findAll(Sort sort);
}
