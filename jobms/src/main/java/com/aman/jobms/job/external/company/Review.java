package com.aman.jobms.job.external.company;

import lombok.Data;

@Data
public class Review {
	private Long id;
	private String title;
	private String description;
	private double rating;
	//private Long companyId;
	

}
