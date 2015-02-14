package nona.starwars.engine.physics;

import nona.starwars.engine.core.Vector2f;

public class AABB {

    private Vector2f min;
    private Vector2f max;

    public AABB(Vector2f min, Vector2f max) {
        this.min = min;
        this.max = max;
    }

    public AABB(float minX, float minY, float maxX, float maxY) {
        this.min = new Vector2f(minX, minY);
        this.max = new Vector2f(maxX, maxY);
    }

    public boolean intersectAABB(AABB other) {
        return intersectRect(other.getMin(), other.getMax());
    }

    public boolean intersectRect(Vector2f min, Vector2f max) {
        return this.min.getX() < max.getX() && this.max.getX() > min.getX()
                && this.min.getY() < max.getY() && this.max.getY() > min.getY();
    }

    public boolean intersectRect(float minX, float minY, float maxX, float maxY) {
        return intersectRect(new Vector2f(minX, minY), new Vector2f(maxX, maxY));
    }

    public float getDistanceX(AABB other) {
        float distance1 = getMin().getX() - other.getMax().getX();
        float distance2 = other.getMin().getX() - getMax().getX();

        float distance = distance1 > distance2 ? distance1 : distance2;
        return distance;
    }

    public float getDistanceY(AABB other) {
        float distance1 = getMin().getY() - other.getMax().getY();
        float distance2 = other.getMin().getY() - getMax().getY();

        float distance = distance1 > distance2 ? distance1 : distance2;
        return distance;
    }

    public Vector2f getDistance(AABB other) {
        return new Vector2f(getDistanceX(other), getDistanceY(other));
    }

    public float getWidth() {
        return max.getX() - min.getX();
    }

    public float getHeight() {
        return max.getY() - min.getY();
    }

    public Vector2f getCenter() {
        return new Vector2f(min.getX() + getWidth() / 2, min.getY() + getHeight() / 2);
    }

    public Vector2f getMin() {
        return min;
    }

    public void setMin(Vector2f min) {
        this.min = min;
    }

    public void setMinX(float minX) {
        min.setX(minX);
    }

    public void setMinY(float minY) {
        min.setY(minY);
    }

    public Vector2f getMax() {
        return max;
    }

    public void setMaxX(float maxX) {
        max.setX(maxX);
    }

    public void setMaxY(float maxY) {
        max.setY(maxY);
    }

    public void setMax(Vector2f max) {
        this.max = max;
    }

}