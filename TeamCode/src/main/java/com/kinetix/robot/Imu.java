package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Imu {
    /**
     * Declare imu variables
     */
    private Robot robot = new Robot();
    private BNO055IMU imu;
    private double globalHeading;
    private double lastHeading;

    /**
     * Constructor for imu
     */
    public Imu() {
        this.imu = robot.imu;
    }

    /**
     * Get the angle on the x-axis
     * @return angle on the x-axis
     */
    public double getXAngle() {
        return imu.getAngularOrientation().thirdAngle;
    }

    /**
     * Get the angle on the y-axis
     * @return angle on the y-axis
     */
    public double getYAngle() {
        return imu.getAngularOrientation().secondAngle;
    }

    /**
     * Get the angle on the z-axis
     * @return angle on the z-axis
     */
    public double getZAngle() {
        return imu.getAngularOrientation().firstAngle;
    }

    /**
     * Get the last stored heading
     * @return last heading
     */
    public double getLastAngle() {
        return lastHeading;
    }

    /**
     * Reset the global heading to the current heading
     */
    public void resetHeading() {
        lastHeading = getZAngle();
        globalHeading = 0;
    }

    /**
     * Get the adjusted heading
     * @return global heading
     */
    public double getHeading() {
        double deltaAngle = getZAngle() - getLastAngle();

        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        globalHeading += deltaAngle;
        lastHeading = getZAngle();
        return globalHeading;
    }

    /**
     * Initializes imu parameters
     */
    public void initialize() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
    }
}