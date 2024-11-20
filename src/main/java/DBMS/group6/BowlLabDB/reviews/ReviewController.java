package DBMS.group6.BowlLabDB.reviews;

import DBMS.group6.BowlLabDB.reviews.models.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Create a new review
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review created successfully.");
    }

    // find all reviews
    @GetMapping("/find/all")
    public ResponseEntity<List<Review>> findAllReviews() {
        List<Review> reviews = reviewService.findAllReviews();
        return ResponseEntity.ok(reviews);
    }

    //update a review
    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        if (reviewService.findReview(review.id())) {
            reviewService.updateReview(review);
            return ResponseEntity.ok("Review updated successfully.");
        } 
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }
    }

    // Get reviews by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Review>> getReviewsByCustomer(@PathVariable int customerId) {
        List<Review> reviews = reviewService.findReviewsByCustomer(customerId);
        return ResponseEntity.ok(reviews);
    }


    // Check if a review exists by ID
    @GetMapping("/find/{reviewId}")
    public ResponseEntity<Boolean> findReview(@PathVariable int reviewId) {
        boolean exists = reviewService.findReview(reviewId);
        return ResponseEntity.ok(exists);
    }

    // Delete a review by ID
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable int reviewId) {
        if (reviewService.findReview(reviewId)) {
            reviewService.deleteReviewById(reviewId);
            return ResponseEntity.ok("Review deleted successfully.");
        } 
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found.");
        }
    }

    // Get review report
    @GetMapping("/review-report")
    public ResponseEntity<Map<Integer, Integer>> getReviewReport() {
        Map<Integer, Integer> reviewReport = reviewService.getReviewReport();
        return ResponseEntity.ok(reviewReport);
    }
}
