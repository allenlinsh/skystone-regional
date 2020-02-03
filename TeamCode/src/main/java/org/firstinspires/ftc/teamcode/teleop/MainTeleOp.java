package org.firstinspires.ftc.teamcode.teleop;

import com.kinetix.robot.Drive;
import com.kinetix.robot.Intake;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MainTeleOp extends LinearOpMode {
    /**
     * Declare teleop variables
     */
    private Gamepad driver = gamepad1;
    private Gamepad builder = gamepad2;
    private boolean isDriverStartPressed, isBuilderStartPressed;

    private double[] drivePower = new double[4];
    private double maxDrivePower = 1.0;
    private double minDrivePower = 0.25;
    private double slowDrivePower = 0.25 * maxDrivePower;
    private double x, y, rotation;

    private double[] intakePower = new double[2];
    private double maxIntakePower = 1.0;
    private double in, out;

    @Override
    public void runOpMode() {
        Drive drive = new Drive();
        Intake intake = new Intake();

        /**
         * Initialize
         */
        drive.initialize();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        /**
         * Run once
         */


        /**
         * Run loop
         */
        while(opModeIsActive()) {
            ////////////////////////////////////   gamepad 1   ////////////////////////////////////
            isDriverStartPressed = driver.start ? true : false;
            if (driver.left_stick_x + driver.left_stick_y == 0) {
                x = slowDrivePower * (driver.dpad_left ? 1 : driver.dpad_right ? -1 : 0);
                y = slowDrivePower * (driver.dpad_up ? 1 : driver.dpad_down ? -1 : 0);
            } else {
                x = -driver.left_stick_x;
                y = -driver.left_stick_y;
            }
            if (driver.right_stick_x == 0) {
                rotation = slowDrivePower * ((!isDriverStartPressed && driver.b) ? 1 : (!isDriverStartPressed && driver.x) ? -1 : 0);
            } else {
                rotation = driver.right_stick_x;
            }
            in = driver.right_trigger;
            out = -driver.left_trigger;

            ////////////////////////////////////   gamepad 2   ////////////////////////////////////
            isBuilderStartPressed = (builder.start) ? true : false;

            /////////////////////////////////   drive subsystem   /////////////////////////////////
            drivePower[0] = x + y + rotation; // power for left back motor
            drivePower[1] = -x + y - rotation; // power for right back motor
            drivePower[2] = -x + y + rotation; // power for left front motor
            drivePower[3] = x + y - rotation; // power for right front motor

            for(int i = 0; i < 4; i++) {
                drivePower[i] = Math.signum(drivePower[i]) * Math.pow(drivePower[i], 2); // square the output for fine movement control
                drive.motors[i].setPower(Range.clip(drivePower[i], -maxDrivePower, maxDrivePower));
            }

            /////////////////////////////////   intake subsystem   /////////////////////////////////
            intakePower[0] = in + out; // power for left intake motor
            intakePower[1] = -(in + out); // power for right intake motor

            for(int i = 0; i < 2; i++) {
                intake.motors[i].setPower(Range.clip(intakePower[i], -maxIntakePower, maxIntakePower));
            }

            //////////////////////////////////   lift subsystem   //////////////////////////////////

            //////////////////////////////////   arm subsystem   //////////////////////////////////

            //////////////////////////////////   hook subsystem   //////////////////////////////////

            ////////////////////////////////   capstone subsystem   ////////////////////////////////

            ////////////////////////////////////   telemetry   ////////////////////////////////////
        }

        /**
         * Stop
         */
        drive.stopMotors();
        intake.stopMotors();
    }
}