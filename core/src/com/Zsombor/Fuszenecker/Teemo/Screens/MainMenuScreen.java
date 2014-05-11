package com.Zsombor.Fuszenecker.Teemo.Screens;

import com.Zsombor.Fuszenecker.Teemo.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Zsombor on 5/2/14.
 */
public class MainMenuScreen implements Screen {
    public static final int VIEWPORT_WIDTH = 1280;
    final MyGdxGame game;

    OrthographicCamera camera;

    public MainMenuScreen(final MyGdxGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        //konstansok nem ártanának...
        camera.setToOrtho(false, ScreenData.VIEWPORT_WIDTH,ScreenData.VIEWPORT_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Teemo Runner!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
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
