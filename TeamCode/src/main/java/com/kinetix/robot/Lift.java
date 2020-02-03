package com.kinetix.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    /**
     * Declare lift variables
     */
    private Robot robot = new Robot();
    private DcMotor motor;
    private Servo servos[] = new Servo[4];

    /**
     * Constructor for lift
     */
    public Lift() {
        this.motor = robot.lift;
        this.servos[0] = robot.lt;
        this.servos[1] = robot.lb;
        this.servos[2] = robot.rt;
        this.servos[3] = robot.rb;
    }
}