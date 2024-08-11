package com.aman.reviewms.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aman.reviewms.review.messaging.ReviewMessageProducer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	
	private ReviewService reviewService;
	private ReviewMessageProducer reviewMessageProducer;
	
	public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
		this.reviewService = reviewService;
		this.reviewMessageProducer = reviewMessageProducer;
	}

	
	
	@GetMapping
	public ResponseEntity<List<Review>> getAllReview(@RequestParam Long companyId){
		
		return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<String> addReview(@RequestParam Long companyId,@RequestBody Review review) {
		
		boolean isReview = reviewService.saveReview(companyId, review);
		if (isReview) {
			reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<>("Review added successfully",HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>("review not saved",HttpStatus.NOT_FOUND);
		}
		
		
	}

	@GetMapping("/reviews/{reviewId}")
	public ResponseEntity<Review> getReview(
			@PathVariable Long reviewId){
		
		return new ResponseEntity<>(reviewService.getreviewId(reviewId),HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
		boolean isReviewDeleted = reviewService.deleteReviewById(reviewId);
		if (isReviewDeleted) 
			return new ResponseEntity<>("Review Deleted Successfully",HttpStatus.OK);
		else 
			return new ResponseEntity<>("Not deleted Successfully",HttpStatus.NOT_FOUND);
		
		
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReviewId(@PathVariable Long reviewId,
			@RequestBody Review review){
		
	boolean isReview = reviewService.updateReview( reviewId, review);
	if (isReview) 
		return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
	else 
		return new ResponseEntity<>("Not updated Successfully",HttpStatus.NOT_FOUND);
	
	}
	@GetMapping("/averageRating")
	public Double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviewList =reviewService.getAllReviews(companyId);
		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
	}

}
