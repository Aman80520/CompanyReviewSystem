package com.aman.jobms.job.mapper;

import java.util.List;

import com.aman.jobms.job.Job;
import com.aman.jobms.job.dto.JobDTO;
import com.aman.jobms.job.external.company.Company;
import com.aman.jobms.job.external.company.Review;

public class JobMapper {
	public static JobDTO mapToJobWithCompanyDTO(Job job,Company company,List<Review> review) {
		JobDTO jobDTO=new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setMaxsalary(job.getMaxsalary());
		jobDTO.setMinsalry(job.getMinsalry());
		jobDTO.setCompany(company);
		jobDTO.setReview(review);
		//jobDTO.setReview(review);
		
		return jobDTO;
	}
}
