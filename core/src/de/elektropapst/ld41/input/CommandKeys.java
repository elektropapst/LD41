package de.elektropapst.ld41.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.assets.enums.Textures;

public enum CommandKeys {
    EMPTY(Textures.BTN_EMPTY),
    SHOOT(Textures.BTN_SHOOT),
    MOVE(Textures.BTN_MOVE),
    LEFT(Textures.BTN_LEFT),
    RIGHT(Textures.BTN_RIGHT),
    UP(Textures.BTN_UP),
    DOWN(Textures.BTN_DOWN),
    COMMIT(Textures.BTN_COMMIT);

    public Texture texture;

    CommandKeys(Textures texture) {
        this.texture = Statics.asset.getTexture(texture);
    }
}
