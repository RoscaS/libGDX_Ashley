package com.ashleyecs;

import com.ashleyecs.controllers.KeyboardController;
import com.ashleyecs.loader.AshleyAssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class AshleyModel {

    public World world;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;

    private Sound ping;
   	private Sound boing;

    public KeyboardController controller;
    private AshleyAssetManager assMan;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public static final int BOING_SOUND = 0;
   	public static final int PING_SOUND = 1;

    public boolean isSwimming = false;

    public Body player;
    /*------------------------------------------------------------------*\
    |*							Constructors
    \*------------------------------------------------------------------*/

    public AshleyModel(KeyboardController ctrlr, OrthographicCamera cam, AshleyAssetManager assMan) {
        this.assMan = assMan;

        camera = cam;
        controller = ctrlr;
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener(this));
        createFloor();
        //createObject();
        //createMovingObject();

        assMan.queueAddSounds();
        assMan.manager.finishLoading();

        ping = assMan.manager.get("sounds/ping.wav", Sound.class);
        boing = assMan.manager.get("sounds/boing.wav", Sound.class);



        // get our body factory singleton and store it in bodyFactory
        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        // add a player
        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody, false);

        // add some water
        Body water = bodyFactory.makeBoxPolyBody(1, -8, 40, 4, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody, false);

        water.setUserData("IAMTHESEA");

        // make the water a sensor so it doesn't obstruct our player
        bodyFactory.makeAllFixturesSensors(water);
    }

    /*------------------------------------------------------------------*\
    |*							Update Methods
    \*------------------------------------------------------------------*/

    public void logicStep(float delta) {

        if (controller.isMouse1Down && pointIntersectsBody(player, controller.mouseLocation)) {
            System.out.println("Player was clicked");
        }

        if (controller.left) {
            player.applyForceToCenter(-10, 0, true);
        } else if (controller.right) {
            player.applyForceToCenter(10, 0, true);
        } else if (controller.up) {
            player.applyForceToCenter(0, -10, true);
        }

        if (isSwimming) {
            player.applyForceToCenter(0, 40, true);
        }

        world.step(delta, 3, 3);
    }

    /*------------------------------------------------------------------*\
    |*							Public Methods
    \*------------------------------------------------------------------*/

    public boolean pointIntersectsBody(Body body, Vector2 mouseLocation) {
        Vector3 mousePos = new Vector3(mouseLocation, 0); //convert mouseLocation to 3D position
        camera.unproject(mousePos); // convert from screen potition to world position
        if (body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)) {
            return true;
        }
        return false;
    }

    public void playSound(int sound) {
        switch (sound) {
            case BOING_SOUND:
                boing.play();
                break;
            case PING_SOUND:
                ping.play();
                break;
        }
    }

    /*------------------------------------------------------------------*\
    |*							Private Methods
    \*------------------------------------------------------------------*/

    private void createObject() {
        // bodydef (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        // add it to the world
        bodyd = world.createBody(bodyDef);
        // set the shape (box: 50m wide, 1m tall)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);
        // set the props of the obj
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        // create the phisical obj in our body
        bodyd.createFixture(shape, 0.0f);
        // no longer use the shape so dispose
        shape.dispose();
    }

    private void createFloor() {
        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);
        // add it to the world
        bodys = world.createBody(bodyDef);
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject() {
        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0, -12);
        // add it to the world
        bodyk = world.createBody(bodyDef);
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);
        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);
        // we no longer use the shape object here so dispose of it.
        shape.dispose();
        bodyk.setLinearVelocity(0, 0.75f);
    }

}
