package org.brentwardindustries.main;

import org.brentwardindustries.entity.Direction;

public class InputState {
    public Direction inputDirection;
//    public boolean up;
//    public boolean down;
//    public boolean left;
//    public boolean right;
    public boolean jump;
    public boolean dash;
    public boolean pause;

    public InputState() {
        inputDirection = Direction.CENTER;
    }
}
