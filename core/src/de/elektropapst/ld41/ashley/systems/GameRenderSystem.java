package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.*;
import de.elektropapst.ld41.assets.enums.Textures;

public class GameRenderSystem extends EntitySystem {

    public static final int TILE_SIZE = 64;
    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 12;

    private Batch spriteBatch;
    private ImmutableArray<Entity> entities;

    private Texture background1, background2;
    private Texture background1D1, background2D1;

    private TiledMapRenderer renderer;
    private OrthographicCamera camera;

    private static class BasicTile extends StaticTiledMapTile {

        private static final TextureRegion[][] tiles = TextureRegion.split(Statics.asset.getTexture(Textures.TILEMAP), TILE_SIZE, TILE_SIZE);
        public static final BasicTile BORDER = new BasicTile(tiles[0][0]);

        public BasicTile(TextureRegion textureRegion) {
            super(textureRegion);
        }
    }


    public GameRenderSystem(OrthographicCamera camera) {
        this.camera = camera;

        spriteBatch = new SpriteBatch();
        background1 = Statics.asset.getTexture(Textures.BG_SPARKLES);
        background2 = Statics.asset.getTexture(Textures.BG_SPARKLES);
        background1D1 = Statics.asset.getTexture(Textures.BG_SPARKLES);
        background2D1 = Statics.asset.getTexture(Textures.BG_SPARKLES);


        BasicTile tile = BasicTile.BORDER;
        TiledMapTileLayer layer = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, TILE_SIZE);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (y == 0 || y == layer.getHeight() - 1) {
                    cell.setTile(tile);
                }
                layer.setCell(x, y, cell);
            }
        }
        TiledMap map = new TiledMap();
        map.getLayers().add(layer);

        renderer = new OrthogonalTiledMapRenderer(map);

        camera.translate((Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 2);
        camera.update();

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(
                Family.all(
                        RenderableComponent.class,
                        TargetTileComponent.class,
                        PositionComponent.class
                ).get());
    }

    float xCoordBG1 = 0;
    float xCoordBG2 = Gdx.graphics.getWidth();
    float scrollSpeed = 250;
    float xCoordBG1D1 = 0;
    float xCoordBG2D1 = Gdx.graphics.getWidth();
    float scrollSpeedD1 = 150;

    Color backgroundTint = new Color(1, 1, 1, 1);
    Color backgroundTintD1 = new Color(1, 1, 1, 1);
    float cummulativeDelta;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        xCoordBG1 -= scrollSpeed * deltaTime;
        xCoordBG2 -= scrollSpeed * deltaTime;
        xCoordBG1D1 -= scrollSpeedD1 * deltaTime;
        xCoordBG2D1 -= scrollSpeedD1 * deltaTime;

        if (xCoordBG1 < -background1.getWidth()) xCoordBG1 = Gdx.graphics.getWidth();
        if (xCoordBG2 < -background2.getWidth()) xCoordBG2 = Gdx.graphics.getWidth();
        if (xCoordBG1D1 < -background1D1.getWidth()) xCoordBG1D1 = Gdx.graphics.getWidth();
        if (xCoordBG2D1 < -background2D1.getWidth()) xCoordBG2D1 = Gdx.graphics.getWidth();

        cummulativeDelta += deltaTime;
        float vD = 0.25f * (MathUtils.sin(cummulativeDelta) + 3);
        float v = 0.25f * (MathUtils.sin(cummulativeDelta - MathUtils.PI) + 3);

        backgroundTintD1.set(vD, vD, vD, 1);
        backgroundTint.set(v, v, v, 1);

        spriteBatch.begin();
        spriteBatch.setColor(backgroundTintD1);
        spriteBatch.draw(background1D1, xCoordBG1D1, 0);
        spriteBatch.draw(background2D1, xCoordBG2D1, 0);
        spriteBatch.setColor(backgroundTint);
        spriteBatch.draw(background1, xCoordBG1, 0);
        spriteBatch.draw(background2, xCoordBG2, 0);
        spriteBatch.setColor(Color.WHITE);
        spriteBatch.end();


        RotationComponent rc;
        for (Entity entity : entities) {
            PositionComponent pc = ComponentMappers.position.get(entity);

            TextureRegion textureRegion;
            if (ComponentMappers.player.has(entity)) {
                textureRegion = ComponentMappers.renderable.get(entity).textureRegion;
            } else {
                textureRegion = ComponentMappers.renderable.get(entity).textureRegion;
            }
            spriteBatch.begin();

            if (ComponentMappers.rotation.has(entity)) {
                rc = ComponentMappers.rotation.get(entity);
                spriteBatch.draw(textureRegion, pc.x * TILE_SIZE, pc.y * TILE_SIZE, textureRegion.getRegionWidth()/2, textureRegion.getRegionHeight()/2 , textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 1, 1, rc.rotation);
            } else {
                spriteBatch.draw(textureRegion, pc.x * TILE_SIZE, pc.y * TILE_SIZE);
            }
            spriteBatch.end();
        }
        camera.update();
        renderer.setView(camera);
        renderer.render();
    }

}
