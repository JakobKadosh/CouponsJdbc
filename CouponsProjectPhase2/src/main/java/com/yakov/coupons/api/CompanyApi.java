package com.yakov.coupons.api;

import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.Company;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.logic.CompanyController;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
<dependency>
<groupId>org.glassfish.jersey.core</groupId>
<artifactId>jersey-common</artifactId>
</dependency>
 */

@RestController
@RequestMapping("/companies")
public class CompanyApi {
	@Autowired
	private CompanyController companyController;

	@PostMapping
	public long addCompany(@RequestBody Company company) throws MyException {
		return companyController.addCompany(company);
	}
	
	@PutMapping
	public void updateCompany(@RequestBody Company company)throws MyException{
		companyController.updateCompany(company);
	}
	
	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId")long id)throws MyException{
		companyController.deleteCompany(id);
	}
	
	@GetMapping("/{companyId}")
	public Company getCompanyById(@PathVariable("companyId")long id)throws MyException{
		return companyController.getCompanyById(id);
	}
	
	@GetMapping("/company")
	public Company getCompanyByName(@RequestParam("name")String companyName)throws MyException{
		return companyController.getCompanyByName(companyName);
	}
	@GetMapping
	public List<Company> getAllCompanies()throws MyException{
		return companyController.getAllCompanies();
	}
	
	

}
