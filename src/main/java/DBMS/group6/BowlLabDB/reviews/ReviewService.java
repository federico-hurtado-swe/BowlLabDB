package DBMS.group6.BowlLabDB.reviews;

import DBMS.group6.BowlLabDB.reviews.models.Review;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Get review report
    public Map<Integer, Integer> getReviewReport() {
        List<Review> reviews = reviewRepository.findAll();

        // Initialize a map with all star ratings (1-5) defaulting to 0
        Map<Integer, Integer> reviewReport = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            reviewReport.put(i, 0);
        }

        // Populate the map with actual counts
        for (Review review : reviews) {
            int starsGiven = review.stars_given();
            reviewReport.put(starsGiven, reviewReport.getOrDefault(starsGiven, 0) + 1);
        }

        return reviewReport;
    }
}
