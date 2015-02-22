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
        return pos.distance(other.getPos());
    }

    public boolean intersectBoundingSphere(BoundingSphere other) {
        return getDistance(other) <= radius + other.getRadius();
    }

    public boolean intersectAABB(AABB other) {
        Vector2f min = other.getMin();
        Vector2f max = other.getMax();

        float distance = 0;
        float s;

        if(pos.getX() < min.getX()) {
            s = pos.getX() - min.getX();
            distance += s * s;
        } else if(pos.getX() > max.getX()) {
            s = pos.getX() - max.getX();
            distance += s * s;
        }

        if(pos.getY() < min.getY()) {
            s = pos.getY() - min.getY();
            distance += s * s;
        } else if(pos.getY() > max.getY()) {
            s = pos.getY() - max.getY();
            distance += s * s;
        }

        return distance <= radius * radius;
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