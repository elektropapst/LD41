package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.ComboComponent;
import de.elektropapst.ld41.ashley.components.HealthComponent;
import de.elektropapst.ld41.ashley.components.PlayerComponent;
import de.elektropapst.ld41.assets.enums.BitmapFonts;
import de.elektropapst.ld41.assets.enums.Textures;

public class PlayerUIRenderSystem extends EntitySystem{

    private Batch spriteBatch;
    private BitmapFont font;
    private ImmutableArray<Entity> entities;

    public PlayerUIRenderSystem() {
        spriteBatch = new SpriteBatch();
        font = Statics.asset.getFont(BitmapFonts.GAMEFONT);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(
                PlayerComponent.class,
                HealthComponent.class,
                ComboComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(entities.size() == 1) {

            HealthComponent hc = ComponentMappers.health.get(entities.get(0));
            ComboComponent cc = ComponentMappers.combo.get(entities.get(0));
            PlayerComponent pc = ComponentMappers.player.get(entities.get(0));

            spriteBatch.begin();
            for (int i = 0; i < cc.maxCombo; i++) {
                Texture tex = Statics.asset.getTexture( i < cc.combo ? Textures.BLUE_SQUARE : Textures.GRAY_SQUARE);
                spriteBatch.draw(tex, (i*32)+220+8, Gdx.graphics.getHeight()-64, 32, 32);
            }


            for (int i = 0; i < hc.maxHealth; i++) {
                Texture tex = Statics.asset.getTexture( i < hc.health ? Textures.RED_SQUARE : Textures.GRAY_SQUARE);
                spriteBatch.draw(tex, (i*32)+220+8, Gdx.graphics.getHeight()-64-32, 32, 32);
            }

            font.draw(spriteBatch, "Combo", 0, Gdx.graphics.getHeight()-(font.getLineHeight()), 220, Align.right, false);
            font.draw(spriteBatch, "Health", 0, Gdx.graphics.getHeight()-32-(font.getLineHeight()), 220, Align.right, false);
            font.draw(spriteBatch, "Score: " + pc.score, 0, 10+font.getLineHeight(), Gdx.graphics.getWidth(), Align.center, false);
            spriteBatch.end();
        }
    }
}
