package com.aman.companyms.company;

import java.util.List;

import com.aman.companyms.company.dto.ReviewMessage;


public interface CompanyService {
	
	void save(Company company);
	List<Company> getAllCompany();
	Company getCompanyById(Long id);
	boolean deleteCompanyById(Long id);
	boolean updateCompany(Company updateCompany, Long id);
	void updateCompanyRating(ReviewMessage reviewMessage);

}
