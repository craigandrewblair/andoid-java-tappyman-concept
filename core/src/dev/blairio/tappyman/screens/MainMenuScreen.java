package dev.blairio.tappyman.screens;

import dev.blairio.tappyman.TappyMan;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import dev.blairio.tappyman.tools.ScrollingBackground;

public class MainMenuScreen implements com.badlogic.gdx.Screen {
	
	private static final int EXIT_BUTTON_WIDTH = (int) (TappyMan.WORLD_WIDTH/3 + TappyMan.WORLD_WIDTH/100);
	private static final int EXIT_BUTTON_HEIGHT = (int) (TappyMan.WORLD_HEIGHT/5 + TappyMan.WORLD_HEIGHT/100);
	private static final int PLAY_BUTTON_WIDTH = (int) (TappyMan.WORLD_WIDTH/2 + TappyMan.WORLD_WIDTH/100);
	private static final int PLAY_BUTTON_HEIGHT = (int) (TappyMan.WORLD_HEIGHT/5 + TappyMan.WORLD_HEIGHT/100);
	private static final int PLAY_BUTTON_Y = (int) (TappyMan.WORLD_HEIGHT/2 + TappyMan.WORLD_HEIGHT/20);
	private static final int EXIT_BUTTON_Y = (int) (TappyMan.WORLD_HEIGHT/2 - TappyMan.WORLD_HEIGHT/4);
	
	final TappyMan game;
	
	Texture playButtonActive;
	Texture exitButtonActive;

	public MainMenuScreen (final TappyMan game) {
		this.game = game;
		playButtonActive = new Texture("start_button_active.png");
		exitButtonActive = new Texture("exit_button_active.png");

		game.scrollingBackground.setSpeedFixed(true);
		game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
		
		final MainMenuScreen mainMenuScreen = this;
		
		Gdx.input.setInputProcessor( new InputAdapter() {
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				int x = (int) (TappyMan.WORLD_WIDTH / 2 - EXIT_BUTTON_WIDTH / 2);
				if (game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y) {
					mainMenuScreen.dispose();
					Gdx.app.exit();
				}
				x = (int) (TappyMan.WORLD_WIDTH / 2 - PLAY_BUTTON_WIDTH / 2);
				if (game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y) {
					mainMenuScreen.dispose();
					game.setScreen(new MainGameScreen(game));
				}
				
				return super.touchUp(screenX, screenY, pointer, button);
			}
			
		});
	}
	
	@Override
	public void show () {
		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();

		// scrolling
		game.scrollingBackground.updateAndRender(delta, game.batch );

		// buttons
		int x = (int) (TappyMan.WORLD_WIDTH / 2 - EXIT_BUTTON_WIDTH / 2);
		game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		if (game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y) {
			game.batch.draw( exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT );
		}

		x = (int) (TappyMan.WORLD_WIDTH / 2 - PLAY_BUTTON_WIDTH / 2);
		game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		if (game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && TappyMan.WORLD_HEIGHT - game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y) {
			game.batch.draw( playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT );
		}

		game.batch.end();
	}

	@Override
	public void resize (int width, int height) {
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

}
