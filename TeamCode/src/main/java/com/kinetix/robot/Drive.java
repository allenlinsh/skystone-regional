package com.kinetix.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Drive {
    /*
     * Declare motor variables
     */
    private static final double WHEEL_DIAMETER = 3.75;
    private static final double TICKS_PER_REV = 480;
    private static final double IN_PER_REV = Math.PI * WHEEL_DIAMETER / TICKS_PER_REV; // ticks = inches / IN_PER_REV;
    public DcMotor motors[] = new DcMotor[4];

    public Drive() {
        Robot robot = new Robot();

        /*
         * Initialize the motor hardware and configure the initial status
         */
        motors[0] = robot.m0;
        motors[1] = robot.m1;
        motors[2] = robot.m2;
        motors[3] = robot.m3;

        motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[2].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        for (int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motors[i].setPower(0);
        }
    }
    public void mode(String mode) {
        switch (mode) {
            case "RESET":
                for (int i = 0; i < 4; i++) {
                    motors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
                break;
            case "RUN_TO_POSITION":
                for (int i = 0; i < 4; i++) {
                    motors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                break;
            case "RUN_WITHOUT_ENCODER":
                for (int i = 0; i < 4; i++) {
                    motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                break;
            case "RUN_USING_ENCODER":
                for (int i = 0; i < 4; i++) {
                    motors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                break;
        }
    }
    public void run(double p0, double p1, double p2, double p3) {
        motors[0].setPower(p0);
        motors[1].setPower(p1);
        motors[2].setPower(p2);
        motors[3].setPower(p3);
    }
    public void stopMotors() {
        for (int i = 0; i < 4; i++) {
            motors[i].setPower(0);
        }
    }
    public void resetEncoders() {
        mode("RESET");
    }
}