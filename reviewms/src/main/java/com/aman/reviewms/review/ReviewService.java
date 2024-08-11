package com.aman.reviewms.review;

import java.util.List;

public interface ReviewService {
	 List<Review> getAllReviews(Long companyId);
	 boolean saveReview(Long companyId,Review review); 
	 Review getreviewId(Long reviewId);
	 boolean deleteReviewById(Long reviewId);
	boolean updateReview(Long reviewId, Review ss);

}
