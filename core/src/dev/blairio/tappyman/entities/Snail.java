package dev.blairio.tappyman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import dev.blairio.tappyman.screens.MainGameScreen;

import static dev.blairio.tappyman.TappyMan.WORLD_HEIGHT;
import static dev.blairio.tappyman.TappyMan.WORLD_WIDTH;

public class Snail {

    private int mSnailSpawnSpeed = 3;
    private int mSnailCount = 0;
    private int mShellPos = 0;
    private int mSnailSpeed = 7;
    private boolean fireShell = false;
    private ArrayList<Integer> mSnailX = new ArrayList<Integer>();
    private ArrayList<Integer> mSnailY = new ArrayList<Integer>();
    private Rectangle snailRectangle = new Rectangle();
    private Random ranSnail = new Random();
    private Texture mShell = new Texture( "collectibles/snail-shell.png" );
    private Texture mSnail = new Texture( "collectibles/snail.png" );

    public Texture getmShell() { return mShell; }

    public Texture getmSnail() { return mSnail; }

    public int getmSnailSpeed() {
        return mSnailSpeed;
    }

    public void setmSnailSpeed(int mSnailSpeed) {
        this.mSnailSpeed = mSnailSpeed;
    }

    public void setmSnailSpawnSpeed(int mSnailSpawnSpeed) {
        this.mSnailSpawnSpeed = mSnailSpawnSpeed;
    }

    public void addToSnailArray(int snailSpawnPercentage) {
        boolean addSnail;
        int addSnailChance = ranSnail.nextInt( 100 );
        if (addSnailChance < snailSpawnPercentage) {
            addSnail = true;
        } else {
            addSnail = false;
        }
        if (addSnail) {
            float height = WORLD_HEIGHT / 20;
            mSnailY.add( (int) height );
            mSnailX.add( Gdx.graphics.getWidth() );
        } else {
            mSnailY.add( null );
            mSnailX.add( null );
        }
    }

    public void snailCounter() {
        if (mSnailCount < 100) {
            mSnailCount++;
        } else {
            mSnailCount = 0;
            addToSnailArray( mSnailSpawnSpeed );
        }
    }

    public void displaySnails(SpriteBatch batch, Rectangle manRectangle, MainGameScreen cv) {
        for (int i = 0; i < mSnailX.size(); i++) {
            if (mSnailX.get( i ) != null) {
                batch.draw( mSnail, mSnailX.get( i ), mSnailY.get( i ), WORLD_WIDTH / 14, WORLD_HEIGHT / 10 );
                snailRectangle.set( mSnailX.get( i ), mSnailY.get( i ), WORLD_WIDTH / 14, WORLD_HEIGHT / 10 );
            } else {
                continue;
            }
            mSnailX.set( i, mSnailX.get( i ) - mSnailSpeed );
            snailCollision( i, manRectangle, cv );
        }
    }

    public void snailCollision(int i, Rectangle manRectangle, MainGameScreen cv) {
        if (Intersector.overlaps( manRectangle, snailRectangle )) {
            //Gdx.app.log( "Snail", "Snail Smash!" );
            if (MainGameScreen.getmScoreCount() < 5) {
                MainGameScreen.setmScoreCount(0);
                mSnailX.remove( i );
                mSnailY.remove( i );
            }
            if (MainGameScreen.getmScoreCount() >= 5) {
                MainGameScreen.setmScoreCount( MainGameScreen.getmScoreCount() - 5 );
                mSnailX.remove( i );
                mSnailY.remove( i );
            }
            fireShell = true;
            cv.increaseGameDifficulty();
        }
    }

    public void displayShell(SpriteBatch batch) {
        if (fireShell) {
            if (mShellPos < WORLD_WIDTH) {
                mShellPos = mShellPos + 25;
                batch.draw( mShell, mShellPos, WORLD_WIDTH / 20, WORLD_WIDTH / 18, WORLD_HEIGHT / 13 );
            } else {
                mShellPos = 0;
                fireShell = false;
            }
        }
    }
}
