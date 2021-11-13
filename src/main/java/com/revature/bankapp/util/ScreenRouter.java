package com.revature.bankapp.util;

import com.revature.bankapp.screens.Screen;
import com.revature.bankapp.util.collections.LinkedList;

public class ScreenRouter {

    LinkedList<Screen> screens = new LinkedList<>();

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void navigate(String route) throws Exception {
        for(int i = 0; i < screens.size(); i++) {
            Screen currentScreen = screens.get(i);
            if(currentScreen.getRoute().equals(route)) {
                currentScreen.render();
            }
        }
    }
}
