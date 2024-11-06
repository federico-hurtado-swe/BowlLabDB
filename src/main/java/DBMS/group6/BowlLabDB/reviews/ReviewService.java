package DBMS.group6.BowlLabDB.reviews;

import DBMS.group6.BowlLabDB.reviews.models.Review;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Create a new review
    public void createReview(Review review) {
        reviewRepository.createReview(review);
    }

    // Retrieve all reviews
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    // Retrieve reviews by customer ID
    public List<Review> findReviewsByCustomer(int customerId) {
        return reviewRepository.findByCustomer(customerId);
    }

    // Retrieve reviews by date
    public List<Review> findReviewsByDate(LocalDate date) {
        return reviewRepository.findByDate(date);
    }

    // Check if a review exists by ID
    public boolean findReview(int reviewId) {
        return reviewRepository.findReview(reviewId);
    }

    // ReviewService.java
    public void updateReview(Review review) {
        reviewRepository.updateReview(review);
    }


    // Delete a review by ID
    public void deleteReviewById(int reviewId) {
        reviewRepository.deleteReviewById(reviewId);
    }
}
