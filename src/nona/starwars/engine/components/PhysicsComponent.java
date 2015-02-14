package nona.starwars.engine.components;

import nona.starwars.engine.core.EntityComponent;

public class PhysicsComponent extends EntityComponent {

    @Override
    public void update(float delta) {
        super.update(delta);

        entity.setPos(entity.getPos().add(entity.getVel().mul(delta)));
    }

}