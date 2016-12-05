package de.alphahelix.alphalibary.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleArmorStandAnimator {

	/*
	 * ArmorStand as = Bukkit.getWorld("world").spawn(new Location(Bukkit.getWorld("world"), 0, 4, 0), ArmorStand.class); //spawn the armor stand
                as.setAI(false); //set some stuff
                as.setArms(true);
                as.setCollidable(false);
                as.setVisible(true);
                as.setRemoveWhenFarAway(false);
                as.setInvulnerable(true);
                new SimpleArmorStandAnimator(new File(getDataFolder(), "ani2.animc"), as); //actually register the armor stand
	 * 
	 * SimpleArmorStandAnimator.updateAll();
	 */

    /**
     * This is a map containing the already loaded frames. This way we don't
     * have to parse the same animation over and over.
     */
    private static Map<String, Frame[]> animCache = new HashMap<>();
    /**
     * This is a list with all the animator instances. This makes it easy to
     * update all the instances at one.
     */
    private static Set<SimpleArmorStandAnimator> animators = new HashSet<>();
    /**
     * The armor stand to animate
     */
    private ArmorStand armorStand;
    /**
     * The amount of frames this animation has
     */
    private int length;
    /**
     * All the frames of the animation
     */
    private Frame[] frames;
    /**
     * Says when the animation is paused
     */
    private boolean paused = false;
    /**
     * The current frame we're on
     */
    private int currentFrame;
    /**
     * The start location of the animation
     */
    private Location startLocation;
    /**
     * If this is true. The animator is going to guess the frames that aren't
     * specified
     */
    private boolean interpolate = false;
    /**
     * Constructor of the animator. Takes in the path to the file with the animation and the armor stand to animate.
     *
     * @param aniFile
     * @param armorStand
     */
    public SimpleArmorStandAnimator(File aniFile, ArmorStand armorStand) {
        // set all the stuff
        this.armorStand = armorStand;
        startLocation = armorStand.getLocation();
        // checks if the file has been loaded before. If so return the cached version
        if (animCache.containsKey(aniFile.getAbsolutePath())) {
            frames = animCache.get(aniFile.getAbsolutePath());
        } else {
            // File has not been loaded before so load it.
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(aniFile));
                String line;
                // create the current frame variable
                Frame currentFrame = null;
                while ((line = br.readLine()) != null) {
                    // set the length
                    if (line.startsWith("length")) {
                        length = (int) Float.parseFloat(line.split(" ")[1]);
                        frames = new Frame[length];
                    }
                    // sets the current frame
                    else if (line.startsWith("frame")) {
                        if (currentFrame != null) {
                            frames[currentFrame.frameID] = currentFrame;
                        }
                        int frameID = Integer.parseInt(line.split(" ")[1]);
                        currentFrame = new Frame();
                        currentFrame.frameID = frameID;
                    }
                    // check if we need to interpolate
                    else if (line.contains("interpolate")) {
                        interpolate = true;
                    }
                    // sets the position and rotation or the main armor stand
                    else if (line.contains("Armorstand_Position")) {
                        currentFrame.x = Float.parseFloat(line.split(" ")[1]);
                        currentFrame.y = Float.parseFloat(line.split(" ")[2]);
                        currentFrame.z = Float.parseFloat(line.split(" ")[3]);
                        currentFrame.r = Float.parseFloat(line.split(" ")[4]);
                    }
                    // sets the rotation for the middle
                    else if (line.contains("Armorstand_Middle")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.middle = new EulerAngle(x, y, z);
                    }
                    // sets the rotation for the right leg
                    else if (line.contains("Armorstand_Right_Leg")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.rightLeg = new EulerAngle(x, y, z);
                    }
                    // sets the rotation for the left leg
                    else if (line.contains("Armorstand_Left_Leg")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.leftLeg = new EulerAngle(x, y, z);
                    }
                    // sets the rotation for the left arm
                    else if (line.contains("Armorstand_Left_Arm")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.leftArm = new EulerAngle(x, y, z);
                    }
                    // sets the rotation for the right arm
                    else if (line.contains("Armorstand_Right_Arm")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.rightArm = new EulerAngle(x, y, z);
                    }
                    // sets the rotation for the head
                    else if (line.contains("Armorstand_Head")) {
                        float x = (float) Math.toRadians(Float.parseFloat(line.split(" ")[1]));
                        float y = (float) Math.toRadians(Float.parseFloat(line.split(" ")[2]));
                        float z = (float) Math.toRadians(Float.parseFloat(line.split(" ")[3]));
                        currentFrame.head = new EulerAngle(x, y, z);
                    }
                }
                if (currentFrame != null) {
                    frames[currentFrame.frameID] = currentFrame;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // make sure to close the stream!
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            // add the animation to the cache, else adding the whole cache thing has no point.
            animCache.put(aniFile.getAbsolutePath(), frames);
        }
        // register this instance of the animator
        animators.add(this);
    }

    /**
     * This void updates all the animator instances at once
     */
    public static void updateAll() {
        for (SimpleArmorStandAnimator ani : animators) {
            ani.update();
        }
    }

    /**
     * Returns all the animator instances
     */
    public static Set<SimpleArmorStandAnimator> getAnimators() {
        return animators;
    }

    /**
     * Clears the animation cache in case you want to update an animation
     */
    public static void clearCache() {
        animCache.clear();
    }

    /**
     * This method removes this instance from the animator instances list. When
     * you don't want to use this instance any more, you can call this method.
     */
    public void remove() {
        animators.remove(this);
    }

    /**
     * Pauses the animation
     */
    public void pause() {
        paused = true;
    }

    /**
     * Pauses the animation and sets the current frame to 0. It also updates the
     * animation one more time to set the armor stand to the first frame.
     */
    public void stop() {
        // set the current frame to 0 and update the frame and set it to 0 again
        currentFrame = 0;
        update();
        currentFrame = 0;
        paused = true;
    }

    /**
     * Plays the animation
     */
    public void play() {
        paused = false;
    }

    /**
     * Updates the animation and goes to the next frame
     */
    public void update() {
        // make sure that the animation isn't paused
        if (!paused) {
            // makes sure that the frame is in bounds
            if (currentFrame >= (length - 1) || currentFrame < 0) {
                currentFrame = 0;
            }
            // get the frame
            Frame f = frames[currentFrame];
            // checks if we need to interpolate. If so interpolate.
            if (interpolate) {
                if (f == null) {
                    f = interpolate(currentFrame);
                }
            }
            // make sure it's not null
            if (f != null) {
                // get the new location
                Location newLoc = startLocation.clone().add(f.x, f.y, f.z);
                newLoc.setYaw(f.r + newLoc.getYaw());
                // set all the values
                armorStand.teleport(newLoc);
                armorStand.setBodyPose(f.middle);
                armorStand.setLeftLegPose(f.leftLeg);
                armorStand.setRightLegPose(f.rightLeg);
                armorStand.setLeftArmPose(f.leftArm);
                armorStand.setRightArmPose(f.rightArm);
                armorStand.setHeadPose(f.head);
            }
            // go one frame higher
            currentFrame++;
        }
    }

    /**
     * Returns the current frame
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Sets the current frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Returns the armor stand this instance animates
     */
    public ArmorStand getArmorStand() {
        return armorStand;
    }

    /**
     * Returns the amount of frame this animation has
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the list of frames
     */
    public Frame[] getFrames() {
        return frames;
    }

    /**
     * Returns if the animation is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets the start location
     */
    public Location getStartLocation() {
        return startLocation;
    }

    /**
     * Sets the start location. If you want to teleport the armor stand this is
     * the recommended function
     *
     * @param location
     */
    public void setStartLocation(Location location) {
        startLocation = location;
    }

    /**
     * Returns interpolate
     */
    public boolean isInterpolated() {
        return interpolate;
    }

    /**
     * Sets interpolate
     */
    public void setInterpolated(boolean interpolate) {
        this.interpolate = interpolate;
    }

    /**
     * Returns an interpolated frame
     */
    private Frame interpolate(int frameID) {
        // get the minimum and maximum frames that are the closest
        Frame minFrame = null;
        for (int i = frameID; i >= 0; i--) {
            if (frames[i] != null) {
                minFrame = frames[i];
                break;
            }
        }
        Frame maxFrame = null;
        for (int i = frameID; i < frames.length; i++) {
            if (frames[i] != null) {
                maxFrame = frames[i];
                break;
            }
        }
        // make sure that those frame weren't the last one
        Frame res;

        if (maxFrame == null || minFrame == null) {
            if (maxFrame == null && minFrame != null) {
                return minFrame;
            }
            if (minFrame == null && maxFrame != null) {
                return maxFrame;
            }
            res = new Frame();
            res.frameID = frameID;
            return res;
        }
        // create the frame and interpolate
        res = new Frame();
        res.frameID = frameID;

        // this part calculates the distance the current frame is from the
        // minimum and maximum frame and this allows for an easy linear
        // interpolation
        float Dmin = frameID - minFrame.frameID;
        float D = maxFrame.frameID - minFrame.frameID;
        float D0 = Dmin / D;

        res = minFrame.mult(1 - D0, frameID).add(maxFrame.mult(D0, frameID), frameID);

        return res;
    }

    /**
     * The frame class. This class holds all the information of one frame.
     */
    public static class Frame {
        /**
         * The Frame ID
         */
        int frameID;
        /**
         * the location and rotation
         */
        float x, y, z, r;
        /**
         * The rotation of the body parts
         */
        EulerAngle middle;
        EulerAngle rightLeg;
        EulerAngle leftLeg;
        EulerAngle rightArm;
        EulerAngle leftArm;
        EulerAngle head;

        /**
         * This multiplies every value with another value. Used for
         * interpolation
         *
         * @param a
         * @param frameID
         * @return
         */
        public Frame mult(float a, int frameID) {
            Frame f = new Frame();
            f.frameID = frameID;
            f.x = f.x * a;
            f.y = f.y * a;
            f.z = f.z * a;
            f.r = f.r * a;
            f.middle = new EulerAngle(middle.getX() * a, middle.getY() * a, middle.getZ() * a);
            f.rightLeg = new EulerAngle(rightLeg.getX() * a, rightLeg.getY() * a, rightLeg.getZ() * a);
            f.leftLeg = new EulerAngle(leftLeg.getX() * a, leftLeg.getY() * a, leftLeg.getZ() * a);
            f.rightArm = new EulerAngle(rightArm.getX() * a, rightArm.getY() * a, rightArm.getZ() * a);
            f.leftArm = new EulerAngle(leftArm.getX() * a, leftArm.getY() * a, leftArm.getZ() * a);
            f.head = new EulerAngle(head.getX() * a, head.getY() * a, head.getZ() * a);
            return f;
        }

        /**
         * This adds a value to every value. Used for interpolation
         *
         * @param a
         * @param frameID
         * @return
         */
        public Frame add(Frame a, int frameID) {
            Frame f = new Frame();
            f.frameID = frameID;
            f.x = f.x + a.x;
            f.y = f.y + a.y;
            f.z = f.z + a.z;
            f.r = f.r + a.r;
            f.middle = new EulerAngle(middle.getX() + a.middle.getX(), middle.getY() + a.middle.getY(),
                    middle.getZ() + a.middle.getZ());
            f.rightLeg = new EulerAngle(rightLeg.getX() + a.rightLeg.getX(), rightLeg.getY() + a.rightLeg.getY(),
                    rightLeg.getZ() + a.rightLeg.getZ());
            f.leftLeg = new EulerAngle(leftLeg.getX() + a.leftLeg.getX(), leftLeg.getY() + a.leftLeg.getY(),
                    leftLeg.getZ() + a.leftLeg.getZ());
            f.rightArm = new EulerAngle(rightArm.getX() + a.rightArm.getX(), rightArm.getY() + a.rightArm.getY(),
                    rightArm.getZ() + a.rightArm.getZ());
            f.leftArm = new EulerAngle(leftArm.getX() + a.leftArm.getX(), leftArm.getY() + a.leftArm.getY(),
                    leftArm.getZ() + a.leftArm.getZ());
            f.head = new EulerAngle(head.getX() + a.head.getX(), head.getY() + a.head.getY(),
                    head.getZ() + a.head.getZ());
            return f;
        }
    }

}
