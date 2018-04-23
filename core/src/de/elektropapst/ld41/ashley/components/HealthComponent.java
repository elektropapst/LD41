package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int maxHealth = 6;
    public int health = maxHealth;

    @Override
    public void reset() {
        maxHealth = 6;
        health = maxHealth;
    }
}
