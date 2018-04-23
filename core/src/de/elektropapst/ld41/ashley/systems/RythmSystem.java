package de.elektropapst.ld41.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.ashley.ComponentMappers;
import de.elektropapst.ld41.ashley.components.*;
import de.elektropapst.ld41.assets.enums.Musics;
import de.elektropapst.ld41.assets.enums.Sounds;
import de.elektropapst.ld41.assets.enums.Textures;
import de.elektropapst.ld41.input.CommandKeys;
import de.elektropapst.ld41.input.Commands;
import javafx.geometry.Pos;

public class RythmSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private Entity rythmEntity;
    private Music music;
    private ImmutableArray<Entity> playerEntities;
    Music musicLvl0 = Statics.asset.getMusic(Musics.LVL0);
    Music musicLvl1 = Statics.asset.getMusic(Musics.LVL1);
    Music musicLvl2  = Statics.asset.getMusic(Musics.LVL2);
    Music musicLvl3  = Statics.asset.getMusic(Musics.LVL3);
    Music musicLvl4  = Statics.asset.getMusic(Musics.LVL4);
    Music musicLvl5  = Statics.asset.getMusic(Musics.LVL5);
    Music musicLvl6  = Statics.asset.getMusic(Musics.LVL6);

    public RythmSystem() {
        music = musicLvl0;
//        music = Statics.asset.getMusic(Musics.SELF);
    }

    public boolean isRunning() {
        return music.isPlaying();
    }

    public void start() {
        if(!isRunning()) music.play();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(RythmComponent.class).get());
        playerEntities = engine.getEntitiesFor(Family.all(
                PlayerComponent.class,
                PositionComponent.class,
                TargetTileComponent.class,
                ComboComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        if (entities.size() == 1) {
            rythmEntity = entities.get(0);
            RythmComponent rc = ComponentMappers.rythm.get(rythmEntity);
            rc.musicPosition = music.getPosition();

            ComboComponent cc = ComponentMappers.combo.get(playerEntities.get(0));

            if (rc.musicPosition > rc.lastBeat + rc.crotchet) {
                rc.lastBeat += rc.crotchet;
                fireNextBeat(rc, cc);
            } else if (rc.musicPosition < rc.lastBeat) {
                rc.lastBeat = 0;
                fireNextBeat(rc, cc);
            }
        }
    }

    private void fireNextBeat(RythmComponent rc, ComboComponent cc) {
        rc.currentBeat = (rc.currentBeat + 1) % rc.numBeats;
        if (rc.currentBeat == 0) {
            if(!music.isPlaying()) {

                switch (cc.combo) {
                    case 0 : music = musicLvl0; break;
                    case 1 : music = musicLvl1; break;
                    case 2 : music = musicLvl2; break;
                    case 3 : music = musicLvl3; break;
                    case 4 : music = musicLvl4; break;
                    case 5 : music = musicLvl5; break;
                    case 6: music = musicLvl6; break;
                    default: music = musicLvl6;
                }
//
                music.play();
            }
            spawnEnemy(cc);
            startExecution(rc);
            startRecording(rc);

            // Spawn new enemy

//            switch (rc.state) {
//                case RECORDING:
//                    startExecution(rc);
//                    break;
//                case EXECUTING:
//                    startRecording(rc);
//                    break;
//            }
        }
    }

    private void spawnMeteor(Textures textures, ComboComponent cc) {
        Entity enemyEntity = Statics.ashley.createEntity();
        EnemyComponent enemyComponent = Statics.ashley.createComponent(EnemyComponent.class);
        HealthComponent healthComponent = Statics.ashley.createComponent(HealthComponent.class);
        TargetTileComponent targetTileComponent = Statics.ashley.createComponent(TargetTileComponent.class);
        PositionComponent positionComponent = Statics.ashley.createComponent(PositionComponent.class);
        VelocityComponent velocityComponent = Statics.ashley.createComponent(VelocityComponent.class);
        RotationComponent rotationComponent = Statics.ashley.createComponent(RotationComponent.class);
        ComboComponent comboComponent = Statics.ashley.createComponent(ComboComponent.class);
        RenderableComponent renderableComponent = Statics.ashley.createComponent(RenderableComponent.class);

        renderableComponent.textureRegion = new TextureRegion(Statics.asset.getTexture(textures));

        float comboPercent = (float) cc.combo / (float) cc.maxCombo;

        positionComponent.x = GameRenderSystem.MAP_WIDTH;
        positionComponent.y = MathUtils.random(1, GameRenderSystem.MAP_HEIGHT - 2);
        velocityComponent.velocity = MathUtils.random(0.25f, 1.0f) * MathUtils.lerp(1.0f, 5.0f, comboPercent);
        targetTileComponent.targetX = -1;
        targetTileComponent.targetY = positionComponent.y;
        rotationComponent.rotationInc = MathUtils.random(-15, 15);

        enemyEntity.add(enemyComponent);
        enemyEntity.add(healthComponent);
        enemyEntity.add(comboComponent);
        enemyEntity.add(velocityComponent);
        enemyEntity.add(rotationComponent);
        enemyEntity.add(renderableComponent);
        enemyEntity.add(targetTileComponent);
        enemyEntity.add(positionComponent);
        Statics.ashley.addEntity(enemyEntity);

    }

    private void spawnShip(ComboComponent cc) {
        Entity enemyEntity = Statics.ashley.createEntity();
        EnemyComponent enemyComponent = Statics.ashley.createComponent(EnemyComponent.class);
        HealthComponent healthComponent = Statics.ashley.createComponent(HealthComponent.class);
        TargetTileComponent targetTileComponent = Statics.ashley.createComponent(TargetTileComponent.class);
        PositionComponent positionComponent = Statics.ashley.createComponent(PositionComponent.class);
        VelocityComponent velocityComponent = Statics.ashley.createComponent(VelocityComponent.class);
        RotationComponent rotationComponent = Statics.ashley.createComponent(RotationComponent.class);
        ComboComponent comboComponent = Statics.ashley.createComponent(ComboComponent.class);
        RenderableComponent renderableComponent = Statics.ashley.createComponent(RenderableComponent.class);

        if(MathUtils.randomBoolean(0.5f)) {
            renderableComponent.textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.ENEMY1));
        } else {
            renderableComponent.textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.ENEMY2));
        }


        float comboPercent = (float) cc.combo / (float) cc.maxCombo;

        positionComponent.x = GameRenderSystem.MAP_WIDTH;
        positionComponent.y = MathUtils.random(1, GameRenderSystem.MAP_HEIGHT - 2);
        velocityComponent.velocity = MathUtils.random(0.25f, 1.0f) * MathUtils.lerp(1.0f, 5.0f, comboPercent);
        targetTileComponent.targetX = -1;
        targetTileComponent.targetY = positionComponent.y;
        rotationComponent.rotationInc = 0;
        rotationComponent.rotation = 180;

        enemyEntity.add(enemyComponent);
        enemyEntity.add(healthComponent);
        enemyEntity.add(comboComponent);
        enemyEntity.add(velocityComponent);
        enemyEntity.add(renderableComponent);
        enemyEntity.add(rotationComponent);
        enemyEntity.add(targetTileComponent);
        enemyEntity.add(positionComponent);
        Statics.ashley.addEntity(enemyEntity);

    }

    private void spawnEnemy(ComboComponent cc) {

        if (MathUtils.randomBoolean(0.5f)) {
            int i = MathUtils.random(1, 4);
            Textures texture;
            switch (i) {
                case 2:
                    texture = Textures.METEOR_02;
                    break;
                case 3:
                    texture = Textures.METEOR_03;
                    break;
                case 4:
                    texture = Textures.METEOR_04;
                    break;
                default:
                    texture = Textures.METEOR_01;
            }
            spawnMeteor(texture, cc);
        } else {
            spawnShip(cc);
        }

    }

    private void startRecording(RythmComponent rc) {
        for (int i = 0; i < rc.numBeats; i++) {
            rc.keyChain[i] = CommandKeys.EMPTY;
        }
        rc.state = RythmComponent.States.RECORDING;
    }


    private void startExecution(RythmComponent rc) {
        rc.state = RythmComponent.States.EXECUTING;

        if (playerEntities.size() == 1) {
            Commands nextCommand = toCommands(rc.keyChain);
            ComboComponent cc = ComponentMappers.combo.get(playerEntities.get(0));
            if (nextCommand.equals(Commands.MOVE_RIGHT)) moveTo(playerEntities.get(0), 1, 0);
            else if (nextCommand.equals(Commands.MOVE_LEFT)) moveTo(playerEntities.get(0), -1, 0);
            else if (nextCommand.equals(Commands.MOVE_UP)) moveTo(playerEntities.get(0), 0, 1);
            else if (nextCommand.equals(Commands.MOVE_DOWN)) moveTo(playerEntities.get(0), 0, -1);
            else if (nextCommand.equals(Commands.MOVE_LEFT_DOWN)) moveTo(playerEntities.get(0), -1, -1);
            else if (nextCommand.equals(Commands.MOVE_LEFT_UP)) moveTo(playerEntities.get(0), -1, 1);
            else if (nextCommand.equals(Commands.MOVE_RIGHT_UP)) moveTo(playerEntities.get(0), 1, 1);
            else if (nextCommand.equals(Commands.MOVE_RIGHT_DOWN)) moveTo(playerEntities.get(0), 1, -1);
            else if (nextCommand.equals(Commands.SHOOT_RIGHT)) shoot(playerEntities.get(0), 1, 0);
            else {
                cc.combo = 0;
                Statics.asset.getSound(Sounds.ERROR).play();
            }
        }
    }

    private void shoot(Entity player, int x, int y) {
        Statics.asset.getSound(Sounds.SUCCESS).play();

        PositionComponent playerPositionComponent = ComponentMappers.position.get(player);
        ComboComponent comboComponent = ComponentMappers.combo.get(player);

        Entity bullet = Statics.ashley.createEntity();

        RenderableComponent renderableComponent = Statics.ashley.createComponent(RenderableComponent.class);
        TargetTileComponent targetTileComponent = Statics.ashley.createComponent(TargetTileComponent.class);
        PositionComponent positionComponent = Statics.ashley.createComponent(PositionComponent.class);
        BulletComponent bulletComponent = Statics.ashley.createComponent(BulletComponent.class);
        VelocityComponent velocityComponent = Statics.ashley.createComponent(VelocityComponent.class);

        velocityComponent.velocity = MathUtils.lerp(1, 10, (float) comboComponent.combo / (float) comboComponent.maxCombo);

        renderableComponent.textureRegion = new TextureRegion(Statics.asset.getTexture(Textures.MISSILE_RED));
        positionComponent.x = playerPositionComponent.x+0.5f;
        positionComponent.y = playerPositionComponent.y+0.4f;

        targetTileComponent.targetX = 20;
        targetTileComponent.targetY = positionComponent.y;

        bullet.add(renderableComponent);
        bullet.add(targetTileComponent);
        bullet.add(positionComponent);
        bullet.add(bulletComponent);
        bullet.add(velocityComponent);

        Statics.ashley.addEntity(bullet);

    }

    private void moveTo(Entity player, int x, int y) {
        ComboComponent cc = ComponentMappers.combo.get(player);
        TargetTileComponent tc = ComponentMappers.target.get(player);

        float lastTx = tc.targetX;
        float lastTy = tc.targetY;
        tc.targetX = MathUtils.clamp(tc.targetX + x, 0, GameRenderSystem.MAP_WIDTH - 1);
        tc.targetY = MathUtils.clamp(tc.targetY + y, 1, GameRenderSystem.MAP_HEIGHT - 2);

        boolean wasClamped = lastTx == tc.targetX && lastTy == tc.targetY;
        if (!wasClamped) {
            Statics.asset.getSound(Sounds.SUCCESS).play();
            cc.combo = MathUtils.clamp(cc.combo + 1, 0, cc.maxCombo);
        } else {
            cc.combo = MathUtils.clamp(cc.combo - 1, 0, cc.maxCombo);
        }
    }

    private Commands toCommands(CommandKeys[] keyChain) {
        return new Commands(keyChain);
    }
}
