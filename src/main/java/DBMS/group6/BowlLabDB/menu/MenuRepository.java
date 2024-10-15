package DBMS.group6.BowlLabDB.menu;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import DBMS.group6.BowlLabDB.menu.models.MenuItem;

@Repository
public class MenuRepository {

    private final JdbcTemplate jdbcTemplate;

    public MenuRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to convert database rows into MenuItem objects
    private final RowMapper<MenuItem> rowMapper = (rs, rowNum) -> new MenuItem(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("ingredients"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("reward_value")
    );

    // Retrieve all menu items
    public List<MenuItem> findAll() {
        String sql = "SELECT * FROM menu";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Find a menu item by ID
    public Optional<MenuItem> findById(Integer id) {
        String sql = "SELECT * FROM menu WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id).stream().findFirst();
    }

    // Save or update a menu item
    public void save(MenuItem menuItem) {
        String sql = """
                INSERT INTO menu (id, name, ingredients, description, price, reward_value) 
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE 
                SET name = EXCLUDED.name, 
                    ingredients = EXCLUDED.ingredients,
                    description = EXCLUDED.description,
                    price = EXCLUDED.price,
                    reward_value = EXCLUDED.reward_value;
                """;
        jdbcTemplate.update(sql, 
                menuItem.id(), 
                menuItem.name(), 
                menuItem.ingredients(), 
                menuItem.description(), 
                menuItem.price(), 
                menuItem.rewardValue());
    }

    // Delete a menu item by ID
    public void deleteById(Integer id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Check if a menu item exists by ID
    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM menu WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
