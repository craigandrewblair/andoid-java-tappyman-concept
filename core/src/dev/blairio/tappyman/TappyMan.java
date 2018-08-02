package dev.blairio.tappyman;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import dev.blairio.tappyman.screens.MainMenuScreen;
import dev.blairio.tappyman.tools.GameCamera;
import dev.blairio.tappyman.tools.ScrollingBackground;

public class TappyMan extends com.badlogic.gdx.Game {

	public static final float WORLD_WIDTH = 800.0f; // ratio 4:3
	public static final float WORLD_HEIGHT = 600.0f; // ratio 4:3
	public static boolean IS_MOBILE = false;
	
	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public GameCamera cam;
	private Viewport mViewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		cam = new GameCamera(WORLD_WIDTH , WORLD_HEIGHT);
		mViewport = new StretchViewport( WORLD_WIDTH, WORLD_HEIGHT, cam );
		mViewport.apply();
		
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
			IS_MOBILE = true;
		IS_MOBILE = true;
		
		this.scrollingBackground = new ScrollingBackground("background-grass-horizontal.png");
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.update(width, height);
		super.resize(width, height);
	}
	
}
