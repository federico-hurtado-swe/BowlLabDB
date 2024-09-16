package DBMS.group6.BowlLabDB.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import DBMS.group6.BowlLabDB.menu.models.MenuItem;

/*
 * This class is responsible for managing the storing/retrieval of 
 * data within the menu. Currently, this is being done with an ArrayList, but
 * in the future we need to put the data in a persistent DB. 
 */
@Repository
public class MenuRepository {
    
    // hold all the menu items
    private List<MenuItem> menu = new ArrayList<>();

    MenuItem findById(Integer id) {
        // return the first item in the menu that has the requested id
        return menu.stream().filter(item -> item.id() == id).findFirst().get();
    }

    List<MenuItem> findAll() {
        return menu;
    }

    void addItem(MenuItem item) {
        menu.add(item);
    }

    void deleteItem(Integer id) {
        // remove item from menu if the item has the requested id
        menu.removeIf(item -> item.id().equals(id));
    }

    void updateItem(MenuItem newItem, Integer id) {
        MenuItem oldItem = this.findById(id);
        menu.set(menu.indexOf(oldItem), newItem);
    }
}
