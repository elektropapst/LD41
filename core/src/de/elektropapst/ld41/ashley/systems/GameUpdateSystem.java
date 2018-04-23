package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.*;

public class GameUpdateSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    public GameUpdateSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(
                Family.one(PlayerComponent.class, EnemyComponent.class, BulletComponent.class).all(
                        VelocityComponent.class,
                        TargetTileComponent.class,
                        PositionComponent.class
                ).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : entities) {
            boolean isPlayer = ComponentMappers.player.has(entity);
            TargetTileComponent tc = ComponentMappers.target.get(entity);
            PositionComponent pc = ComponentMappers.position.get(entity);
            VelocityComponent vc = ComponentMappers.velocity.get(entity);

            if (tc.targetX > pc.x) {
                pc.x += vc.velocity * deltaTime;
            }
            if (tc.targetX < pc.x) {
                pc.x -= vc.velocity * deltaTime;
            }
            if (tc.targetY > pc.y) {
                pc.y += vc.velocity * deltaTime;
            }
            if (tc.targetY < pc.y) {
                pc.y -= vc.velocity * deltaTime;
            }

            if (!isPlayer && (pc.x < 0 || pc.x > GameRenderSystem.MAP_WIDTH)) {
                Statics.ashley.removeEntity(entity);
            }

            if(ComponentMappers.rotation.has(entity)) {
                RotationComponent rc = ComponentMappers.rotation.get(entity);
                rc.rotation = (rc.rotation + (rc.rotationInc * deltaTime)) % 360;
            }
        }
    }
}
