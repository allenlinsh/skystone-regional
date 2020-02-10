package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.robot.RobotSystem;
import org.firstinspires.ftc.teamcode.util.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MainTeleOp extends RobotSystem {
    /**
     * Declare teleop variables
     */
    // gamepad1 = driver
    // gamepad2 = builder

    private double[] drivePower = new double[4];
    private double minDrivePower = 0;
    private double maxDrivePower = 1.0;
    private double slowDrivePower = 0.25 * maxDrivePower;
    private double x, y, rotation;

    private double[] intakePower = new double[2];
    private double minIntakePower = 0.25;
    private double maxIntakePower = 1.0;
    private double in, out;

    private double liftZ;
    private boolean grabOn = false;

    private double armY;
    private boolean gripOn = false;

    private double capZ;

    private boolean hookOn = false;

    private String mode = "DRIVER"; // OR "BUILDER" OR "BOTH"

    @Override
    public void runOpMode() {
        /**
         * Init
         */
        initHardwareMap();
        initSubsystem();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /**
         * Init Loop
         */

        /**
         * Run loop
         */
        waitForStart();
        while(opModeIsActive()) {
            ////////////////////////////////////   gamepad 1   ////////////////////////////////////
            if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0) {
                x = slowDrivePower * (gamepad1.dpad_left ? 1 : gamepad1.dpad_right ? -1 : 0);
                y = slowDrivePower * (gamepad1.dpad_up ? 1 : gamepad1.dpad_down ? -1 : 0);
            } else {
                x = Math.signum(-gamepad1.left_stick_x) * Math.pow(-gamepad1.left_stick_x, 2);
                y = Math.signum(-gamepad1.left_stick_y) * Math.pow(-gamepad1.left_stick_y, 2);
            }

            if (gamepad1.right_stick_x == 0) {
                rotation = slowDrivePower * ((!gamepad1.start && gamepad1.b) ? 1 : (!gamepad1.start && gamepad1.x) ? -1 : 0);
            } else {
                rotation = gamepad1.right_stick_x;
            }

            in = gamepad1.right_trigger;
            out = -gamepad1.left_trigger;

            if (gamepad1.right_bumper) {
                hookOn = true;
            } else if (gamepad1. left_bumper) {
                hookOn = false;
            }

            ////////////////////////////////////   gamepad 2   ////////////////////////////////////
            liftZ = -gamepad2.left_stick_y;
            if (gamepad2.right_bumper) {
                grabOn = true;
            } else if (gamepad2.left_bumper) {
                grabOn = false;
            }

            armY = (!gamepad2.start && gamepad2.a) ? 1 : (!gamepad2.start && gamepad2.y) ? -1 : 0;
            if (gamepad2.dpad_down) {
                gripOn = true;
            } else if (gamepad2.dpad_up) {
                gripOn = false;
            }

            capZ = (!gamepad2.start && gamepad2.b) ? 1 : (!gamepad2.start && gamepad2.x) ? -1 : 0;

            /////////////////////////////////   drive subsystem   /////////////////////////////////
            drivePower[0] = x + y + rotation; // power for left back motor
            drivePower[1] = -x + y - rotation; // power for right back motor
            drivePower[2] = -x + y + rotation; // power for left front motor
            drivePower[3] = x + y - rotation; // power for right front motor

            // square the output for fine movement control
            // and clip the output between minDrivePower and maxDrivePower
            for (int i = 0; i < drivePower.length; i++) {
                drivePower[i] = MathUtils.clip(drivePower[i], minDrivePower, maxDrivePower);
            }

            setDrivePowerAll(drivePower[0], drivePower[1], drivePower[2], drivePower[3]);

            /////////////////////////////////   intake subsystem   /////////////////////////////////
            intakePower[0] = in + out; // power for left intake motor
            intakePower[1] = -(in + out); // power for right intake motor

            // clip the output between minDrivePower and maxDrivePower
            for (int i = 0; i < intakePower.length; i++) {
                intakePower[i] = MathUtils.clip(intakePower[i], minIntakePower, maxIntakePower);
            }

            setIntakePowerAll(intakePower[0], intakePower[1]);

            //////////////////////////////////   lift subsystem   //////////////////////////////////
            setLiftPower(liftZ);
            if (grabOn) {
                close();
            } else {
                open();
            }

            //////////////////////////////////   arm subsystem   //////////////////////////////////
            setArmPower(armY);

            if (gripOn) {
                hold();
            } else {
                release();
            }

            //////////////////////////////////   hook subsystem   //////////////////////////////////
            if (hookOn) {
                lock();
            } else {
                unlock();
            }

            ////////////////////////////////   capstone subsystem   ////////////////////////////////
            setCapPower(capZ);

            ////////////////////////////////////   telemetry   ////////////////////////////////////
            if (mode == "DRIVER") {
                telemetry.addData("Left Back", drivePower[0]);
                telemetry.addData("Right Back", drivePower[1]);
                telemetry.addData("Left Front", drivePower[2]);
                telemetry.addData("Right Front", drivePower[3]);
                telemetry.addLine("");
                telemetry.addData("Intake Left", intakePower[0]);
                telemetry.addData("Intake Right", intakePower[1]);
            } else if (mode == "BUILDER") {
                telemetry.addData("Lift", liftZ);
                telemetry.addData("Grabber", grabOn ? "On" : "Off");
                telemetry.addLine("");
                telemetry.addData("Arm", armY);
                telemetry.addData("Gripper", gripOn ? "On" : "Off");
            } else if (mode == "BOTH") {
                telemetry.addData("Left Back", drivePower[0]);
                telemetry.addData("Right Back", drivePower[1]);
                telemetry.addData("Left Front", drivePower[2]);
                telemetry.addData("Right Front", drivePower[3]);
                telemetry.addLine("");
                telemetry.addData("Intake Left", intakePower[0]);
                telemetry.addData("Intake Right", intakePower[1]);
                telemetry.addLine("");
                telemetry.addData("Lift", liftZ);
                telemetry.addData("Grabber", grabOn ? "On" : "Off");
                telemetry.addLine("");
                telemetry.addData("Arm", armY);
                telemetry.addData("Gripper", gripOn ? "On" : "Off");
            }
            telemetry.update();
        }

        /**
         * Stop
         */
        stopAllMotors();
    }
}