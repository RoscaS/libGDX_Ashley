package com.ashleyecs;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class AshleyModel {

    public World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Body bodyd;
    private Body bodys;
    private Body bodyk;

    public boolean isSwimming = false;
    private Body player;

    /*------------------------------------------------------------------*\
    |*							Constructors
    \*------------------------------------------------------------------*/

    public AshleyModel() {
        world = new World(new Vector2(0,-10f), true);
       	world.setContactListener(new B2dContactListener(this));
       	createFloor();
       	//createObject();
       	//createMovingObject();

       	// get our body factory singleton and store it in bodyFactory
       	BodyFactory bodyFactory = BodyFactory.getInstance(world);

       	// add a player
       	player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody,false);

       	// add some water
       	Body water =  bodyFactory.makeBoxPolyBody(1, -8, 40, 4, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody,false);
       	// make the water a sensor so it doesn't obstruct our player
       	bodyFactory.makeAllFixturesSensors(water);
        water.setUserData("IAMTHESEA");
    }

    /*------------------------------------------------------------------*\
    |*							Update Methods
    \*------------------------------------------------------------------*/

    public void logicStep(float delta) {
        if(isSwimming){
      		player.applyForceToCenter(0, 50, true);
      	}
      	world.step(delta , 3, 3);
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
