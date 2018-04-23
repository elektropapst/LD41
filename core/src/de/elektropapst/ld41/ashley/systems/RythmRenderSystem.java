package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.RythmComponent;
import de.elektropapst.ld41.assets.enums.Textures;
import de.elektropapst.ld41.input.CommandKeys;

public class RythmRenderSystem extends EntitySystem {

    private Batch spriteBatch;
    private ImmutableArray<Entity> rythmEntities;
    private Entity rythmEntity;

    private ShapeRenderer shapeRenderer;

    public RythmRenderSystem() {
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        rythmEntities = engine.getEntitiesFor(Family.all(RythmComponent.class).get());
    }

    float beatAlpha = 0.0f;
    int lastBeat = 0;
    Color recordingColor = new Color(1, 0, 0, 0);
    Color executingColor = new Color(0, 1, 0, 0);

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (rythmEntities.size() == 1) {
            rythmEntity = rythmEntities.get(0);
            RythmComponent rc = ComponentMappers.rythm.get(rythmEntity);

            if(rc.currentBeat != lastBeat) {
                beatAlpha = 1.0f;
                lastBeat = rc.currentBeat;
            }

            Color color;
            if(rc.state == RythmComponent.States.EXECUTING) {
                color = executingColor;
            } else {
                color = recordingColor;
            }
            color.a = beatAlpha;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(10, 10, Gdx.graphics.getWidth()-20, Gdx.graphics.getHeight()-20);
            if(rc.state == RythmComponent.States.RECORDING) {
                shapeRenderer.rect(20, 20, Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40);
            }
            shapeRenderer.end();

            if(beatAlpha > 0.0) {
                beatAlpha -= (2.0 * deltaTime);
            }

            spriteBatch.begin();

            int targetSize = 80;
            int yOffset = 80;
            for (int i = 0; i < rc.numBeats; i++) {
                boolean highlightCurrentElement = i == rc.currentBeat;
                boolean isEmpty = rc.keyChain[i] == CommandKeys.EMPTY;
                Texture texture;

                if(isEmpty) {
                    if(highlightCurrentElement) {
                        texture = Statics.asset.getTexture(Textures.BTN_EMPTY_LIGHT);
                    } else {
                        texture = Statics.asset.getTexture(Textures.BTN_EMPTY);
                    }
                } else {
                    texture = rc.keyChain[i].texture;
                }

                spriteBatch.draw(texture, i * targetSize + (Gdx.graphics.getWidth() / 2 - ((rc.numBeats / 2) * targetSize)), Gdx.graphics.getHeight() - targetSize - yOffset, targetSize, targetSize);
            }

            spriteBatch.end();
        }
    }

}
