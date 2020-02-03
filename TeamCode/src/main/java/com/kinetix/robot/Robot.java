package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    /**
     * Declare robot variables
     * imu: imu
     * lb: left back
     * rb: right back
     * lf: left front
     * rf: right front
     * il: intake left
     * ir: intake right
     * lift: lift
     * cap: capstone
     * arm: arm
     * grip: grip
     * tilt: tilt
     * tl: top left
     * bl: bottom left
     * tr: top right
     * br: bottom right
     * arm: arm
     * hl: hook left
     * hr: hook right
     */
    public BNO055IMU imu;
    public DcMotor lb, rb, lf, rf;
    public DcMotor il, ir;
    public DcMotor lift;
    public DcMotor cap;
    public Servo grip, tilt;
    public Servo tl, bl, tr, br;
    public Servo arm;
    public Servo hl, hr;

    private LinearOpMode opmode;
    private HardwareMap map;

    /**
     * Constructor for robot hardware
     */
    public Robot() {
        /*
         * Get the hardware map for each hardware
         */
        this.imu = map.get(BNO055IMU.class, "imu");
        this.lb = map.get(DcMotor.class, "left back");
        this.rb = map.get(DcMotor.class, "right back");
        this.lf = map.get(DcMotor.class, "left front");
        this.rf = map.get(DcMotor.class, "right front");
        this.il = map.get(DcMotor.class, "intake left");
        this.ir = map.get(DcMotor.class, "intake right");
        this.lift = map.get(DcMotor.class, "lift");
        this.cap = map.get(DcMotor.class, "capstone");
        this.grip = map.get(Servo.class, "grip");
        //this.tilt = map.get(Servo.class, "tilt");
        this.tl = map.get(Servo.class, "top left");
        this.bl = map.get(Servo.class, "bottom left");
        this.tr = map.get(Servo.class, "top right");
        this.br = map.get(Servo.class, "bottom right");
        this.arm = map.get(Servo.class, "arm");
        this.hl = map.get(Servo.class, "hook left");
        this.hr = map.get(Servo.class, "hook right");
    }
}