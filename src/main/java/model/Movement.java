package model;

public class Movement {
    private final Position sourcePosition;
    private final Position targetPosition;

    public Movement() {
        this.sourcePosition = new Position();
        this.targetPosition = new Position();
    }

    public Movement(Position sourcePosition, Position targetPosition) {
        this.sourcePosition = new Position(sourcePosition);
        this.targetPosition = new Position(targetPosition);
    }

    public Movement(Movement movement) {
        this.sourcePosition = movement.getSourcePosition();
        this.targetPosition = movement.getTargetPosition();
    }

    private Position getSourcePosition() {
        return new Position(this.sourcePosition);
    }

    private Position getTargetPosition() {
        return new Position(this.targetPosition);
    }

    public boolean isEmpty() {
        return (sourcePosition.isEmpty() && targetPosition.isEmpty());
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "";
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.sourcePosition.toString()).append("-");
        stringBuilder.append(this.targetPosition.toString());
        return stringBuilder.toString();
    }
}