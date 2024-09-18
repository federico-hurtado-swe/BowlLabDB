package DBMS.group6.BowlLabDB.menu;

import org.springframework.stereotype.Service;

@Service
public class MenuService {
    
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
}
