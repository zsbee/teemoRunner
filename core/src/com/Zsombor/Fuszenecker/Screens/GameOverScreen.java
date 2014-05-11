package com.Zsombor.Fuszenecker.Screens;

import com.Zsombor.Fuszenecker.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Zsombor on 5/10/14.
 */
public class GameOverScreen implements Screen {
    final MyGdxGame game;

    OrthographicCamera camera;
    Texture bgImage;


    public GameOverScreen(final MyGdxGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenData.VIEWPORT_WIDTH,ScreenData.VIEWPORT_HEIGHT);

        bgImage = new Texture(Gdx.files.internal("gameOverImage.jpg"));
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //game.font.draw(game.batch, "Tap anywhere to go back to the main Menu!", 100, 100);
        game.batch.draw(bgImage,0,0);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        if (Gdx.input.isTouched()) {
            dispose();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
