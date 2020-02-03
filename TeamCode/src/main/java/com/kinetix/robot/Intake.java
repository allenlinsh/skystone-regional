package com.kinetix.robot;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    /**
     * Declare intake variables
     */
    private Robot robot = new Robot();
    public DcMotor motors[] = new DcMotor[2];

    /**
     *
     */
    public Intake() {
        this.motors[0] = robot.il;
        this.motors[1] = robot.ir;
    }

    /**
     * Intake stones into the robot
     */
    public void intake() {
        motors[0].setPower(1);
        motors[1].setPower(-1);
    }

    /**
     * Eject stones out of the robot
     */
    public void eject() {
        motors[0].setPower(-1);
        motors[1].setPower(1);
    }

    /**
     * Stop all intake motors
     */
    public void stopMotors() {
        for (int i = 0; i < 2; i++) {
            motors[i].setPower(0);
        }
    }
}