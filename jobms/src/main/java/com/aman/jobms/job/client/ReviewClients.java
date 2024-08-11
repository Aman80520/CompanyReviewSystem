package com.aman.jobms.job.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.aman.jobms.job.external.company.Review;

@FeignClient(name="REVIEWMS")
public interface ReviewClients {
	
	@GetMapping("/reviews")
	List<Review> getReview(@RequestParam("companyId") Long companyId);

	

}
