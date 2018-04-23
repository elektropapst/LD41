package de.elektropapst.ld41.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Disposable;
import de.elektropapst.ld41.assets.enums.*;

public class AssetLoader implements Disposable {

    private AssetManager manager;

    public AssetLoader() {
        manager = new AssetManager();
        loadTextures();
        loadMusics();
        loadSounds();
        loadFonts();
    }

    private void loadTextures() {
        for (Textures texture : Textures.values()) {
            manager.load(texture.name, Texture.class);
        }
    }

    private void loadFonts() {
        for (BitmapFonts font : BitmapFonts.values()) {
            manager.load(font.name, BitmapFont.class);
        }
    }

    private void loadMusics() {
        for (Musics musics : Musics.values()) {
            manager.load(musics.name, Music.class);
        }
    }

    private void loadSounds() {
        for (Sounds sound : Sounds.values()) {
            manager.load(sound.name, Sound.class);
        }
    }

    public BitmapFont getFont(BitmapFonts font) {
        return manager.get(font.name, BitmapFont.class);
    }

    public Texture getTexture(Textures texture) {
        return manager.get(texture.name, Texture.class);
    }

    public Music getMusic(Musics music) {
        return manager.get(music.name, Music.class);
    }

    public Sound getSound(Sounds sound) {
        return manager.get(sound.name, Sound.class);
    }

    public boolean update() {
        return manager.update();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }

}
