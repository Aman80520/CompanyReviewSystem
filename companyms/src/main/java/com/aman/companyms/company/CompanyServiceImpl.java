package com.aman.companyms.company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.companyms.company.clients.ReviewClient;
import com.aman.companyms.company.dto.ReviewMessage;

import jakarta.ws.rs.NotFoundException;

//import com.example.demo.job.Job;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ReviewClient reviewClient;
	@Override
	public void save(Company company) {
		companyRepository.save(company);
		
	}

	@Override
	public List<Company> getAllCompany() {
		// TODO Auto-generated method stub
		return companyRepository.findAll();
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteCompanyById(Long id) {
		
		try {
			companyRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean updateCompany(Company updateCompany, Long id) {
		Optional<Company> companyoptional=companyRepository.findById(id);
		
		if (companyoptional.isPresent()) {
			Company company=companyoptional.get();
			//job.setId(updateJob.getId());
			company.setName(updateCompany.getName());
			company.setDescription(updateCompany.getDescription());
			//company.setJobs(updateCompany.getJobs());
			
			companyRepository.save(company);
			return true;
		}
		return false;
	}

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
		
		System.out.println(reviewMessage.getDescription());
		Company company =companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()->new NotFoundException("Company Not Found"+reviewMessage.getCompanyId()));
		double averageRating=reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
		company.setRating(averageRating);
		companyRepository.save(company);
	}

}
