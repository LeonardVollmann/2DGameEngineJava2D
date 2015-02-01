package nona.starwars.engine.core;

public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(float value) {
        this(value, value);
    }

    public Vector2f(Vector2f other) {
        this(other.getX(), other.getY());
    }

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.getX(), y + other.getY());
    }

    public Vector2f add(float r) {
        return new Vector2f(x + r, y + r);
    }

    public Vector2f sub(Vector2f other) {
        return new Vector2f(x - other.getX(), y - other.getY());
    }

    public Vector2f sub(float r) {
        return new Vector2f(x - r, y - r);
    }

    public Vector2f mul(Vector2f other) {
        return new Vector2f(x * other.getX(), y * other.getY());
    }

    public Vector2f mul(float r) {
        return new Vector2f(x * r, y * r);
    }

    public Vector2f div(Vector2f other) {
        return new Vector2f(x / other.getX(), y / other.getY());
    }

    public Vector2f div(float r) {
        return new Vector2f(x / r, y / r);
    }

    public float length() {
        return (float)Math.sqrt((double)(x * x + y * y));
    }

    public float squaredLength() {
        return x * x + y * y;
    }

    public Vector2f normalized() {
        return new Vector2f(this.div(length()));
    }

    public Vector2f rotate(float angle) {
        x = x * (float)Math.cos((double)angle) - y * (float)Math.sin((double)angle);
        y = y * (float)Math.sin((double)angle) + x * (float)Math.sin((double)angle);
        return this;
    }

    public float dot(Vector2f other) {
        return x * other.getX() + y * other.getY();
    }

    public float cross(Vector2f other) {
        return x * other.getY() - y * other.getX();
    }

    public float angle() {
        return (float)Math.atan2(y, x);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}