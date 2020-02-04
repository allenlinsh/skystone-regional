package com.kinetix.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift extends LinearOpMode {
    /**
     * Declare lift variables
     */
    private Robot robot = new Robot(hardwareMap);
    private DcMotor motor;
    private Servo servos[] = new Servo[4];

    /**
     * Constructor for lift
     */
    public Lift() {
        this.motor = robot.lift;
        this.servos[0] = robot.tl;
        this.servos[1] = robot.bl;
        this.servos[2] = robot.tr;
        this.servos[3] = robot.br;
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}