package dev.blairio.tappyman.screens;

import dev.blairio.tappyman.TappyMan;
import dev.blairio.tappyman.entities.Bomb;
import dev.blairio.tappyman.entities.Coin;
import dev.blairio.tappyman.entities.Man;
import dev.blairio.tappyman.entities.Snail;
import dev.blairio.tappyman.tools.ScrollingBackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import static dev.blairio.tappyman.TappyMan.WORLD_HEIGHT;
import static dev.blairio.tappyman.TappyMan.WORLD_WIDTH;

public class MainGameScreen implements Screen {

	TappyMan game;
	BitmapFont scoreFont;
	private Bomb bomb;
	private Coin coin;
	private Snail snail;
	private Man man;
	private int mBackgroundSpeed = 1;
	private int mBackgroundCloseSpeed = 2;
	private int mGrassSpeed = 1;
	private static int mGameState = 1;
	private static int mScoreCount = 0;
	private static int mHighScore = 0;
	private int mGameDifficulty = 1;
	private ScrollingBackground scrollingBackground;
	private ScrollingBackground scrollingBackgroundClose;
	private ScrollingBackground scrollingGrass;
	private BitmapFont mFont;
	private BitmapFont mScoreFont;

	public MainGameScreen (TappyMan game) {

		this.game = game;
		bomb = new Bomb();
		coin = new Coin();
		snail = new Snail();
		man = new Man();
		man.createRunFrameArray();
		man.createJumpFrameArray();
		man.setManPos( Gdx.graphics.getWidth(), Gdx.graphics.getWidth() / 2 );
		scoreFont = new BitmapFont( Gdx.files.internal("fonts/score.fnt"));
		mFont = new BitmapFont( Gdx.files.internal( "font/roboto-medium-64.fnt" ) );
		mScoreFont = new BitmapFont( Gdx.files.internal( "font/roboto-medium-64.fnt" ) );
		if (TappyMan.IS_MOBILE){
			game.scrollingBackground.setSpeedFixed(false);
		}
		this.scrollingBackground = new ScrollingBackground( "background-grass-horizontal.png" );
		this.scrollingGrass = new ScrollingBackground( "scenery/grass-stretched.png" );
		this.scrollingBackgroundClose = new ScrollingBackground( "background-close.png" );

	}

	public static int getmScoreCount() {
		return mScoreCount;
	}
	public static void setmScoreCount(int mScoreCount) { MainGameScreen.mScoreCount = mScoreCount; }
	public static int getmHighScore() { return mHighScore; }

	@Override
	public void show () {
		
	}

	@Override
	public void render (float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		draw();
		game.batch.end();
	}

	public void draw(){
		if (mGameState == 1) {
			// scrolling background
			scrollingBackground.updateAndRender( Gdx.graphics.getDeltaTime() * mBackgroundSpeed, game.batch );
			scrollingBackgroundClose.updateAndRender( Gdx.graphics.getDeltaTime() * mBackgroundCloseSpeed, game.batch );
			scrollingGrass.updateAndRender( Gdx.graphics.getDeltaTime() * mGrassSpeed, game.batch );
			man.touchJumpMotion( 18, game.batch );
			// jump control
			man.jumpControl( 1.0f );
			// restrict person to y-pos of 0 or greater (stops man falling of bottom of screen)
			man.screenFloorLimit();
			// limits man jump height to screen max
			man.screenJumpLimit();
			// determine relevant animation for state
			man.runOrJump( game.batch );
			// score
			setHighScore();
			displayScore();
			displayHighScore();
			coin.coinCounter();
			coin.displayCoins( game.batch, man.getManRectangle(), this );
			bomb.bombCounter();
			bomb.displayBombs( game.batch, man.getManRectangle(), game);
			snail.snailCounter();
			snail.displaySnails( game.batch, man.getManRectangle(), this );
			snail.displayShell( game.batch );
		}
	}

	// reset game state
	public void reset() {
		mGameState = 1;
		man.setmManY( Gdx.graphics.getHeight() / 2 );
		bomb.getmBombX().clear();
		bomb.getmBombY().clear();
		coin.getmCoinX().clear();
		coin.getmCoinY().clear();
		bomb.setmBombCount( 0 );
		man.setmVelocity( 0 );
		coin.setmCoinCount( 0 );
		mScoreCount = 0;
		mGameDifficulty = 1;
		bomb.setmBombSpeed( 6 );
		coin.setmCoinSpeed( 4 );
		snail.setmSnailSpeed( 7 );
		mBackgroundSpeed = 1;
		mBackgroundCloseSpeed = 2;
		mGrassSpeed = 1;
		snail.setmSnailSpawnSpeed( 3 );
	}


	public void setHighScore() {
		Preferences prefs = Gdx.app.getPreferences( "dasher" );
		mHighScore = prefs.getInteger( "highscore", 0 );
		if (mScoreCount > mHighScore) {
			prefs.putInteger( "highscore", mScoreCount );
			// save to file
			prefs.flush();
		}
	}

	public void displayHighScore() {
		mScoreFont.draw( game.batch, "High: " + String.valueOf( mHighScore ), WORLD_WIDTH - WORLD_WIDTH / 5, WORLD_HEIGHT - WORLD_HEIGHT / 25 );
	}

	public void displayScore() {
		mScoreFont.draw( game.batch, "Score: " + String.valueOf( mScoreCount ), WORLD_WIDTH - WORLD_WIDTH / 5, 0 + WORLD_HEIGHT / 8 );
	}

	// difficulty
	public void increaseGameDifficultyValue() {
		mGameDifficulty++;
		switch (mScoreCount) {
			case 5:
				increaseGameDifficulty();
				break;
			case 10:
				increaseGameDifficulty();
				break;
			case 15:
				increaseGameDifficulty();
				break;
			case 20:
				increaseGameDifficulty();
				break;
			case 25:
				increaseGameDifficulty();
				break;
			case 30:
				increaseGameDifficulty();
				break;
			case 35:
				increaseGameDifficulty();
				break;
			case 40:
				increaseGameDifficulty();
				break;
			case 50:
				increaseGameDifficulty();
				break;
			default:
				break;
		}
	}

	public void increaseGameDifficulty() {
		bomb.setmBombSpeed( bomb.getmBombSpeed() + 1 );
		coin.setmCoinSpeed( coin.getmCoinSpeed() + 1 );
		snail.setmSnailSpeed( snail.getmSnailSpeed() + 1 );
		mBackgroundSpeed = mBackgroundSpeed + 1;
		mBackgroundCloseSpeed = mBackgroundCloseSpeed + 1;
		mGrassSpeed = mGrassSpeed + 1;
		snail.setmSnailSpawnSpeed( snail.getmSnailSpeed() + 5 );
	}

	private boolean isRight () {
		return false;
	}

	private boolean isLeft () {
		return false;
	}

	private boolean isJustRight () {
		return false;
	}

	private boolean isJustLeft () {
		return false;
	}

	@Override
	public void resize (int width, int height) {
		
	}

	@Override
	public void pause () {
		
	}

	@Override
	public void resume () {
		
	}

	@Override
	public void hide () {
		
	}

	@Override
	public void dispose() {

	}
}
