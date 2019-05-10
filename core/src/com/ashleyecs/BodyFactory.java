package com.ashleyecs;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {

    private static BodyFactory thisInstance;
    private World world;

    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;

    /*------------------------------------------------------------------*\
    |*							Constructors
    \*------------------------------------------------------------------*/

    private BodyFactory(World world) {
        this.world = world;
    }

    /*------------------------------------------------------------------*\
    |*							Public Methods
    \*------------------------------------------------------------------*/

    /*------------------------------*\
    |*			  Circles
    \*------------------------------*/

    public Body makeCirclePolyBody(float x, float y, float r, int material) {
        return makeCirclePolyBody(x, y, r, material, BodyDef.BodyType.DynamicBody, false);
    }

    public Body makeCirclePolyBody(float x, float y, float r, int material, BodyDef.BodyType bodyType) {
        return makeCirclePolyBody(x, y, r, material, bodyType, false);
    }

    public Body makeCirclePolyBody(float x, float y, float r, int material, BodyDef.BodyType bodyType, boolean fixedR) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = fixedR;

        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(r / 2);
        boxBody.createFixture(makeFixture(material, circleShape));
        circleShape.dispose();
        return boxBody;
    }

    /*------------------------------*\
    |*			  Rectangles
    \*------------------------------*/

    public Body makeBoxPolyBody(float x, float y, float w, float h, int material, BodyDef.BodyType bodyType) {
        return makeBoxPolyBody(x, y, w, h, material, bodyType, false);
    }

    public Body makeBoxPolyBody(float x, float y, float w, float h, int material, BodyDef.BodyType bodyType, boolean fixedR) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = fixedR;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w / 2, h / 2);
        boxBody.createFixture(makeFixture(material, poly));
        poly.dispose();
        return boxBody;
    }

    /*------------------------------*\
    |*			  Polygones
    \*------------------------------*/

    public Body makePolygonShapeBody(Vector2[] vertices, float x, float y, int material, BodyDef.BodyType bodyType) {
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        Body boxBody = world.createBody(boxBodyDef);

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);
        boxBody.createFixture(makeFixture(material, polygon));
        polygon.dispose();
        return boxBody;
    }

    /*------------------------------------------------------------------*\
    |*							Sensors
    \*------------------------------------------------------------------*/

    public void makeConeSensor(Body body, float size) {

        FixtureDef fixtureDef = new FixtureDef();
        //fixtureDef.isSensor = true; // will add in future

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0, 0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) (i / 6.0 * 145 * MathUtils.degRad);
            vertices[i - 1] = new Vector2(radius * ((float) Math.cos(angle)), radius * ((float) Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        body.createFixture(fixtureDef);
        polygon.dispose();
    }

    public void makeAllFixturesSensors(Body body) {
        for (Fixture fix : body.getFixtureList()) {
            fix.setSensor(true);
        }
    }

    /*------------------------------------------------------------------*\
    |*							Static Methods
    \*------------------------------------------------------------------*/

    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) {
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    static public FixtureDef makeFixture(int material, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch (material) {
            case 0:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case 1:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case 2:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case 3:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }


}
