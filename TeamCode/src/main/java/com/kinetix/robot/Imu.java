package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class Imu {
    /*
     * Declare imu variables
     */
    private Robot robot = new Robot();

    public Imu() {
        /*
         * Initialize the imu and its parameters
         */
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        robot.imu.initialize(parameters);
    }
}