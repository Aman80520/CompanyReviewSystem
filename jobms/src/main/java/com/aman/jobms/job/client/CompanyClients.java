package com.aman.jobms.job.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aman.jobms.job.external.company.Company;

@FeignClient(name="COMPANYMS")
public interface CompanyClients {
	@GetMapping("/company/{id}")
	Company getCompany(@PathVariable("id") Long id);

}
