package com.ashleyecs.views;

import com.ashleyecs.AshleyGame;
import com.ashleyecs.AshleyModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class MainScreen implements Screen {

    private AshleyModel model;
    private OrthographicCamera cam;
    private Box2DDebugRenderer debugRenderer;

    private AshleyGame parent;

    /*------------------------------------------------------------------*\
    |*							Constructors
    \*------------------------------------------------------------------*/

    public MainScreen(AshleyGame parent) {
        this.parent = parent;
        model = new AshleyModel();
        cam = new OrthographicCamera(32,24);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
    }

    /*------------------------------------------------------------------*\
    |*							Public Methods
    \*------------------------------------------------------------------*/

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        model.logicStep(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
