package com.aman.reviewms.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.demo.company.Company;
//import com.example.demo.company.CompanyService;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;
	
//	@Autowired
//	private CompanyService companyService;
	@Override
	public List<Review> getAllReviews(Long companyId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);
		return reviews;
	}
	@Override
	public boolean saveReview(Long companyId, Review review) {
		//Company company = companyService.getCompanyById(companyId);
		
		if (companyId!=null) {
			review.setCompanyId(companyId);
			reviewRepository.save(review);
			return true;
		}else {return false;}
	}
	@Override
	public Review getreviewId( Long reviewId) {
		return reviewRepository.findById(reviewId).orElse(null);
	}
	@Override
	public boolean deleteReviewById( Long reviewId) {
		Review review =reviewRepository.findById(reviewId).orElse(null);
		if (review!=null) {
			reviewRepository.delete(review);
			return true;
		}else {return false;}
		
		
//		if (companyService.getCompanyById(companyId) != null 
//				&& reviewRepository.existsById(reviewId)) {
//			Review review=reviewRepository.findById(reviewId).orElse(null);
//			Company company=review.getCompany();
//			company.getReviews().remove(review);
//			review.setCompany(null);
//			companyService.updateCompany(company, companyId);
//			reviewRepository.deleteById(reviewId);
//			return true;
//		}else{
//	}
	}
	@Override
	public boolean updateReview(Long reviewId, Review updatedReview) {
		Review review =reviewRepository.findById(reviewId).orElse(null);
		if (reviewId!=null) {
			review.setTitle(updatedReview.getTitle());
			review.setDescription(updatedReview.getDescription());
			review.setRating(updatedReview.getRating());
			review.setCompanyId(updatedReview.getCompanyId());
			reviewRepository.save(updatedReview);
			return true;
		}else {
			return false;
		}
		
	}
	
}
