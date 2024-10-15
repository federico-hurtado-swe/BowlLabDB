package DBMS.group6.BowlLabDB.menu;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import DBMS.group6.BowlLabDB.menu.models.MenuItem;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    // Retrieve all menu items
    public List<MenuItem> findAll() {
        return menuRepository.findAll();
    }

    // Find a menu item by ID
    public Optional<MenuItem> findById(Integer id) {
        return menuRepository.findById(id);
    }

    // Add a new menu item
    public void addItem(MenuItem menuItem) {
        menuRepository.save(menuItem);
    }

    // Update an existing menu item by ID
    public void updateItem(MenuItem menuItem, Integer id) {
        if (menuRepository.existsById(id)) {
            menuRepository.save(new MenuItem(id, menuItem.name(), menuItem.ingredients(), 
                                             menuItem.description(), menuItem.price(), menuItem.rewardValue()));
        } else {
            throw new IllegalArgumentException("Menu item with ID " + id + " not found.");
        }
    }

    // Delete a menu item by ID
    public void delete(Integer id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Menu item with ID " + id + " not found.");
        }
    }
}
