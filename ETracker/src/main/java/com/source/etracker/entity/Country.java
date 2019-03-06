package com.source.etracker.entity;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class Country {

	private int id;
	private String countryName;
	private List<City> cities;
	
	
}
