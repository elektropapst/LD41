package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class VelocityComponent implements Component, Pool.Poolable{
    public float velocity = 0.5f;

    @Override
    public void reset() {
        velocity = 0.5f;
    }
}
