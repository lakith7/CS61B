package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;


public class Clorus extends Creature {

    private int r;

    private int g;

    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public void move() {
        this.energy -= 0.03;
    }

    public void stay() {
        this.energy -= 0.01;
    }

    public Color color() {
        r = 34;
        b = 0;
        g = 231;
        return color(r, g, b);
    }

    public void attack(Creature c) {
        this.energy += c.energy();
    }

    public Clorus replicate() {
        double holder = this.energy();
        this.energy = (holder/2);
        Clorus y = new Clorus(holder/2);
        return y;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        if (neighbors.get(Direction.TOP).name().equals("empty")) {
            emptyNeighbors.addFirst(Direction.TOP);
        }

        if (neighbors.get(Direction.BOTTOM).name().equals("empty")) {
            emptyNeighbors.addFirst(Direction.BOTTOM);
        }

        if (neighbors.get(Direction.LEFT).name().equals("empty")) {
            emptyNeighbors.addFirst(Direction.LEFT);
        }

        if (neighbors.get(Direction.RIGHT).name().equals("empty")) {
            emptyNeighbors.addFirst(Direction.RIGHT);
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        Deque<Direction> allPlips = new ArrayDeque<>();

        if (neighbors.get(Direction.TOP).name().equals("plip")) {
            allPlips.addFirst(Direction.TOP);
        }

        if (neighbors.get(Direction.BOTTOM).name().equals("plip")) {
            allPlips.addFirst(Direction.BOTTOM);
        }

        if (neighbors.get(Direction.LEFT).name().equals("plip")) {
            allPlips.addFirst(Direction.LEFT);
        }

        if (neighbors.get(Direction.RIGHT).name().equals("plip")) {
            allPlips.addFirst(Direction.RIGHT);
        }

        if (!allPlips.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(allPlips));
        }

        if (this.energy > 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));


    }


}
