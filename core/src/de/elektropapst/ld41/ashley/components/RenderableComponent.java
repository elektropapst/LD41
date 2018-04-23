package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.assets.enums.Textures;

public class RenderableComponent implements Component, Pool.Poolable {

    public TextureRegion textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.GRAY_SQUARE));

    @Override
    public void reset() {
        textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.GRAY_SQUARE));
    }
}
