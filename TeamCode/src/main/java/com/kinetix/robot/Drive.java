package com.kinetix.robot;

import com.kinetix.util.MathUtils;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Drive {
    /**
     * Declare drive variables
     */
    private Robot robot = new Robot();
    private Imu imu = new Imu();
    private final double MM_PER_INCH = 25.4;
    private final double WHEEL_DIAMETER = MathUtils.round(100/MM_PER_INCH, 2); // specific for GoBilda Mecanum wheels
    private final double TICKS_PER_REV = 723.24; // specific for GoBilda 26:1 motors
    private final double IN_PER_REV = Math.PI * WHEEL_DIAMETER / TICKS_PER_REV; // ticks = inches / IN_PER_REV;
    private final double IN_PER_BLOCK = 23.625;
    public DcMotor motors[] = new DcMotor[4];

    /**
     * Constructor for mecanum drive with imu
     */
    public Drive() {
        this.motors[0] = robot.lb;
        this.motors[1] = robot.rb;
        this.motors[2] = robot.lf;
        this.motors[3] = robot.rf;
    }

    /**
     * Drive to an {x,y} position based on the global coordinates and turn to {heading} angle
     * with motor powers set to {speed}
     * @param x desired global x position
     * @param y desired global y position
     * @param heading desired heading
     * @param speed desired speed
     */
    public void goToPosition(double x, double y, double heading, double speed) {

    }

    /**
     * Set mode for all drive motors
     * @param mode desired mode for all motors
     */
    public void setModeAll(String mode) {
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

    /**
     * Set power for all drive motors
     * @param p0 power for left back motor
     * @param p1 power for right back motor
     * @param p2 power for left front motor
     * @param p3 power for right front motor
     */
    public void setPowerAll(double p0, double p1, double p2, double p3) {
        motors[0].setPower(p0);
        motors[1].setPower(p1);
        motors[2].setPower(p2);
        motors[3].setPower(p3);
    }

    /**
     * Set target position for all drive motor encoders
     * @param t0 target position for left back motor encoder
     * @param t1 target position for right back motor encoder
     * @param t2 target position for left front motor encoder
     * @param t3 target position for right front motor encoder
     */
    public void setTargetPositionAll(int t0, int t1, int t2, int t3) {
        motors[0].setTargetPosition(t0);
        motors[1].setTargetPosition(t1);
        motors[2].setTargetPosition(t2);
        motors[3].setTargetPosition(t3);
    }

    /**
     * Set the power for all drive motors to 0
     */
    public void stopMotors() {
        for (int i = 0; i < 4; i++) {
            motors[i].setPower(0);
        }
    }

    /**
     * Convert block units to inch units
     * @param blocks desired block units to be converted
     * @return inch units
     */
    public double blockToInch(double blocks) {
        return blocks*IN_PER_BLOCK;
    }

    /**
     * Convert inch units to tick units
     * @param inches desired inch units to be converted
     * @return tick units
     */
    public double inchToTick(double inches) {
        return inches/IN_PER_REV;
    }

    /**
     * Initialize drive motors
     */
    public void init() {
        motors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[2].setDirection(DcMotorSimple.Direction.FORWARD);
        motors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        for (int i = 0; i < 4; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motors[i].setPower(0);
        }
    }
}