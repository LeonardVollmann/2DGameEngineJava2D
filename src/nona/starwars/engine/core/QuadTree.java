package nona.starwars.engine.core;

import nona.starwars.engine.physics.AABB;

import java.util.HashSet;

public class QuadTree {

    private QuadTree[] nodes;
    private Entity entity;
    private AABB aabb;

    public QuadTree(Entity entity, AABB aabb) {
        this.nodes = new QuadTree[4];
        this.entity = entity;
        this.aabb = aabb;
    }

    public void print() {
        print(0, "NW");
    }

    private void print(int depth, String location) {
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "-";
        }

        System.out.println(prefix + location + ": " + entity);

        if (nodes[0] != null) {
            nodes[0].print(depth + 1, "NW");
        }
        if (nodes[1] != null) {
            nodes[1].print(depth + 1, "NE");
        }
        if (nodes[2] != null) {
            nodes[2].print(depth + 1, "SW");
        }
        if (nodes[3] != null) {
            nodes[3].print(depth + 1, "SE");
        }
    }

    public HashSet<Entity> queryRange(AABB range) {
        HashSet<Entity> entities = new HashSet<Entity>();

        queryRangeInternal(range, entities);

        return entities;
    }

    private void queryRangeInternal(AABB range, HashSet<Entity> entities) {
        if(!aabb.intersectAABB(range)) {
            return;
        }

        if(entity.getAABB().intersectAABB(range)) {
            entities.add(entity);
        }

        for(int i = 0; i < 4; i++) {
            if(nodes[i] != null) {
                nodes[i].queryRangeInternal(range, entities);
            }
        }
    }

    public void add(Entity entity) {
        if(entity.getAABB().intersectAABB(aabb)) {
            addToChild(entity);
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