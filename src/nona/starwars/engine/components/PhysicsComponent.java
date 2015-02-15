package nona.starwars.engine.components;

import nona.starwars.engine.core.Constants;
import nona.starwars.engine.core.EntityComponent;
import nona.starwars.engine.core.Vector2f;
import nona.starwars.engine.physics.AABB;

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

    public void collide(PhysicsComponent other) {
        // TODO: Refine collision system (Different colliders, different ways to respond to collisions (elasticity, etc.)). Also change the algorithm to work for aabbs on top of each other.

        float distX = getAABB().getDistanceX(other.getAABB());
        float distY = getAABB().getDistanceY(other.getAABB());
        if(distY > distX) {
            distX = 0.0f;
            vel.setY(vel.getY() * -1);
        } else if(distX > distY) {
            distY = 0.0f;
            vel.setX(vel.getX() * -1);
        } else {
            vel.setX(vel.getX() * -1);
            vel.setY(vel.getY() * -1);
        }
    }

	public Vector2f getVel() {
		return vel;
	}

    @Override
    public String getName() {
        return Constants.COMPONENT_PHYSICS;
    }

    public AABB getAABB() {
        return getEntity().getAABB();
    }

}
