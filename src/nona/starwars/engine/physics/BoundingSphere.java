package nona.starwars.engine.physics;

import nona.starwars.engine.math.Vector2f;

public class BoundingSphere {

    private Vector2f pos;
    private float radius;

    public BoundingSphere(Vector2f pos, float radius) {
        this.pos = pos;
        this.radius = radius;
    }

    public Vector2f getDistanceXY(BoundingSphere other) {
        return pos.sub(other.getPos());
    }

    public float getDistance(BoundingSphere other) {
        Vector2f distanceXY = getDistanceXY(other);

        return (float)Math.sqrt(distanceXY.getX() * distanceXY.getX() + distanceXY.getY() * distanceXY.getY());
    }

    public boolean intersectBoundingSphere(BoundingSphere other) {
        return getDistance(other) <= radius + other.getRadius();
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}