package org.brentwardindustries.entity;

public enum Direction {
    CENTER,
    UP_LEFT,
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    ANY;

    public Direction opposite() {
        Direction oppositeDirection = Direction.ANY;
        switch (this) {
            case UP_LEFT -> oppositeDirection = DOWN_RIGHT;
            case UP -> oppositeDirection = DOWN;
            case UP_RIGHT -> oppositeDirection = DOWN_LEFT;
            case RIGHT -> oppositeDirection = LEFT;
            case DOWN_RIGHT -> oppositeDirection = UP_LEFT;
            case DOWN ->  oppositeDirection = UP;
            case DOWN_LEFT -> oppositeDirection = UP_RIGHT;
            case LEFT ->  oppositeDirection = RIGHT;
            case ANY -> oppositeDirection = CENTER;
        }
        return oppositeDirection;
    }
    public int speed(float currentSpeed) {
        int speed = 0;
        switch (this) {
            case UP_RIGHT, RIGHT, DOWN_RIGHT -> speed = (int) currentSpeed;
            case DOWN_LEFT, LEFT, UP_LEFT -> speed =  (int) (currentSpeed * -1.0f);
        }
        return speed;
    }

    public Direction horizontalDirection() {
        Direction horizontalDirection = Direction.CENTER;
        switch (this) {
            case UP_LEFT, DOWN_LEFT, LEFT -> horizontalDirection = LEFT;
            case UP_RIGHT, RIGHT, DOWN_RIGHT -> horizontalDirection = RIGHT;
        }
        return horizontalDirection;
    }

    public Direction verticalDirection() {
        Direction verticalDirection = Direction.CENTER;
        switch (this) {
            case UP_LEFT, UP, UP_RIGHT -> verticalDirection = UP;
            case DOWN_RIGHT, DOWN, DOWN_LEFT -> verticalDirection = DOWN;
        }
        return verticalDirection;
    }
}