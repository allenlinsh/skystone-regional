package com.kinetix.robot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    /**
     * Declare intake variables
     */
    private Robot robot = new Robot();
    public DcMotor motors[] = new DcMotor[2];

    /**
     * Constructor for intake
     */
    public Intake() {
        this.motors[0] = robot.il;
        this.motors[1] = robot.ir;
    }

    /**
     * Set power for intake motors
     * @param p0
     * @param p1
     */
    public void setPowerAll(double p0, double p1) {
        motors[0].setPower(p0);
        motors[1].setPower(p1);
    }

    /**
     * Intake stones into the robot
     */
    public void intake() {
        setPowerAll(1, -1);
    }

    /**
     * Eject stones out of the robot
     */
    public void eject() {
        setPowerAll(-1, 1);
    }

    /**
     * Stop all intake motors
     */
    public void stopMotors() {
        for (int i = 0; i < 2; i++) {
            motors[i].setPower(0);
        }
    }

    /**
     * Initialize intake motors
     */
    public void init() {
        for (int i = 0; i < 2; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors[i].setPower(0);
        }
    }
}