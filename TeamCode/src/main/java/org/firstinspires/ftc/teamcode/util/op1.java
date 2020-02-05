package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;




@TeleOp(name="teleOp", group="Linear Opmode")
//@Disabled
public class op1 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fr = null;
    private DcMotor br = null;
    private DcMotor fl = null;
    private DcMotor bl = null;
    private DcMotor ir = null;
    private DcMotor il = null;
    private DcMotor lift = null;
    private DcMotor tilt = null;
    private Servo grip = null;
    private Servo arm = null;
    private double frv;
    private double brv;
    private double flv;
    private double blv;


    @Override
    public void runOpMode() {

        double limit = 0.5;
        boolean intstop1 = true;
        boolean intstop2 = true;
        double gripper = 0;
        int gripPos = 1;

        boolean slowMode = false;

        int liftPos = 1;

        grip = hardwareMap.servo.get("grip");
        arm = hardwareMap.servo.get("arm");
        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        ir = hardwareMap.dcMotor.get("intake right");
        il = hardwareMap.dcMotor.get("intake left");
        tilt = hardwareMap.dcMotor.get("tilt");
        lift = hardwareMap.dcMotor.get("lift");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //((DcMotorEx) fr).setVelocity(0);
        //((DcMotorEx) fl).setVelocity(0);
        //((DcMotorEx) br).setVelocity(0);
        //((DcMotorEx) bl).setVelocity(0);

        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.REVERSE);


        int drive = 1;
        arm.setPosition(0);
        grip.setPosition(0.7);
        tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();


        while (opModeIsActive()) {


            telemetry.addData("Mode", drive);
            telemetry.addData("grip POS", gripper);

            telemetry.update();

            if(gamepad1.right_bumper){

                arm.setPosition(0.35);

            }


            if(gamepad1.left_bumper){

                arm.setPosition(0);

            }

            if (gamepad1.right_trigger > 0) {


                grip.setPosition(0);

                tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                tilt.setTargetPosition(0);
                tilt.setPower(0.5);
                while (tilt.isBusy() && opModeIsActive()) {

                    flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                    frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                    blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                    brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                    fr.setPower(frv * limit);
                    br.setPower(brv * limit);
                    fl.setPower(flv * limit);
                    bl.setPower(blv * limit);
                }
            }


            else if(gamepad1.dpad_left){


                tilt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                tilt.setPower(0.25);

            }
            else if(gamepad1.dpad_right) {

                tilt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                tilt.setPower(-0.5);


            }
             else {
                tilt.setPower(0);
            }


            if (gamepad1.y) {

                grip.setPosition(0.7);
                /*


                liftPos = liftPos +1;
                sleep(100);

                lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(liftPos == 1) {
                    lift.setTargetPosition(6000);
                }
                if(liftPos == 2) {
                    lift.setTargetPosition(6000);
                }if(liftPos == 3) {
                    lift.setTargetPosition(300);
                }if(liftPos == 4) {
                    lift.setTargetPosition(300);
                }if(liftPos == 5) {
                    lift.setTargetPosition(300);
                }if(liftPos == 6) {
                    lift.setTargetPosition(300);
                }if(liftPos == 7) {
                    lift.setTargetPosition(300);
                }
                lift.setPower(0.5);
                while (lift.isBusy() && opModeIsActive()) {

                    flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                    frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                    blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                    brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                    fr.setPower(frv);
                    br.setPower(brv);
                    fl.setPower(flv);
                    bl.setPower(blv);

                }

                tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                tilt.setTargetPosition(-2290);
                tilt.setPower(0.5);
                while (tilt.isBusy() && opModeIsActive()) {

                    flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                    frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                    blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                    brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                    fr.setPower(frv*0.5);
                    br.setPower(brv*0.5);
                    fl.setPower(flv*0.5);
                    bl.setPower(blv*0.5);
                    */

                }


              /*  if (liftPos == 1) {

                    lift.setTargetPosition(800);

                    while (lift.isBusy() && opModeIsActive()) {



                    flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                    frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                    blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                    brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                    fr.setPower(frv);
                    br.setPower(brv);
                    fl.setPower(flv);
                    bl.setPower(blv);

                }}

                if (liftPos == 2) {

                    lift.setTargetPosition(800);

                    while (lift.isBusy() && opModeIsActive()) {



                        flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                        frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                        blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                        brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                        fr.setPower(frv);
                        br.setPower(brv);
                        fl.setPower(flv);
                        bl.setPower(blv);

                    }}

                if (liftPos == 3) {

                    lift.setTargetPosition(800);

                    while (lift.isBusy() && opModeIsActive()) {



                        flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                        frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
                        blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
                        brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;

                        fr.setPower(frv);
                        br.setPower(brv);
                        fl.setPower(flv);
                        bl.setPower(blv);

                    }}


            }

               */


            if (gamepad1.x) {



                 grip.setPosition(0.05);


                sleep(300);

            }


            if (gamepad1.dpad_up) {

                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                lift.setPower(0.8);

            } else if (gamepad1.dpad_down) {

                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                lift.setPower(-0.8);
            } else {

                lift.setPower(0);
            }


            if (gamepad1.right_stick_button) {

                drive = drive + 1;


                if (drive > 2)

                {
                    drive = 1;

                }

            }


            if (drive == 1)

            {

                fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            }

            if (drive == 2)

            {

                fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            }

            flv = (gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
            frv = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
            blv = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
            brv = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;


            if(gamepad1.back) {
                tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                sleep(300);
            }



            if(gamepad1.start) {
                slowMode = false;
                sleep(300);
            }

            if (slowMode == true) {
                fr.setPower(frv);
                br.setPower(brv);
                fl.setPower(flv);
                bl.setPower(blv);
            }

            if (slowMode == false) {
                fr.setPower(frv*limit);
                br.setPower(brv*limit);
                fl.setPower(flv*limit);
                bl.setPower(blv*limit);
            }

            if (gamepad1.a)

            {
                il.setPower(-1);
                ir.setPower(1);


            } else {
                if (gamepad1.b) {

                    il.setPower(1);
                    ir.setPower(-1);
                } else {

                    il.setPower(0);
                    ir.setPower(0);

                }

            }

        }


    }
}

