package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    private LinearOpMode opmode;
    private HardwareMap map;

    public BNO055IMU imu;
    public DcMotor m0, m1, m2, m3;


    public Robot() {
        /*
         * Get the hardware map for each hardware
         */
        this.imu = map.get(BNO055IMU.class, "imu");
        this.m0 = map.get(DcMotor.class, "m0");
        this.m1 = map.get(DcMotor.class, "m1");
        this.m2 = map.get(DcMotor.class, "m2");
        this.m3 = map.get(DcMotor.class, "m3");

        /*
         * Initialize the imu and its parameters
         */
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        this.imu.initialize(parameters);
    }
}