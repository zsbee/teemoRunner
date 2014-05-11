package com.Zsombor.Fuszenecker.Screens;

import com.Zsombor.Fuszenecker.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Zsombor on 5/2/14.
 */
public class MainMenuScreen implements Screen {
    final MyGdxGame game;
    OrthographicCamera camera;

    Texture bgImage;

    Texture playImage;
    Texture quitImage;
    Texture HSImage;

    public MainMenuScreen(final MyGdxGame gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenData.VIEWPORT_WIDTH,ScreenData.VIEWPORT_HEIGHT);
        //background setup
        bgImage = new Texture(Gdx.files.internal("MainMenuBg.png"));
        playImage = new Texture(Gdx.files.internal("btn_play.png"));
        quitImage = new Texture(Gdx.files.internal("btn_quit.png"));
        HSImage = new Texture(Gdx.files.internal("btn_hs.png"));

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Teemo Runner!!! ", 100, 150);

        game.batch.draw(bgImage,0,0);
        Vector2 playPos = new Vector2(ScreenData.VIEWPORT_WIDTH/2-playImage.getWidth()/2,ScreenData.VIEWPORT_HEIGHT/2-playImage.getHeight()/2 + 15 + quitImage.getHeight());
        Vector2 hsPos = new Vector2(ScreenData.VIEWPORT_WIDTH/2-HSImage.getWidth()/2,ScreenData.VIEWPORT_HEIGHT/2-HSImage.getHeight()/2);
        Vector2 quitPos = new Vector2(ScreenData.VIEWPORT_WIDTH/2-quitImage.getWidth()/2,quitImage.getHeight() + 15);

        game.batch.draw(playImage,playPos.x,playPos.y);//legfelül
        game.batch.draw(HSImage,hsPos.x,hsPos.y);//középre
        game.batch.draw(quitImage,quitPos.x,quitPos.y);//legalul

        game.batch.end();

        if (Gdx.input.isTouched()) {
            int tx = Gdx.input.getX();
            int ty = Gdx.input.getY();
            int tz = 0;
            Vector3 uVect3 = new Vector3(tx,ty,tz);
            camera.unproject(uVect3);
            //check quitBtn Tap - nincs kedvem actorokkal szarakodni
            if(uVect3.x>=quitPos.x-quitImage.getWidth()/2 && uVect3.x<=quitPos.x+quitImage.getWidth())
            {
                if(uVect3.y>=quitPos.y-quitImage.getHeight()/2 && uVect3.y <= quitPos.y+quitImage.getHeight()) {
                    dispose();
                    Gdx.app.exit();
                }
            }
            //check playBtn Tap - nincs kedvem actorokkal szarakodni
            if(uVect3.x>=playPos.x-playImage.getWidth()/2 && uVect3.x<=playPos.x+playImage.getWidth())
            {
                if(uVect3.y>=playPos.y-playImage.getHeight()/2 && uVect3.y <= playPos.y+playImage.getHeight()) {
                    dispose();
                    game.setScreen(new GameScreen(game));
                }
            }

            //check hsBtn Tap - nincs kedvem actorokkal szarakodni
            if(uVect3.x>= hsPos.x-HSImage.getWidth()/2 && uVect3.x<=hsPos.x+HSImage.getWidth())
            {
                if(uVect3.y>=hsPos.y-HSImage.getHeight()/2 && uVect3.y <= hsPos.y+HSImage.getHeight()) {
                    dispose();
                    game.setScreen(new GameScreen(game));
                }
            }

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
    public void dispose()
    {
        bgImage.dispose();
        playImage.dispose();
        HSImage.dispose();
        quitImage.dispose();
    }
}
