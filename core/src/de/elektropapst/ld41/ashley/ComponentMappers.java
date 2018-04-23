package de.elektropapst.ld41.ashley;

import com.badlogic.ashley.core.ComponentMapper;
import de.elektropapst.ld41.ashley.components.*;

public class ComponentMappers {
    public static final ComponentMapper<RythmComponent> rythm = ComponentMapper.getFor(RythmComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<ComboComponent> combo = ComponentMapper.getFor(ComboComponent.class);
    public static final ComponentMapper<TargetTileComponent> target = ComponentMapper.getFor(TargetTileComponent.class);
    public static final ComponentMapper<EnemyComponent> enemy = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<RotationComponent> rotation = ComponentMapper.getFor(RotationComponent.class);
    public static final ComponentMapper<RenderableComponent> renderable = ComponentMapper.getFor(RenderableComponent.class);
}
