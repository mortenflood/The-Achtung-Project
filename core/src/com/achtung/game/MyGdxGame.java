package com.achtung.game;

import com.achtung.game.multiplayer.WarpController;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Color;
import java.util.ArrayList;


public class MyGdxGame extends ApplicationAdapter {


	public static int WIDTH;
	public static int HEIGHT;
	private ArrayList<Player> players, losingplayers;
	private ShapeRenderer renderer;
	private Texture left, right, exit;
	private SpriteBatch sprites;
	public static OrthographicCamera cam;
	private AchtungInputProcessor processor;
	private Player mainPlayer;
	private int gapCounter;
	private Player gapPlayer;
	private Viewport viewport;

	private final int WORLD_HEIGHT = 1280;
	private final int WORLD_WIDTH = 720;

	private float heightScale;
	private float widthScale;

	//lage worldcoordinates
	//fit til screen
	//bruke worldcoordinates til alle tester
	//vise på skjermen
	//


	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		gapCounter = 0;
		gapPlayer = null;


		players = new ArrayList<Player>();
		losingplayers = new ArrayList<Player>();
		renderer = new ShapeRenderer();
		sprites = new SpriteBatch();

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		heightScale = HEIGHT/WORLD_HEIGHT;
		widthScale = WIDTH/WORLD_WIDTH;

		left = new Texture("leftarrowfinal.png");
		right = new Texture("rightarrowfinal.png");
		exit = new Texture("exitfinal.png");

		//cam = new OrthographicCamera(WIDTH, HEIGHT);
		//viewport = new StretchViewport(500, 800, cam);
		//cam.translate(WIDTH / 2, HEIGHT / 2);


		//Position pos1 = new Position(100, 100);
		//Position pos2 = new Position(500, 500);

		Player p1 = new Player(MathUtils.round(500*widthScale), MathUtils.round(500*heightScale), 2,
				com.badlogic.gdx.graphics.Color.BLUE);
		//Player p2 = new Player(500, 1000, 2, com.badlogic.gdx.graphics.Color.RED);

		players.add(p1);
		//players.add(p2);

		mainPlayer = p1;

		processor = new AchtungInputProcessor(mainPlayer);
		Gdx.input.setInputProcessor(processor);

		//WarpController wc = new WarpController();

	}

	@Override
	public void resize(int width, int height){
		//viewport.update(width, height);
		//cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
	}

	@Override
	public void render () {

		//check if the game is over
		//if one player left
		//double list solution?
		// new activity with another interface/back to lobby
		//

		// is game over?
		//if(losingplayers.size() >= players.size() - 1) {
		//return;
		// return to lobby
		//}

		//cam.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sprites.begin();
		sprites.draw(left, 0f, 0f, 4 * WIDTH / 10, HEIGHT / 10);
		sprites.draw(right, 6 * WIDTH / 10, 0f, 4 * WIDTH / 10, HEIGHT / 10);
		//sprites.draw(exit, Gdx.graphics.getWidth()/2, 500f, 300f, 300f);
		sprites.end();



		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
		renderer.rectLine(10f, HEIGHT / 10, 10f, HEIGHT - 5f, 10f);
		renderer.rectLine(10f, HEIGHT / 10 + 5f, WIDTH - 10f, HEIGHT / 10 + 5f, 10f);
		renderer.rectLine(10f, HEIGHT - 10f, WIDTH - 10f, HEIGHT - 10f, 10f);
		renderer.rectLine(WIDTH - 10f, HEIGHT / 10, WIDTH - 10f, HEIGHT - 5f, 10f);
		renderer.end();


		for(Player p:players) {
			if (checkCollision(p)) {
				p.setSpeed(0);
				//improve performance if needed
				//remove from game
				// make new list with all players, remove from list, add to new?
				losingplayers.add(p);
			}

			//update players list, remove losingplayers

			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(p.getColor());
			for (Position pos:p.getPath()) {
				renderer.circle(pos.getxPos(), pos.getyPos(), 5);
			}
			renderer.circle(p.getxPos(), p.getyPos(), 5);
			renderer.end();

			//p.setyPos(p.getyPos() + p.getSpeed());
			// sett ny pos her

			//update radians her
			if(p.isMoveLeft()) {
				p.setRadians(p.getRadians() + p.getRotationSpeed());
			}
			else if (p.isMoveRight()) {
				p.setRadians(p.getRadians() - p.getRotationSpeed());
			}

			p.setxPos(MathUtils.round(p.getxPos() + (MathUtils.cos(p.getRadians()) * p.getSpeed())));
			p.setyPos(MathUtils.round(p.getyPos() + (MathUtils.sin(p.getRadians()) * p.getSpeed())));

			//sometimes dont add to path
			//to create illusion of not being drawn

			if(gapCounter == 0) {
				if(MathUtils.random() > 0.99) {
					gapCounter++;
					gapPlayer = p;
					return;
				}
			}

			if(!p.equals(gapPlayer)) {
				p.addToPath(new Position(p.getxPos(), p.getyPos()));
			}

			if (p.equals(gapPlayer)) {
				gapCounter++;
				if (gapCounter >= 20) {
					gapCounter = 0;
					gapPlayer = null;
				}
			}
		}

	}

	public boolean checkCollision(Player p) {

		if(p.getxPos() <= 15f || p.getxPos() >= Gdx.graphics.getWidth() - 15f
				|| p.getyPos() <= Gdx.graphics.getHeight()/10 + 5f || p.getyPos() >= Gdx.graphics.getHeight() - 15f) {
			return true;
		}

		for(Player plyr:players) {


			if (p.equals(plyr) && p.getPath().size() > 10) {
				for(int i = 0; i < p.getPath().size() - 10; i++ ) {
					if((p.getxPos() < p.getPath().get(i).getxPos() + 6 && p.getxPos() > p.getPath().get(i).getxPos() - 6)
							&& (p.getyPos() < p.getPath().get(i).getyPos() + 6 && p.getyPos() > p.getPath().get(i).getyPos() - 6)) {
						return true;
					}
				}
			}

			if(p.equals(plyr)) {
				continue;
			}

			for(Position pos:plyr.getPath()) {
				if((p.getxPos() < pos.getxPos() + 6 && p.getxPos() > pos.getxPos() - 6)
						&& (p.getyPos() < pos.getyPos() + 6 && p.getyPos() > pos.getyPos() - 6)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		sprites.dispose();
	}
}
