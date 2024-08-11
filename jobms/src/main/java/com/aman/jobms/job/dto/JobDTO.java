package com.aman.jobms.job.dto;

import java.util.List;

import com.aman.jobms.job.Job;
import com.aman.jobms.job.external.company.Company;
import com.aman.jobms.job.external.company.Review;

import lombok.Data;

@Data
public class JobDTO {
	private Long id;
	private String title;
	private String description;
	private String minsalry;
	private String maxsalary;
	private String location;
	private Company company;
	private List<Review> review;

}
