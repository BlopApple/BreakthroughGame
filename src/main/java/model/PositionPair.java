package model;

public class PositionPair {
    private final Position sourcePosition;
    private final Position targetPosition;

    public PositionPair(Position sourcePosition, Position targetPosition) {
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.sourcePosition.toString()).append("-");
        stringBuilder.append(this.targetPosition.toString());
        return stringBuilder.toString();
    }
}