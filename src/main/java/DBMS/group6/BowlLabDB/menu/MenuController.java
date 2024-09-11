package DBMS.group6.BowlLabDB.menu;

import java.util.List;

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

import jakarta.validation.Valid;


/*
 * This class is the entry point of the REST API calls that
 * are related to updating and viewing the menu
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {   
    
    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/find/all") // GET 
    List<MenuItem> findAll() {
        return menuRepository.findAll();
    }

    @GetMapping("/find/{id}") 
    MenuItem findById(@PathVariable Integer id) {
        return menuRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addItem")
    void addItem(@Valid @RequestBody MenuItem menuItem) { // requires a valid MenuItem as the request body or else returns 404
        menuRepository.addItem(menuItem);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updateItem/{id}")
    void updateItem(@Valid @RequestBody MenuItem menuItem, @PathVariable Integer id) {
        menuRepository.updateItem(menuItem, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("delete/{id}")
    void delete(@PathVariable Integer id) {
        menuRepository.deleteItem(id);
    }
}
