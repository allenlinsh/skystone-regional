package com.kinetix.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot extends LinearOpMode{
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

    /**
     * Constructor for robot hardware
     */
    public Robot() {
        /*
         * Get the hardware map for each hardware
         */
        this.imu = hardwareMap.get(BNO055IMU.class, "imu");
        this.lb = hardwareMap.get(DcMotor.class, "left back");
        this.rb = hardwareMap.get(DcMotor.class, "right back");
        this.lf = hardwareMap.get(DcMotor.class, "left front");
        this.rf = hardwareMap.get(DcMotor.class, "right front");
        this.il = hardwareMap.get(DcMotor.class, "intake left");
        this.ir = hardwareMap.get(DcMotor.class, "intake right");
        this.lift = hardwareMap.get(DcMotor.class, "lift");
        this.cap = hardwareMap.get(DcMotor.class, "capstone");
        this.arm = hardwareMap.get(Servo.class, "arm");
        this.grip = hardwareMap.get(Servo.class, "grip");
        //this.tilt = hardwareMap.get(Servo.class, "tilt");
        this.tl = hardwareMap.get(Servo.class, "top left");
        this.bl = hardwareMap.get(Servo.class, "bottom left");
        this.tr = hardwareMap.get(Servo.class, "top right");
        this.br = hardwareMap.get(Servo.class, "bottom right");
        this.hl = hardwareMap.get(Servo.class, "hook left");
        this.hr = hardwareMap.get(Servo.class, "hook right");
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}