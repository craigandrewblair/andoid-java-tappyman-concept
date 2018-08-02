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

public class Coin {

    private Texture mCoin = new Texture( "collectibles/star_coin_1.png" );
    private int mCoinSpeed = 4;
    private int mCoinCount = 0;
    private Rectangle coinRectangle = new Rectangle();
    private Random ranCoin = new Random();
    private ArrayList<Integer> mCoinX = new ArrayList<Integer>();
    private ArrayList<Integer> mCoinY = new ArrayList<Integer>();

    public ArrayList<Integer> getmCoinX() { return mCoinX;}

    public ArrayList<Integer> getmCoinY() { return mCoinY; }

    public void setmCoinCount(int mCoinCount) {
        this.mCoinCount = mCoinCount;
    }

    public int getmCoinSpeed() {
        return mCoinSpeed;
    }

    public void setmCoinSpeed(int mCoinSpeed) {
        this.mCoinSpeed = mCoinSpeed;
    }

    public void addToCoinArray() {
        float height = (ranCoin.nextFloat() * WORLD_HEIGHT * 0.75f) + WORLD_HEIGHT / 7;
        mCoinY.add( (int) height );
        mCoinX.add( Gdx.graphics.getWidth() );
    }

    public void coinCounter() {
        if (mCoinCount < 25) {
            mCoinCount++;
        } else {
            mCoinCount = 0;
            addToCoinArray();
        }
    }

    public void displayCoins(SpriteBatch batch, Rectangle manRectangle, MainGameScreen cv) {
        for (int i = 0; i < mCoinX.size(); i++) {
            batch.draw( mCoin, mCoinX.get( i ), mCoinY.get( i ), WORLD_WIDTH / 16, WORLD_HEIGHT / 12 );
            mCoinX.set( i, mCoinX.get( i ) - mCoinSpeed );
            coinRectangle.set( mCoinX.get( i ), mCoinY.get( i ), WORLD_WIDTH / 17, WORLD_HEIGHT / 13 );
            coinCollision( i, manRectangle, cv );
        }
    }

    public void coinCollision(int i, Rectangle manRectangle, MainGameScreen cv) {
        if (Intersector.overlaps( manRectangle, coinRectangle )) {
            mCoinX.set( i, -999 );
            MainGameScreen.setmScoreCount( MainGameScreen.getmScoreCount() + 1 );
            cv.increaseGameDifficultyValue();
        }
    }
}
