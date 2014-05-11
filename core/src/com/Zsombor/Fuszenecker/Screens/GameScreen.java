package com.Zsombor.Fuszenecker.Screens;

import com.Zsombor.Fuszenecker.MyGdxGame;
import com.Zsombor.Fuszenecker.Objects.ObjectConstants;
import com.Zsombor.Fuszenecker.Objects.TrapSprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Created by Zsombor on 5/2/14.
 */
public class GameScreen implements Screen
{

    float frameDuration = 0.075f;
    Sprite myTeemo;
    Rectangle bg;
    float bgSpeed = 300;
    OrthographicCamera camera;
    MyGdxGame game;
    private int travelledDistance = 0;

    Texture bgImage;

    //TrapStart
    private List<TrapSprite> trapPool;
    int currentTrap = 0;
    Texture trapTexture;
    //TrapEnd

    //TeemoStart
    private static final int FRAME_COLS = 7;
    private static final int FRAME_ROWS = 1;

    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;
    TextureRegion teemoCurrentFrame;
    float stateTime;
    Vector3 targetPosition;
    float teemoSpeed = 4.0f;
    //TeemoEnd

    //NidaleeSpearStart
    float spearSpeed = 25;
    Sprite nidSpear;
    Texture nidImage;
    boolean nidSpearSpawned = false;
    float spearX = 1500;
    float spearY = 1500;
    private float degree = 19;
    float targetTeemoX;
    float targetTeemoY;
    private int spearSign;
    private boolean spearCanStillHit = true;

    //NidaleeSpearEnd

    //music
    private Music backGroundMusic;

    //sounds
    private List<Sound> walkSounds;
    private List<Sound> deadSounds;
    private List<Sound> laughSounds;
    private Sound spearHit;
    private Sound trapHit;

    private boolean soundPlaying = false;
    private float soundDeadLockTime = 3.0f;
    private float soundPlayProbability = 0.15f;
    private float soundCurrentTime = 0;


    public GameScreen(MyGdxGame gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenData.VIEWPORT_WIDTH, ScreenData.VIEWPORT_HEIGHT);

        //Teemo setup
        walkSheet = new Texture(Gdx.files.internal("rsz_teemo_animation.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++)
        {
            for (int j = 0; j < FRAME_COLS; j++)
            {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(frameDuration, walkFrames);
        stateTime = 0f;

        //load the images
        bgImage = new Texture(Gdx.files.internal("full_map.png"));
        nidImage = new Texture(Gdx.files.internal("rsz_nid_spear.png"));
        nidImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        //nidSpear object representing nidImage logically
        nidSpear = new Sprite(nidImage);
        //nidSpear.setOrigin(20,20);

        degree = 0;
        //bg object representing the bgImage logically
        bg = new Rectangle();
        bg.x = 0;
        bg.y = 0;
        bg.width = bgImage.getWidth() * 2;
        bg.height = 630;

        //teemo object representing the teemoCurrentFrame logically
        teemoCurrentFrame = walkAnimation.getKeyFrame(stateTime, true);
        myTeemo = new Sprite(teemoCurrentFrame);
        myTeemo.setX(15);
        myTeemo.setY(Gdx.graphics.getHeight() / 2); //starting position y
        targetPosition = new Vector3(myTeemo.getX(), myTeemo.getY(), 0);
        //Traps setup
        trapTexture = new Texture(Gdx.files.internal("rsz_trap.png"));
        trapPool = new ArrayList<TrapSprite>();
        for(int i=0;i< ObjectConstants.TRAP_POOLSIZE;i++)
        {
            TrapSprite tmpSprite = new TrapSprite(trapTexture);
            trapPool.add(tmpSprite);
        }


        //set bg music
        backGroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bg.mp3"));
        backGroundMusic.setLooping(true);
        //set sounds - laughs
        laughSounds = new ArrayList<Sound>();
        Sound l1 = Gdx.audio.newSound(Gdx.files.internal("laugh1.mp3"));
        Sound l2 = Gdx.audio.newSound(Gdx.files.internal("laugh2.mp3"));
        Sound l3 = Gdx.audio.newSound(Gdx.files.internal("laugh3.mp3"));
        laughSounds.add(l1);
        laughSounds.add(l2);
        laughSounds.add(l3);
        //set sounds - walk
        walkSounds = new ArrayList<Sound>();
        Sound w1 = Gdx.audio.newSound(Gdx.files.internal("walk1.mp3"));
        Sound w2 = Gdx.audio.newSound(Gdx.files.internal("walk2.mp3"));
        Sound w3 = Gdx.audio.newSound(Gdx.files.internal("walk3.mp3"));
        Sound w4 = Gdx.audio.newSound(Gdx.files.internal("walk4.mp3"));
        walkSounds.add(w1);
        walkSounds.add(w2);
        walkSounds.add(w3);
        walkSounds.add(w4);
        //set sounds - dead
        deadSounds = new ArrayList<Sound>();
        Sound d1 = Gdx.audio.newSound(Gdx.files.internal("dead1.mp3"));
        Sound d2 = Gdx.audio.newSound(Gdx.files.internal("dead2.mp3"));
        deadSounds.add(d1);
        deadSounds.add(d2);
        //spearhit
        spearHit = Gdx.audio.newSound(Gdx.files.internal("spearHit.mp3"));
        trapHit = Gdx.audio.newSound(Gdx.files.internal("trapHit.mp3"));

    }

    @Override
    public void render(float delta)
    {
        soundCurrentTime += Gdx.graphics.getDeltaTime();
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // Tell the SpriteBatch to render th the coordinate system specified by the camera
        game.batch.setProjectionMatrix(camera.combined);

        //begin a new batch
        game.batch.begin();
        bg.x -= bgSpeed * Gdx.graphics.getDeltaTime();
        game.batch.draw(bgImage, bg.x, bg.y);
        game.batch.draw(bgImage, bg.x + bg.width / 2, bg.y);
        nidSpear.setX(spearX);
        nidSpear.setY(spearY);
        nidSpear.draw(game.batch);
       // if(degree >=0)
            nidSpear.setRotation(degree);
        //else
         //   nidSpear.setRotation(360+degree);

        //Randomly place traps
        if(Math.random() * 1000 < 4)
        {
            //If the currentTrap is available
            if(trapPool.get(currentTrap).getOnMap() == ObjectConstants.TRAP_OUTOFMAP)
            {
                PlaceTrap(currentTrap);
                trapPool.get(currentTrap).setOnMap(ObjectConstants.TRAP_ONMAP);
                if(++currentTrap == ObjectConstants.TRAP_POOLSIZE)
                    currentTrap = 0;
            }
        }
        //Check traps
        for(int k=0;k<ObjectConstants.TRAP_POOLSIZE;k++)
        {
            switch (checkTrap(k))
            {
                case ObjectConstants.TRAP_ONMAP:
                    //render
                    trapPool.get(k).setX(trapPool.get(k).getX() - (bgSpeed * Gdx.graphics.getDeltaTime()));
                    trapPool.get(k).draw(game.batch);
                    break;
                case ObjectConstants.TRAP_OUTOFMAP:
                    trapPool.get(k).setOnMap(ObjectConstants.TRAP_OUTOFMAP);
                    break;
                case ObjectConstants.TRAP_TRIGGERED:
                    trapPool.get(k).setOnMap(ObjectConstants.TRAP_OUTOFMAP);
                    die("trap");
                    break;
            }
        }


        //randomly Draw Spear 1000 <2
        if (Math.random() * 1000 < 2 && !nidSpearSpawned)
        {
            PlaceSpear();
            targetTeemoX = myTeemo.getX();
            targetTeemoY = myTeemo.getY();
            double dx = spearX - targetTeemoX;
            double dy = spearY - targetTeemoY;
            spearSign = (spearY >= targetTeemoY) ? -1 : 1;
            degree = (float) Math.toDegrees(Math.atan2(dy,dx));
        }
        else if(nidSpearSpawned)
        {
            //move
            spearX -= spearSpeed*Math.cos(Math.toRadians(degree));
            spearY += spearSign*Math.abs(spearSpeed*Math.sin(Math.toRadians(degree)));


            //If hit
            if(nidSpear.getBoundingRectangle().overlaps(myTeemo.getBoundingRectangle()) && spearCanStillHit)
            {
                resetSpear();
                die("spear");
            }

            //IF out of sight
            if(spearX <= myTeemo.getX()+myTeemo.getWidth()-spearSpeed)
                spearCanStillHit = false;

            if(spearX <= -nidSpear.getWidth())
            {
                resetSpear();
                int randomPos = (int) Math.floor(Math.random()*(laughSounds.size()));
                laughSounds.get(randomPos).play();
                spearCanStillHit = true;
            }

        }


        game.font.draw(game.batch, "Distance: " + travelledDistance, 10, 20);


        if (bg.x < -(bg.width / 2))
            bg.x = 0;

        //draw teeeeeemoooooooooooooooo
        stateTime += Gdx.graphics.getDeltaTime();
        teemoCurrentFrame = walkAnimation.getKeyFrame(stateTime, true);
        myTeemo.setRegion(teemoCurrentFrame);
        if (Gdx.input.isTouched() && Gdx.input.getX() <= Gdx.graphics.getWidth() * 0.2)
        {
            if(soundCurrentTime > soundDeadLockTime)
                soundPlaying = false;
            //play random walk sound: 15% that the sound will play and there is a 3 sec minimum deadlock
            if(Math.random() <= soundPlayProbability && !soundPlaying)
            {
                int randomPos = (int) Math.floor(Math.random()*(walkSounds.size()));
                walkSounds.get(randomPos).play();
                soundPlaying = true;
                soundCurrentTime = 0;
            }

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()+myTeemo.getHeight()/2, 0);
            camera.unproject(touchPos);
            targetPosition = touchPos;
        }

        //Move Teemo
        myTeemo.setY(myTeemo.getY() + (Mathf_Sign(myTeemo.getY(), targetPosition.y, teemoSpeed,false) * teemoSpeed));
        //boundaries
        if (myTeemo.getY() <= 150)
            myTeemo.setY(150);
        else if (myTeemo.getY() >= 450)
            myTeemo.setY(450);


        //Set distance
        travelledDistance += 1;
        myTeemo.draw(game.batch);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Do something
            Gdx.input.setCatchBackKey(true);
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void resetSpear()
    {
        nidSpearSpawned = false;
        spearX = 1500;
        spearY = 1500;
    }

    private void die(String cause)
    {
        if(cause == "trap")
        {
            trapHit.play();
            int randomPos = (int) Math.floor(Math.random()*(deadSounds.size()));
            deadSounds.get(randomPos).play();
        }
        else if(cause == "spear")
        {
            spearHit.play();
            int randomPos = (int) Math.floor(Math.random()*(deadSounds.size()));
            deadSounds.get(randomPos).play();
            spearCanStillHit = true;
        }

                dispose();
                game.setScreen(new GameOverScreen(game));

    }


    private void PlaceSpear()
    {
    float rx = (float) (800 + Math.random()* ScreenData.VIEWPORT_WIDTH);
    float ry = 10.0f;
    while(ry >= 0 && ry <= ScreenData.VIEWPORT_HEIGHT)
        ry = (float) (-200 + Math.random()*(ScreenData.VIEWPORT_HEIGHT+400));

    spearX = rx;
    spearY = ry;
    nidSpearSpawned = true;
}

    private void PlaceTrap(int trapID)
    {
        float x = ScreenData.VIEWPORT_WIDTH+200.0f;
        float ry = (float) (150+Math.random()*300);

        trapPool.get(trapID).setX(x);
        trapPool.get(trapID).setY(ry);
    }

    private int checkTrap(int trapID)
    {
        if(trapPool.get(trapID).getOnMap() == ObjectConstants.TRAP_ONMAP)
        {
            //hit
            if(trapPool.get(trapID).getBoundingRectangle().overlaps(myTeemo.getBoundingRectangle()))
                return ObjectConstants.TRAP_TRIGGERED;

            //Out of map
            if(trapPool.get(trapID).getX() < -50)
                return ObjectConstants.TRAP_OUTOFMAP;

            //idle on screen
            return ObjectConstants.TRAP_ONMAP;
        }
        else
            return ObjectConstants.TRAP_NOTPLACED;
    }

    //current position to target position (speed parameter is needed for no ghost effect)
    private float Mathf_Sign(float c,float t,float speed,boolean changeDirection) {
        if(Math.abs(c-t) > speed)
        {
            if(!changeDirection)
                return (c - t > 0) ? -1 : 1;
            else
                return (c - t > 0) ? 1 : -1;
        }
        else
            return 0;
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void show()
    {
        backGroundMusic.play();
        backGroundMusic.setVolume(0.2f);
    }

    @Override
    public void hide()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        backGroundMusic.dispose();
        /*for(Iterator<Sound> i = walkSounds.iterator(); i.hasNext(); )
        {
            i.next().stop();
            i.next().dispose();
        }
        for(Iterator<Sound> i = deadSounds.iterator(); i.hasNext(); )
        {
            i.next().stop();
            i.next().dispose();
        }
        for(Iterator<Sound> i = laughSounds.iterator(); i.hasNext(); )
        {
            i.next().stop();
            i.next().dispose();
        }

        trapHit.stop();
        trapHit.dispose();
        spearHit.dispose();
        spearHit.dispose()
        game.batch.dispose();
*/
    }
}
