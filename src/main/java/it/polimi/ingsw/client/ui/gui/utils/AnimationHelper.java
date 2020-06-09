package it.polimi.ingsw.client.ui.gui.utils;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class handles complex animations on elements of the GUI.
 */
public class AnimationHelper {

    /**
     * Structure holding all the timer threads handling the animations.
     */
    private final List<Timer> animationTimers = new LinkedList<>();

    /**
     * This method creates a new timer to show a loading animation on an ImageView using
     * the images in the imageArray, shown in sequence.
     *
     * @param imageArray   the image array
     * @param loadingImage the loading image
     */
    public void animateLoadingScreen(Image[] imageArray, ImageView loadingImage) {

        Timer animationTimer = new Timer(true);

        animationTimer.schedule(new TimerTask() {
            int imgCount = 0;

            @Override
            public void run() {
                Platform.runLater(() -> loadingImage.setImage(imageArray[imgCount]));
                if (imgCount == imageArray.length - 1) {
                    imgCount = 0;
                } else {
                    imgCount++;
                }
            }
        }, 0, 1000);

        animationTimers.add(animationTimer);
    }

    /**
     * Shuts down the animations.
     */
    public void stopAnimations() {
        animationTimers.forEach(Timer::cancel);
        animationTimers.clear();
    }
}
