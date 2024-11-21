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
        List<Map<String, Object>> rawData = reviewRepository.getReviewSummary();

        // Create a Map to store star ratings and their counts
        Map<Integer, Integer> reviewReport = new HashMap<>();

        // Initialize all star ratings (1-5) with 0
        for (int i = 1; i <= 5; i++) {
            reviewReport.put(i, 0);
        }

        // Populate the Map with data from the database
        for (Map<String, Object> row : rawData) {
            Integer starRating = (Integer) row.get("star_rating"); // Cast to Integer
            Integer totalReviews = ((Long) row.get("total_reviews")).intValue(); // Convert to Integer
            reviewReport.put(starRating, totalReviews);
        }

        return reviewReport;
    }
}
