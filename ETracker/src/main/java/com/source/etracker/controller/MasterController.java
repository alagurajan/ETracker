package com.source.etracker.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String saveCountry(@Valid @ModelAttribute("country") Country country , BindingResult bindingResult, RedirectAttributes redirAttr) {
		if(bindingResult.hasErrors())
		{
			redirAttr.addFlashAttribute("errormessage", bindingResult.getFieldError().getDefaultMessage());
			return "redirect:add";
		}
		else
		{
			try
			{
				countryRepository.save(country);
				redirAttr.addFlashAttribute("successmessage","Country \""+country.getCountryName()+"\" added successfully");
			}
			catch(DataIntegrityViolationException e)
			{
				System.out.println("Country is alreay available in our database");
				redirAttr.addFlashAttribute("errormessage", "Country \""+country.getCountryName()+"\" is alreay available in our database");
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
	public String deleteEmployee(@RequestParam("countryId") Long theId, RedirectAttributes redirAttr){
		Country tobeDelete	=	countryRepository.getOne(theId);
		redirAttr.addFlashAttribute("successmessage", "Country \""+tobeDelete.getCountryName()+"\" deleted successfully");
		countryRepository.deleteById(theId);
		return "redirect:list";
	}
	
	
	@PostMapping("/city/save")
	public String saveCity(@Valid  @ModelAttribute("city") City city , BindingResult bindingResult ,  @RequestParam("country_id") Long theId,  RedirectAttributes redirAttr ) {
		
		if(theId==0)
		{
			redirAttr.addFlashAttribute("errormessage", "Please select the Country");
			return "redirect:add";
		}
		if(bindingResult.hasErrors())
		{
			redirAttr.addFlashAttribute("errormessage", bindingResult.getFieldError().getDefaultMessage());
			return "redirect:add";
		}
		else
		{
			try
			{
				countryRepository.findById(theId).map(country -> {
				city.setCountry(country);
	            return cityRepository.save(city);
				});
				redirAttr.addFlashAttribute("successmessage","City \""+city.getCityName()+"\" added successfully");
			}
			catch(DataIntegrityViolationException e) {
				System.out.println("Country is alreay available in our database");
				redirAttr.addFlashAttribute("errormessage", "City \""+city.getCityName()+"\" is alreay available in our database");
				return "redirect:add";
			}
			return "redirect:list";
		}
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
		model.addAttribute("city", cityRepository.findAll(new Sort("Country").ascending()));
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
	public String deleteCity(@RequestParam("cityId") Long theId, RedirectAttributes redirAttr){
		City tobeDelete	=	cityRepository.getOne(theId);
		redirAttr.addFlashAttribute("successmessage", "City \""+tobeDelete.getCityName()+"\" deleted successfully");
		cityRepository.deleteById(theId);
		return "redirect:list";
	}

}
