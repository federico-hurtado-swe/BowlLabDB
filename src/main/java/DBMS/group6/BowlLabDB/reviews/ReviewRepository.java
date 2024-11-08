package DBMS.group6.BowlLabDB.reviews;

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import DBMS.group6.BowlLabDB.reviews.models.Review;

@Repository
public class ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReviewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to convert database rows into Review objects
    private final RowMapper<Review> rowMapperReview = (rs, rowNum) -> new Review(
            rs.getInt("id"),
            rs.getInt("written_by"),
            rs.getInt("stars_given"),
            rs.getString("description"));

    // Create a new review
    public void createReview(Review review) {
        String sql = "INSERT INTO Reviews (written_by, stars_given, description) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, review.written_by(), review.stars_given(), review.description());
    }

    // Retrieve all reviews
    public List<Review> findAll() {
        String sql = "SELECT * FROM Reviews";
        return jdbcTemplate.query(sql, rowMapperReview);
    }

    // Retrieve reviews by customer ID
    @SuppressWarnings("deprecation")
    public List<Review> findByCustomer(int customerId) {
        String sql = "SELECT * FROM Reviews WHERE written_by = ?";
        return jdbcTemplate.query(sql, new Object[] { customerId }, rowMapperReview);
    }

    // Retrieve reviews by date
    @SuppressWarnings("deprecation")
    public List<Review> findByDate(LocalDate date) {
        String sql = "SELECT * FROM Reviews WHERE date = ?";
        return jdbcTemplate.query(sql, new Object[] { date }, rowMapperReview);
    }

    // Check if a review exists by ID
    @SuppressWarnings("deprecation")
    public boolean findReview(int id) {
        String sql = "SELECT COUNT(*) FROM Reviews WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);
        return count != null && count > 0;
    }

    // update a review
    public void updateReview(Review review) {
        String sql = "UPDATE Reviews SET stars_given = ?, description = ?, written_by = ? WHERE id = ?";
        jdbcTemplate.update(sql, review.stars_given(), review.description(), review.written_by(), review.id());
    }

    // Delete a review by ID
    public void deleteReviewById(int id) {
        String sql = "DELETE FROM Reviews WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
