package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.RythmComponent;
import de.elektropapst.ld41.assets.enums.Sounds;
import de.elektropapst.ld41.input.CommandKeys;

public class InputSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity rythmEntity;

    public InputSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(RythmComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (entities.size() == 1) {
            rythmEntity = entities.get(0);
            RythmComponent rc = ComponentMappers.rythm.get(rythmEntity);
            if(rc.state == RythmComponent.States.RECORDING && rc.keyChain[rc.currentBeat] == CommandKeys.EMPTY) {
                if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.MOVE;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.UP;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.DOWN;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.LEFT;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.RIGHT;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.COMMIT;
                }else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                    rc.keyChain[rc.currentBeat] = CommandKeys.SHOOT;
                }
            }
        }
    }
}
