package dev.blairio.tappyman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import dev.blairio.tappyman.TappyMan;
import dev.blairio.tappyman.screens.GameOverScreen;

import static dev.blairio.tappyman.TappyMan.WORLD_HEIGHT;
import static dev.blairio.tappyman.TappyMan.WORLD_WIDTH;

public class Bomb {

    private Texture mExplosion = new Texture( "explode/explosion_3.png" );
    private Texture mBomb = new Texture( "collectibles/bomb.png" );
    private int mBombSpeed = 6;
    private int mBombCount;
    private Random ranBomb = new Random();
    private Rectangle bombRectangle = new Rectangle();
    private ArrayList<Integer> mBombX = new ArrayList<Integer>();
    private ArrayList<Integer> mBombY = new ArrayList<Integer>();

    public ArrayList<Integer> getmBombX() {
        return mBombX;
    }

    public ArrayList<Integer> getmBombY() {
        return mBombY;
    }

    public Texture getmExplosion() {
        return mExplosion;
    }

    public Texture getmBomb() {
        return mBomb;
    }

    public void setmBombCount(int mBombCount) {
        this.mBombCount = mBombCount;
    }

    public int getmBombSpeed() {
        return mBombSpeed;
    }

    public void setmBombSpeed(int mBombSpeed) {
        this.mBombSpeed = mBombSpeed;
    }

    public void addToBombArray() {
        float height = (ranBomb.nextFloat() * WORLD_HEIGHT * 0.75f) + WORLD_HEIGHT / 7;
        mBombY.add( (int) height );
        mBombX.add( Gdx.graphics.getWidth() );
    }

    public void bombCounter() {
        if (mBombCount < 200) {
            mBombCount++;
        } else {
            mBombCount = 0;
            addToBombArray();
        }
    }

    public void displayBombs(SpriteBatch batch, Rectangle manRect, TappyMan game) {
        for (int i = 0; i < mBombX.size(); i++) {
            batch.draw( mBomb, mBombX.get( i ), mBombY.get( i ), WORLD_WIDTH / 16, WORLD_HEIGHT / 6 );
            mBombX.set( i, mBombX.get( i ) - mBombSpeed );
            bombRectangle.set( mBombX.get( i ), mBombY.get( i ), WORLD_WIDTH / 17, WORLD_HEIGHT / 7 );
            bombCollision( i, manRect, game );
        }
    }

    public void bombCollision(int i, Rectangle manRect, TappyMan game) {
        if (Intersector.overlaps( manRect, bombRectangle )) {
            //Gdx.app.log("Bomb", "BOOM!");
            mBombX.set( i, -999 );
            game.setScreen( new GameOverScreen( game ) );
        }
    }
}
