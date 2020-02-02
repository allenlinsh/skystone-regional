package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    private LinearOpMode opmode;
    private HardwareMap map;

    /*
     * imu: imu
     * bl: back left
     * br: back right
     * fl: front left
     * fr: front right
     * il: intake left
     * ir: intake right
     * lift: lift
     * cap: capstone
     * arm: arm
     * grip: grip
     * tilt: tilt
     * lt: left top
     * lb: left bottom
     * rt: right top
     * rb: right bottom
     * arm: arm
     */
    public BNO055IMU imu;
    public DcMotor bl, br, fl, fr;
    public DcMotor il, ir;
    public DcMotor lift;
    public DcMotor cap;
    public Servo grip, tilt;
    public Servo lt, lb, rt, rb;
    public Servo arm;


    public Robot() {
        /*
         * Get the hardware map for each hardware
         */
        this.imu = map.get(BNO055IMU.class, "imu");
        this.bl = map.get(DcMotor.class, "back left");
        this.br = map.get(DcMotor.class, "back right");
        this.fl = map.get(DcMotor.class, "front left");
        this.fr = map.get(DcMotor.class, "front right");
        this.il = map.get(DcMotor.class, "intake left");
        this.ir = map.get(DcMotor.class, "intake right");
        this.lift = map.get(DcMotor.class, "lift");
        this.cap = map.get(DcMotor.class, "capstone");
        this.grip = map.get(Servo.class, "grip");
        this.tilt = map.get(Servo.class, "tilt");
        this.lt = map.get(Servo.class, "left top");
        this.lb = map.get(Servo.class, "left bottom");
        this.rt = map.get(Servo.class, "right top");
        this.rb = map.get(Servo.class, "right bottom");
        this.arm = map.get(Servo.class, "arm");
    }
}