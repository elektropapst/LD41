package de.elektropapst.ld41.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import de.elektropapst.ld41.ashley.systems.InputSystem;

public class GameInputProcessor extends InputAdapter {

    private InputSystem inputSystem;

    public GameInputProcessor(InputSystem inputSystem) {
        this.inputSystem = inputSystem;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                break;
            case Input.Keys.DOWN:
                break;
            case Input.Keys.LEFT:
                break;
            case Input.Keys.RIGHT:
                break;
        }

        return super.keyDown(keycode);
    }
}
