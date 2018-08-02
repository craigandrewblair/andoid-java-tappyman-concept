package dev.blairio.tappyman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static dev.blairio.tappyman.TappyMan.WORLD_HEIGHT;
import static dev.blairio.tappyman.TappyMan.WORLD_WIDTH;

public class Man {

    private Texture[] run;
    private Texture[] jump;
    private Rectangle manRectangle = new Rectangle();
    private float mVelocity = 0.0f;
    private int jumpTapCount = 0;
    private int mManFlaps = 10;
    private int mManY;
    private int mRunState = 0;

    public Rectangle getManRectangle() {
        return manRectangle;
    }

    public void setmManY(int mManY) {
        this.mManY = mManY;
    }

    public void setmVelocity(float mVelocity) {
        this.mVelocity = mVelocity;
    }

    public void setManPos(int xpos, int ypos) {
        int mManX;
        mManX = xpos;
        mManY = ypos;
    }

    public void run(SpriteBatch batch) {
        if (mRunState < 49) {
            mRunState++;
        } else {
            mRunState = 0;
        }
        batch.draw( run[mRunState], 0, WORLD_HEIGHT / 20, WORLD_WIDTH / 10, WORLD_HEIGHT / 10 );
        manRectangle.set( 0, WORLD_WIDTH / 20, WORLD_WIDTH / 12, WORLD_HEIGHT / 12 );
    }

    public void jump(SpriteBatch batch) {
        if (mRunState < 17) {
            mRunState++;
        } else {
            mRunState = 0;
        }
        batch.draw( run[mRunState], 0, mManY, WORLD_WIDTH / 10, WORLD_HEIGHT / 10 );
        manRectangle.set( 0, mManY, WORLD_WIDTH / 12, WORLD_HEIGHT / 12 );
    }

    public void touchJumpMotion(int velocityChange, SpriteBatch batch) {
        if (Gdx.input.justTouched()) {
            if (jumpTapCount == 0) {
                mVelocity -= velocityChange; // minus value mVelocity when tapped
                jump( batch );
                jumpTapCount++;
            } else if (jumpTapCount <= mManFlaps && mManY != WORLD_WIDTH / 20) {
                mVelocity -= velocityChange; // minus value mVelocity when tapped
                jump( batch );
                jumpTapCount++;
            } else if (mManY == WORLD_WIDTH / 20) {
                jumpTapCount = 0;
            }
        }
    }

    public void screenJumpLimit() {
        if (mManY >= WORLD_HEIGHT - WORLD_HEIGHT / 8) {
            mManY = (int) WORLD_HEIGHT - (int) WORLD_HEIGHT / 8;
            // stops cumulative taps in excess of screen height
            mVelocity = 0;
        }
    }

    public void screenFloorLimit() {
        if (mManY <= WORLD_WIDTH / 20) {
            mManY = (int) (WORLD_WIDTH / 20);
            mVelocity = 0;
        }
    }

    public void jumpControl(float gravity) {
        // gravity is continuously added to mVelocity value (eg mVelocity = mVelocity + gravity) reducing mManY
        mVelocity += gravity;
        // the new minus mVelocity value results in mVelocity being added to the mManY value
        mManY -= mVelocity; // mManY = mManY - -mVelocity (-- = +)
    }

    public void createJumpFrameArray() {
        jump = new Texture[18];
        for (int i = 0; i < 18; i++) {
            jump[i] = new Texture( "jump/jump_" + Integer.toString( i ) + ".png" );
        }
    }

    public void createRunFrameArray() {
        run = new Texture[50];
        for (int i = 0; i < 50; i++) {
            run[i] = new Texture( "run/running_" + Integer.toString( i ) + ".png" );
        }
    }

    public void runOrJump(SpriteBatch batch) {
        if (mManY > WORLD_WIDTH / 20) {
            jump( batch );
        } else {
            run( batch );
        }
    }

}

