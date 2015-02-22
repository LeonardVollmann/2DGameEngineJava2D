package nona.starwars.engine.scene;

import nona.starwars.engine.math.Vector2f;
import nona.starwars.engine.entity.Entity;
import nona.starwars.engine.physics.AABB;

import java.util.Set;

public class QuadTree {

    private QuadTree[] nodes;
    private Entity[] entities;
    private int numEntities;
    private AABB aabb;

    public QuadTree(AABB aabb, int maxEntities) {
        this.nodes = new QuadTree[4];
        this.entities = new Entity[maxEntities];
        this.numEntities = 0;
        this.aabb = aabb;
    }

    public QuadTree(AABB aabb, QuadTree[] nodes, Entity[] entities, int numEntities) {
        this.aabb = aabb;
        this.nodes = nodes;
        this.entities = entities;
        this.numEntities = numEntities;
    }

    public void print() {
        print(0, "NW");
    }

    private void print(int depth, String location) {
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "-";
        }

        for(int i = 0; i < numEntities; i++) {
            System.out.println(prefix + location + i + ": " + entities[i]);
        }

        if (nodes[0] != null) {
            nodes[0].print(depth + 1, "NW ");
        }
        if (nodes[1] != null) {
            nodes[1].print(depth + 1, "NE ");
        }
        if (nodes[2] != null) {
            nodes[2].print(depth + 1, "SW ");
        }
        if (nodes[3] != null) {
            nodes[3].print(depth + 1, "SE ");
        }
    }

    public Set<Entity> queryRange(AABB range, Set<Entity> result) {
        if(!aabb.intersectAABB(range)) {
            return result;
        }

        for(int i = 0; i < numEntities; i++) {
            if(entities[i].getAABB().intersectAABB(range)) {
                result.add(entities[i]);
            }
        }

        for(int i = 0; i < 4; i++) {
            if(nodes[i] != null) {
                nodes[i].queryRange(range, result);
            }
        }

        return result;
    }

    public Set<Entity> getAll(Set<Entity> result) {
        return queryRange(aabb, result);
    }

    public void add(Entity entity) {
        if(entity.getAABB().intersectAABB(aabb)) {
            if(numEntities < entities.length) {
                entities[numEntities] = entity;
                numEntities++;
            } else {
                addToChild(entity);
            }
        } else {
            QuadTree thisAsNode = new QuadTree(aabb, nodes, entities, numEntities);

            Vector2f direction = entity.getPos().sub(aabb.getCenter());

            nodes = new QuadTree[4];
            numEntities = 0;
            entities = new Entity[entities.length];

            float minX = aabb.getMin().getX();
            float minY = aabb.getMin().getY();
            float maxX = aabb.getMax().getX();
            float maxY = aabb.getMax().getY();

            float width = aabb.getWidth();
            float height = aabb.getHeight();

            if(direction.getX() >= 0 && direction.getY() < 0) {
                nodes[0] = thisAsNode;
                aabb = new AABB(minX, minY - height, maxX + width, maxY);
            } else if(direction.getX() < 0 && direction.getY() < 0) {
                nodes[1] = thisAsNode;
                aabb = new AABB(minX - width, minY - height, maxX, maxY);
            } else if(direction.getX() >= 0 && direction.getY() >= 0) {
                nodes[2] = thisAsNode;
                aabb = new AABB(minX, minY, maxX + width, maxY + height);
            } else if(direction.getX() < 0 && direction.getY() >= 0) {
                nodes[3] = thisAsNode;
                aabb = new AABB(minX - width, minY, maxX, maxY + height);
            } else {
                System.err.println("Error: QuadTree expansion calculation failed: dirX = " + direction.getX() + ", dirY = " + direction.getY());
                System.exit(1);
            }

            add(entity);
        }
    }

    public boolean remove(Entity entity) {
        if (!entity.getAABB().intersectAABB(aabb)) {
            return false;
        }

        for (int i = 0; i < numEntities; i++) {
            if (entities[i] == entity) {
                removeEntityFromArray(i);
            }
        }

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[i].remove(entity)) {
                nodes[i] = null;
            }
        }

        return isEmpty();
    }

    private void addToChild(Entity entity) {
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
                nodes[nodeIndex] = new QuadTree(new AABB(minX, minY, maxX, maxY), entities.length);
            }

            nodes[nodeIndex].add(entity);
        }
    }

    private void removeEntityFromArray(int index) {
        for(int i = index + 1; i < numEntities; i++) {
            entities[i - 1] = entities[i];
        }
        entities[numEntities - 1] = null;
        numEntities--;
    }

    private boolean isEmpty() {
        for(int i = 0; i < 4; i++) {
            if(nodes[i] != null) {
                return false;
            }
        }

        return numEntities == 0;
    }

}