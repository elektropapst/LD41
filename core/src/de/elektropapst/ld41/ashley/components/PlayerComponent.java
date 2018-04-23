package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.assets.enums.Textures;

public class PlayerComponent implements Component, Pool.Poolable{
    public int score = 0;

    @Override
    public void reset() {
        score = 0;
    }
}
