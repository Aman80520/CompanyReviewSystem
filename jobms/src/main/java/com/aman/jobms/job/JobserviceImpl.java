package com.aman.jobms.job;

import java.util.ArrayList;
//import java.lang.foreign.Linker.Option;
//import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aman.jobms.job.client.CompanyClients;
import com.aman.jobms.job.client.ReviewClients;
import com.aman.jobms.job.dto.JobDTO;
import com.aman.jobms.job.external.company.Company;
import com.aman.jobms.job.external.company.Review;
import com.aman.jobms.job.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobserviceImpl implements JobService{
	//private List<Job> jobs=new ArrayList<>();
	private long nextid =1L;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private CompanyClients companyClient;
	@Autowired
	private ReviewClients reviewClient;
	
	@Autowired
	RestTemplate restTemplate;
	int attempt=0;
	@Override
	public void save(Job job) {
		job.setId(nextid++);
		jobRepository.save(job);
		//jobs.add(job);
		//return "Job added successfully";
	}
	
	@Override
//	@CircuitBreaker(name="companyBreaker" ,fallbackMethod = "companybreakerFallback")
//	@Retry(name="companyBreaker",fallbackMethod = "companybreakerFallback")
	@RateLimiter(name="companyBreaker",fallbackMethod = "companybreakerFallback")
	public List<JobDTO> getAllJob() {
		System.out.println("attempt"+ ++attempt);
		List<Job> jobs=jobRepository.findAll();
		
		//List<JobWithCompanyDTO> jobWithCompanyDTOs=new ArrayList<>();
		
		return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
//        Company company = restTemplate.getForObject("http://localhost:8081/company/1",Company.class);
//        System.out.println("Company Name "+company.getName());
//        System.out.println("Company Id "+company.getId());
		//return jobWithCompanyDTOs;
	}
	
	public List<String> companybreakerFallback(Exception e){
		List<String> ls=new ArrayList<>();
		ls.add("Dummy fucked");
		
		return ls;
	}
	private JobDTO convertToDTO(Job job) {
		
		//JobWithCompanyDTO jobWithCompanyDTO =new JobWithCompanyDTO();
		//jobWithCompanyDTO.setJob(job);
		
		//RestTemplate restTemplate=new RestTemplate();
		Company company=companyClient.getCompany(job.getCompanyId());
		List<Review> review=reviewClient.getReview(job.getCompanyId());
//		Company company = restTemplate.
//		getForObject("http://COMPANYMS:8081/company/"+job.getCompanyId(),Company.class);
//		ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId="
//		+job.getCompanyId(), HttpMethod.GET,null,new ParameterizedTypeReference<List<Review>>() {
			
		//});
		//List<Review> review =reviewResponse.getBody();
		JobDTO jobDTO=JobMapper.mapToJobWithCompanyDTO(job, company,review);
		//jobDTO.setCompany(company);
		//jobWithCompanyDTOs.add(jobWithCompanyDTO);
		
		return jobDTO;
		
	}
	
	@Override
	public JobDTO getJobById(Long id) {
//		for (Job job : jobs) {
//			if (job.getId().equals(id)) {
//				return job;
//			}
//		}
		Job job = jobRepository.findById(id).orElse(null);
		return convertToDTO(job); 
		//return null;
	}

	@Override
	public boolean deleteJobById(Long id) {
//		Iterator<Job> iterator=jobs.iterator();
//		while (iterator.hasNext()) {
//			Job job=iterator.next();
//			if (job.getId().equals(id)) {
//				iterator.remove();
//				return true;
//			}
//			
//		}
		//jobs.remove(id);
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}

	@Override
	public boolean updateJob(Job updateJob, Long id) {
		Optional<Job> joboptional=jobRepository.findById(id);
		
				if (joboptional.isPresent()) {
					Job job=joboptional.get();
					//job.setId(updateJob.getId());
					job.setTitle(updateJob.getTitle());
					job.setDescription(updateJob.getDescription());
					job.setMinsalry(updateJob.getMinsalry());
					job.setMaxsalary(updateJob.getMaxsalary());
					job.setLocation(updateJob.getLocation());
					jobRepository.save(job);
					return true;
				}
		
		return false;
	}

	
	


}
