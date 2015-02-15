package nona.starwars.engine.core;

public class Time {

    public static final long SECOND = 1000000000L;

    public static double getTime() {
        return System.nanoTime() / SECOND;
    }

}