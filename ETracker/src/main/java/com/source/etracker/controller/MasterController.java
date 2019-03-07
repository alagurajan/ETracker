package com.source.etracker.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.source.etracker.dao.CityRepository;
import com.source.etracker.dao.CountryRepository;
import com.source.etracker.entity.City;
import com.source.etracker.entity.Country;

@Controller
@RequestMapping("/master")
public class MasterController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@PostMapping("/country/save")
	public String saveCountry(@Valid @ModelAttribute("country") Country country , BindingResult bindingResult) {
		if(bindingResult.hasErrors())
		{
			return "redirect:add";
		}
		else
		{
			try
			{
				countryRepository.save(country);
			}
			catch(DataIntegrityViolationException e) {
				System.out.println("Country is alreay available in our database");
				return "redirect:add";
			}
			return "redirect:list";
		}
	}
	
	@GetMapping("/country/add")
	public String addCountry(Model model) {
		Country country	=	new Country();
		model.addAttribute("pageTitle", "Add Country");
		model.addAttribute("country", country);
		return "/master/country/add-country";
	}
	
	@GetMapping("/country/list")
	public String listCountry(Model model) {
		Country country	=	new Country();
		model.addAttribute("pageTitle", "Country Master");
		model.addAttribute("country", countryRepository.findAll());
		return "/master/country/list-country";
	}
	
	@GetMapping("/country/update")
	public String updateCountry(@RequestParam("countryId") Long theId, Model theModel){
		Optional<Country> theCountry	=	countryRepository.findById(theId);
		theModel.addAttribute("country", theCountry);
		
		return "/master/country/add-country";
	}
	
	@GetMapping("/country/delete")
	public String deleteEmployee(@RequestParam("countryId") Long theId){
		countryRepository.deleteById(theId);
		return "redirect:list";
	}
	
	
	@PostMapping("/city/save")
	public String saveCity(@RequestParam("country_id") Long theId, @ModelAttribute("city") City city ) {
		 countryRepository.findById(theId).map(country -> {
			city.setCountry(country);
            return cityRepository.save(city);
        });
		//cityRepository.save(city);
		return "redirect:list";
	}
	
	@GetMapping("/city/add")
	public String addCity(Model model) {
		City city	=	new City();
		model.addAttribute("countries", countryRepository.findAll());
		model.addAttribute("pageTitle", "Add City");
		model.addAttribute("city", city);
		return "/master/city/add-city";
	}
	
	@GetMapping("/city/list")
	public String listCity(Model model) {
		City city	=	new City();
		model.addAttribute("pageTitle", "city Master");
		model.addAttribute("city", cityRepository.findAll());
		return "/master/city/list-city";
	}
	
	@GetMapping("/city/update")
	public String updateCity(@RequestParam("cityId") Long theId,@RequestParam("countryId") Long theCountryId, Model theModel){
		theModel.addAttribute("countries", countryRepository.findAll());
		Optional<City> theCity	=	cityRepository.findById(theId);
		theModel.addAttribute("countryId", theCountryId);
		theModel.addAttribute("city", theCity);
		
		return "/master/city/add-city";
	}
	
	@GetMapping("/city/delete")
	public String deleteCity(@RequestParam("cityId") Long theId){
		cityRepository.deleteById(theId);
		return "redirect:list";
	}

}
