package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import de.elektropapst.ld41.LDGame;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.*;
import de.elektropapst.ld41.assets.enums.Sounds;
import de.elektropapst.ld41.screens.Screens;

public class CollisionSystem extends EntitySystem {
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;
    private ImmutableArray<Entity> bullets;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, PositionComponent.class).get());
        enemies = engine.getEntitiesFor(Family.all(EnemyComponent.class, PositionComponent.class).get());
        bullets = engine.getEntitiesFor(Family.all(BulletComponent.class, PositionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        if(players.size() == 1) {
            PositionComponent playerPosition = ComponentMappers.position.get(players.get(0));
            PositionComponent enemyPosition, bulletPosition;
            HealthComponent hc = ComponentMappers.health.get(players.get(0));
            float w = 0.5f;
            float h = 0.5f;
            Rectangle playerRect = new Rectangle(playerPosition.x-(w/2), playerPosition.y- (w/2), w, h);
            Rectangle enemyRect = new Rectangle();
            Rectangle bulletRect = new Rectangle();
            for(Entity enemy : enemies) {
                enemyPosition = ComponentMappers.position.get(enemy);
                enemyRect.set(enemyPosition.x-(w/2), enemyPosition.y- (w/2), w, h);
                if(playerRect.overlaps(enemyRect)) {
                    hc.health = MathUtils.clamp(hc.health-1, 0, hc.maxHealth);
                    Statics.ashley.removeEntity(enemy);
                    Statics.asset.getSound(Sounds.EXPLOSION).play();
                    if(hc.health == 0) {
                        LDGame.setCurrentScreen(Screens.GAMEOVER);
                    }
                }

                for(Entity bullet : bullets) {
                    bulletPosition = ComponentMappers.position.get(bullet);
                    bulletRect.set(bulletPosition.x+(w/2), bulletPosition.y - (h/2), w/2, h/2);
                    if(enemyRect.overlaps(bulletRect)) {
                        PlayerComponent playerComponent = ComponentMappers.player.get(players.get(0));
                        playerComponent.score++;
                        Statics.ashley.removeEntity(bullet);
                        Statics.ashley.removeEntity(enemy);
                        Statics.asset.getSound(Sounds.EXPLOSION).play();
                    }
                }
            }
        }
    }
}
