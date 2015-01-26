package nona.starwars;

import nona.starwars.engine.core.CoreEngine;
import nona.starwars.game.StarWars;

public class   Main {

    public static void main(String[] args) {
        StarWars game = new StarWars();
        CoreEngine engine = new CoreEngine(game, 60);
        game.setEngine(engine);

        engine.start();
    }

}