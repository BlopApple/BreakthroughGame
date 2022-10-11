package model;

public class MovementPair {
    private final Movement blackMovement;
    private final Movement whiteMovement;

    public MovementPair() {
        this.blackMovement = new Movement(new Position(), new Position());
        this.whiteMovement = new Movement(new Position(), new Position());
    }

    public MovementPair(Movement blackMovement, Movement whitMovement) {
        this.blackMovement = new Movement(blackMovement);
        this.whiteMovement = new Movement(whitMovement);
    }

    public Movement getBlackMovement() {
        return new Movement(this.blackMovement);
    }

    public Movement getWhiteMovement() {
        return new Movement(this.whiteMovement);
    }
}
