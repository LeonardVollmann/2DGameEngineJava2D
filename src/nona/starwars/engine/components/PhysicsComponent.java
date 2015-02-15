package nona.starwars.engine.components;

import nona.starwars.engine.core.Constants;
import nona.starwars.engine.core.EntityComponent;
import nona.starwars.engine.core.Vector2f;

public class PhysicsComponent extends EntityComponent {

	private Vector2f vel;

    public PhysicsComponent(Vector2f vel) {
        this.vel = vel;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        entity.setPos(entity.getPos().add(vel.mul(delta)));
    }

	public Vector2f getVel() {
		return vel;
	}

    @Override
    public String getName() {
        return Constants.COMPONENT_PHYSICS;
    }

}
