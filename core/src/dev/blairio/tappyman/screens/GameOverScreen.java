package dev.blairio.tappyman.screens;

import dev.blairio.tappyman.TappyMan;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import dev.blairio.tappyman.tools.ScrollingBackground;
import static dev.blairio.tappyman.TappyMan.WORLD_HEIGHT;
import static dev.blairio.tappyman.TappyMan.WORLD_WIDTH;

public class GameOverScreen implements Screen {

	TappyMan game;
	MainGameScreen mainGameScreen;

	private BitmapFont mGameOverFont;

	public GameOverScreen (TappyMan game) {
		this.game = game;
		mainGameScreen = new MainGameScreen(game);

		mGameOverFont = new BitmapFont( Gdx.files.internal( "font/roboto-medium-64.fnt" ) );
		mGameOverFont.getData().setScale( 2, 2 );

		game.scrollingBackground.setSpeedFixed(true);
		game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
	}

	@Override
	public void show () {}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		
		game.scrollingBackground.updateAndRender(delta, game.batch );

		float touchX = game.cam.getInputInGameWorld().x, touchY = WORLD_HEIGHT - game.cam.getInputInGameWorld().y;
		
		GlyphLayout tryAgainLayout = new GlyphLayout(mGameOverFont, "Try Again");
		GlyphLayout mainMenuLayout = new GlyphLayout(mGameOverFont, "Main Menu");

		float tryAgainX = WORLD_WIDTH / 2 - tryAgainLayout.width /2;
		float tryAgainY = WORLD_HEIGHT / 3;
		float mainMenuX = WORLD_WIDTH / 2 - mainMenuLayout.width /2;
		float mainMenuY = WORLD_HEIGHT / 4 + mainMenuLayout.height - tryAgainLayout.height - 30;

		if (Gdx.input.isTouched()) {
			// try again
			if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
				this.dispose();
				game.batch.end();
				mainGameScreen.reset();
				game.setScreen(new MainGameScreen(game));

				return;
			}
			
			// main menu
			if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
				this.dispose();
				game.batch.end();
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
		
		// buttons
		mGameOverFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
		mGameOverFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);
		mGameOverFont.draw( game.batch, "    Game Over!\n\n   High Scored " + MainGameScreen.getmHighScore()
				+ "\n    You Score " + MainGameScreen.getmScoreCount(), WORLD_WIDTH / 5, WORLD_HEIGHT - WORLD_HEIGHT / 14);
		
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
