package tjc.rug.ExpertSystem.controller;

import javafx.animation.RotateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Used to animate elements within the GUI. Currently just consists of a method to rotate circles, however
 * more may be added.
 */
public class AnimateElement {

    /**
     * Causes a circle to rotate
     * @param c         The circle to animate
     * @param reverse   Whether the direction will revers
     * @param angle     The total degrees to move in a given time
     * @param duration  The duration before reversing
     */
    public static void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }
}
