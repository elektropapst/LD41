package de.elektropapst.ld41;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import de.elektropapst.ld41.screens.GameOverScreen;
import de.elektropapst.ld41.screens.GameScreen;
import de.elektropapst.ld41.screens.LoadingScreen;
import de.elektropapst.ld41.screens.Screens;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 Shooting (Player & enemy)
 Score
 Health Shoot bar
 Combo
 */
public class LDGame extends Game {

    public static LDGame game;
    private static Map<Screens, Screen> screens;

    @Override
    public void create() {
        game = this;
        Statics.initializeStatics();

        screens = new HashMap<>();
        screens.put(Screens.LOADING, new LoadingScreen());
        setCurrentScreen(Screens.LOADING);
    }

    public void initScreens() {
        screens.put(Screens.GAME, new GameScreen());
        screens.put(Screens.GAMEOVER, new GameOverScreen());
    }

    public static void setCurrentScreen(Screens screen) {
        game.setScreen(screens.get(screen));
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Screen screen : screens.values()) {
            screen.dispose();
        }
    }

}
