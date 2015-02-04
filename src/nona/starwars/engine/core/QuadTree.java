package nona.starwars.engine.core;

import nona.starwars.engine.physics.AABB;

public class QuadTree {

    private QuadTree[] nodes;
    private Entity entity;
    private AABB aabb;

    public QuadTree(Entity entity, AABB aabb) {
        this.nodes = new QuadTree[4];
        this.entity = entity;
        this.aabb = aabb;
    }

    public void add(Entity entity) {
        if(entity.getAABB().intersectAABB(aabb)) {

        } else {
            System.err.println("Error: AABB not in quad tree!");
            System.exit(1);
        }
    }

    public void addToChild(Entity entity) {
        float halfXLength = aabb.getWidth() / 2.0f;
        float halfYLength = aabb.getHeight() / 2.0f;

        float minX = aabb.getMin().getX();
        float minY = aabb.getMin().getY();
        float maxX = aabb.getMax().getX();
        float maxY = aabb.getMax().getY();

        tryToAddToChildNode(entity, minX, minY + halfYLength, maxX - halfXLength, maxY, 0);

        tryToAddToChildNode(entity, minX + halfXLength, minY + halfYLength, maxX, maxY, 1);

        tryToAddToChildNode(entity, minX, minY, maxX - halfXLength, maxY - halfYLength, 2);

        tryToAddToChildNode(entity, minX + halfXLength, minY, maxX, maxY - halfYLength, 3);
    }

    private void tryToAddToChildNode(Entity entity, float minX, float minY, float maxX, float maxY, int nodeIndex) {
        if(entity.getAABB().intersectRect(minX, minY, maxX, maxY)) {
            if(nodes[nodeIndex] == null) {
                nodes[nodeIndex] = new QuadTree(entity, new AABB(minX, minY, maxX, maxY));
            } else {
                nodes[nodeIndex].addToChild(entity);
            }
        }
    }

}