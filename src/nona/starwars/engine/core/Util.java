package nona.starwars.engine.core;

public class Util {

    public static float clamp(float value, float min, float max) {
        if(value < min) {
            value = min;
        } else if(value > max) {
            value = max;
        }

        return value;
    }

}
