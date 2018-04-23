package de.elektropapst.ld41.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.components.*;
import de.elektropapst.ld41.ashley.systems.*;
import de.elektropapst.ld41.assets.enums.Musics;
import de.elektropapst.ld41.assets.enums.Textures;

public class GameScreen extends ScreenAdapter {

//    private Music music;
    private OrthographicCamera camera;
    private RythmSystem rythmSystem = new RythmSystem();

    public GameScreen() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initAshleySystems();
    }

    private void initAshleySystems() {
        Statics.ashley.addSystem(new GameRenderSystem(camera));
        Statics.ashley.addSystem(new GameUpdateSystem());
        Statics.ashley.addSystem(new RythmRenderSystem());
        Statics.ashley.addSystem(rythmSystem);
        Statics.ashley.addSystem(new InputSystem());
        Statics.ashley.addSystem(new CollisionSystem());
        Statics.ashley.addSystem(new PlayerUIRenderSystem());
    }

    @Override
    public void show() {
        super.show();
        Statics.ashley.removeAllEntities();

        // INITIALIZE GAME
        Entity rythmEntity = Statics.ashley.createEntity();
        RythmComponent rythmComponent = Statics.ashley.createComponent(RythmComponent.class);
        rythmEntity.add(rythmComponent);
        Statics.ashley.addEntity(rythmEntity);

        Entity playerEntity = Statics.ashley.createEntity();
        PlayerComponent playerComponent = Statics.ashley.createComponent(PlayerComponent.class);
        HealthComponent healthComponent = Statics.ashley.createComponent(HealthComponent.class);
        TargetTileComponent targetTileComponent = Statics.ashley.createComponent(TargetTileComponent.class);
        PositionComponent positionComponent = Statics.ashley.createComponent(PositionComponent.class);
        VelocityComponent velocityComponent = Statics.ashley.createComponent(VelocityComponent.class);
        ComboComponent comboComponent = Statics.ashley.createComponent(ComboComponent.class);
        RenderableComponent renderableComponent = Statics.ashley.createComponent(RenderableComponent.class);

        renderableComponent.textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.SHIP));

        positionComponent.x = 1;
        positionComponent.y = 6;
        targetTileComponent.targetX = positionComponent.x;
        targetTileComponent.targetY = positionComponent.y;

        playerEntity.add(playerComponent);
        playerEntity.add(healthComponent);
        playerEntity.add(targetTileComponent);
        playerEntity.add(velocityComponent);
        playerEntity.add(positionComponent);
        playerEntity.add(renderableComponent);
        playerEntity.add(comboComponent);

        Statics.ashley.addEntity(playerEntity);
        rythmSystem.start();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.05f, 0.05f, .05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Statics.ashley.update(delta);
    }

}
