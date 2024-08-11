package com.aman.companyms.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

//import com.example.demo.job.Job;



@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	@PostMapping("/savecompany")
	public ResponseEntity<String> createCompany(@RequestBody Company company){
		companyService.save(company);
		return new ResponseEntity<>("Company Added Successfully",HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getAllCompany(Company company){
		
		return ResponseEntity.ok(companyService.getAllCompany());
		
		
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCompany(@RequestBody Company updatecompany ,@PathVariable Long id){
		boolean updated=companyService.updateCompany(updatecompany,id);
		if (updated) {
			
			return new ResponseEntity<>("company updated Successfully",HttpStatus.OK);
			
		}
		return new ResponseEntity<>("Company Not found",HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletecompany(@PathVariable Long id) {
		boolean isdeleted=companyService.deleteCompanyById(id);
		if (isdeleted) {
			return new ResponseEntity<>("Company successfully deleted ",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
		
		}
		
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Company> CompanyByid(@PathVariable Long id) {
		Company companybyId = companyService.getCompanyById(id);
		if (companybyId!=null) {
			return new ResponseEntity<>(companybyId,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
}
