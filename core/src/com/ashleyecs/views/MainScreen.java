package com.ashleyecs.views;

import com.ashleyecs.AshleyGame;
import com.ashleyecs.GameModel;
import com.ashleyecs.controller.KeyboardController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class MainScreen implements Screen {

    private AshleyGame parent;
    private GameModel model;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private TextureAtlas.AtlasRegion playerTex;
    private SpriteBatch sb;
    private TextureAtlas atlas;

    /*------------------------------------------------------------------*\
    |*							Constructors
    \*------------------------------------------------------------------*/

    public MainScreen(AshleyGame parent) {
        this.parent = parent;
        cam = new OrthographicCamera(32, 24);
        controller = new KeyboardController();
        model = new GameModel(controller, cam, parent.assMan);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);

        atlas = parent.assMan.manager.get("images/game.atlas");
        playerTex = atlas.findRegion("player");
    }

    /*------------------------------------------------------------------*\
    |*							Public Methods
    \*------------------------------------------------------------------*/

    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        sb.draw(playerTex, model.player.getPosition().x - 1, model.player.getPosition().y - 1, 2, 2);
        sb.end();

        debugRenderer.render(model.world, cam.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
