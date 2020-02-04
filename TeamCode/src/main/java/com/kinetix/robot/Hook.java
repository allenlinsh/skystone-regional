package com.kinetix.robot;

import com.qualcomm.robotcore.hardware.Servo;

public class Hook {
    /*
     * Declare hook variables
     */
    private Robot robot = new Robot();
    private Servo servos[] = new Servo[2];
    private int duration = 300; // time to complete servo movement (in milliseconds)

    /**
     * Constructor for hook
     */
    public Hook() {
        this.servos[0] = robot.hl;
        this.servos[1] = robot.hr;
    }

    /**
     * Set position for all servos
     * @param p0 position for left hook
     * @param p1 position for right hook
     */
    public void setPositionAll(double p0, double p1) {
        servos[0].setPosition(p0);
        servos[1].setPosition(p1);
    }

    /**
     * Set position to lock the foundation
     */
    public void lock() {
        setPositionAll(1, 0);
    }

    /**
     * Set position to unlock the foundation
     */
    public void unlock() {
        setPositionAll(0, 1);
    }

    /**
     * Initialize hook servos
     */
    public void init() {
        unlock();
    }
}