package DBMS.group6.BowlLabDB.menu;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import DBMS.group6.BowlLabDB.menu.models.MenuItem;
import jakarta.validation.Valid;

/*
 * This class is the entry point of the REST API calls that
 * are related to updating and viewing the menu
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Retrieve all menu items
    @GetMapping("/find/all")
    List<MenuItem> findAll() {
        return menuService.findAll();
    }

    // Retrieve a menu item by ID
    @GetMapping("/find/{id}")
    MenuItem findById(@PathVariable Integer id) {
        Optional<MenuItem> menuItem = menuService.findById(id);
        return menuItem.orElseThrow(() -> new IllegalArgumentException("Menu item with ID " + id + " not found."));
    }

    // Add a new menu item
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addItem")
    void addItem(@Valid @RequestBody MenuItem menuItem) {
        menuService.addItem(menuItem);
    }

    // Update an existing menu item by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updateItem/{id}")
    void updateItem(@Valid @RequestBody MenuItem menuItem, @PathVariable Integer id) {
        menuService.updateItem(menuItem, id);
    }

    // Delete a menu item by ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) {
        menuService.delete(id);
    }
}
