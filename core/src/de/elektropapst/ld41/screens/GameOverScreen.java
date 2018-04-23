package de.elektropapst.ld41.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import de.elektropapst.ld41.LDGame;
import de.elektropapst.ld41.Statics;
import de.elektropapst.ld41.assets.enums.BitmapFonts;

public class GameOverScreen extends ScreenAdapter {

    private Batch spriteBatch;
    private BitmapFont font;

    public GameOverScreen() {
        spriteBatch = new SpriteBatch();
        font = Statics.asset.getFont(BitmapFonts.GAMEFONT);
    }

    float remainingTime = 0.0f;

    @Override
    public void show() {
        super.show();
        remainingTime = 5.0f;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        float r = MathUtils.lerp(0.0f, 1.0f, remainingTime / 5.0f);
        Gdx.gl.glClearColor(r, 0.05f, .05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        font.draw(spriteBatch, "Game Over", 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Align.center, false);
        font.draw(spriteBatch, "Next game in " + MathUtils.round(remainingTime) + " seconds", 0, Gdx.graphics.getHeight() / 2 - font.getLineHeight(), Gdx.graphics.getWidth(), Align.center, false);
        spriteBatch.end();

        remainingTime -= delta;
        if (remainingTime <= 0) {
            LDGame.setCurrentScreen(Screens.GAME);
        }
    }
}
