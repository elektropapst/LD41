package de.elektropapst.ld41.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.elektropapst.ld41.ashley.ComponentMappers;

public class ComboComponent implements Component, Pool.Poolable {
    public int maxCombo = 6;
    public int combo = 0;

    @Override
    public void reset() {
        maxCombo = 6;
        combo = 0;
    }
}
