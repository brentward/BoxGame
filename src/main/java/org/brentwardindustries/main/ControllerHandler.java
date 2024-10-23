package org.brentwardindustries.main;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import org.brentwardindustries.entity.Direction;

public class ControllerHandler {
    GamePanel gp;
    boolean controllerConnected = false;
    Controller controller;

    public ControllerHandler(GamePanel gp) {
        this.gp = gp;

        Controller[] controllers = ControllerEnvironment
                .getDefaultEnvironment().getControllers();

        if (controllers.length > 0) {
            controllerConnected = true;
        }

        for (Controller potentialController : controllers) {
            if (potentialController.getType() == Controller.Type.GAMEPAD) {
                controller = potentialController;
                break;
            }
        }
    }

    public void pollController() {
        if (controller != null) {
            controller.poll();
            EventQueue queue = controller.getEventQueue();
            Event event = new Event();

            while (queue.getNextEvent(event)) {
                Component comp = event.getComponent();
                String id = comp.getIdentifier().getName();
                float value = event.getValue();
                switch (id) {
                    case "0", "B" -> gp.inputState.jump = value == 1f; // A
                    case "1", "A" -> {} // B
                    case "2", "Y" -> gp.inputState.dash = value == 1f; // X
                    case "3", "X" -> {} // Y
                    case "4", "Right Thumb" ->{} // L
                    case "5", "Left Thumb" -> {} // R
                    case "6", "Select" -> {} // SELECT
                    case "7", "Start" -> gp.inputState.pause = value == 1f; // START
                    case "8", "Left Thumb 3" -> {} // L3
                    case "9", "Right Thumb 3" -> {} // R3
                    case "pov" -> {
                        if (value == Component.POV.CENTER) {
                            gp.inputState.inputDirection = Direction.CENTER;
//                            gp.inputState.up = false;
//                            gp.inputState.down = false;
//                            gp.inputState.left = false;
//                            gp.inputState.right = false;
                        } else if (value == Component.POV.UP_LEFT) {
                            gp.inputState.inputDirection = Direction.UP_LEFT;
//                            gp.inputState.up = true;
//                            gp.inputState.down = false;
//                            gp.inputState.left = true;
//                            gp.inputState.right = false;
                        } else if (value == Component.POV.UP) {
                            gp.inputState.inputDirection = Direction.UP;
//                            gp.inputState.up = true;
//                            gp.inputState.down = false;
//                            gp.inputState.left = false;
//                            gp.inputState.right = false;
                        } else if (value == Component.POV.UP_RIGHT) {
                            gp.inputState.inputDirection = Direction.UP_RIGHT;
//                            gp.inputState.up = true;
//                            gp.inputState.down = false;
//                            gp.inputState.left = false;
//                            gp.inputState.right = true;
                        } else if (value == Component.POV.RIGHT) {
                            gp.inputState.inputDirection = Direction.RIGHT;
//                            gp.inputState.up = false;
//                            gp.inputState.down = false;
//                            gp.inputState.left = false;
//                            gp.inputState.right = true;
                        } else if (value == Component.POV.DOWN_RIGHT) {
                            gp.inputState.inputDirection = Direction.DOWN_RIGHT;
//                            gp.inputState.up = false;
//                            gp.inputState.down = true;
//                            gp.inputState.left = false;
//                            gp.inputState.right = true;
                        } else if (value == Component.POV.DOWN) {
                            gp.inputState.inputDirection = Direction.DOWN;
//                            gp.inputState.up = false;
//                            gp.inputState.down = true;
//                            gp.inputState.left = false;
//                            gp.inputState.right = false;
                        } else if (value == Component.POV.DOWN_LEFT) {
                            gp.inputState.inputDirection = Direction.DOWN_LEFT;
//                            gp.inputState.up = false;
//                            gp.inputState.down = true;
//                            gp.inputState.left = true;
//                            gp.inputState.right = false;
                        } else if (value == Component.POV.LEFT) {
                            gp.inputState.inputDirection = Direction.LEFT;
//                            gp.inputState.up = false;
//                            gp.inputState.down = false;
//                            gp.inputState.left = true;
//                            gp.inputState.right = false;
                        }
                    }
                    case "y" -> {} // LY
                    case "x" -> {} // LX
                    case "ry" -> {} // RY
                    case "rx" -> {} // RX
                    case "z" -> {} // L2 and R2
                    default -> throw new IllegalStateException("Unexpected value: " + id);
                }
            }
        }
    }
}
