package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.util.MathUtils;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class MainTeleOp extends LinearOpMode {
    /**
     * Declare teleop variables
     */
    private Robot robot = new Robot();

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

    private boolean hookOn = false;

    @Override
    public void runOpMode() {
        /**
         * Init
         */
        robot.initHardwareMap();
        robot.initSubsystem();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        /**
         * Init Loop
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

            if (driver.right_bumper) {
                hookOn = true;
            } else if (driver. left_bumper) {
                hookOn = false;
            }

            ////////////////////////////////////   gamepad 2   ////////////////////////////////////
            isBuilderStartPressed = (builder.start) ? true : false;

            /////////////////////////////////   drive subsystem   /////////////////////////////////
            drivePower[0] = x + y + rotation; // power for left back motor
            drivePower[1] = -x + y - rotation; // power for right back motor
            drivePower[2] = -x + y + rotation; // power for left front motor
            drivePower[3] = x + y - rotation; // power for right front motor

            for(int i = 0; i < 4; i++) {
                drivePower[i] = Math.signum(drivePower[i]) * Math.pow(drivePower[i], 2); // square the output for fine movement control
                robot.getDriveMotor(i).setPower(MathUtils.clip(drivePower[i], minDrivePower, maxDrivePower));
            }

            /////////////////////////////////   intake subsystem   /////////////////////////////////
            intakePower[0] = in + out; // power for left intake motor
            intakePower[1] = -(in + out); // power for right intake motor

            for(int i = 0; i < 2; i++) {
                robot.getIntakeMotor(i).setPower(Range.clip(intakePower[i], -maxIntakePower, maxIntakePower));
            }

            //////////////////////////////////   lift subsystem   //////////////////////////////////

            //////////////////////////////////   arm subsystem   //////////////////////////////////

            //////////////////////////////////   hook subsystem   //////////////////////////////////
            if (hookOn) {
                robot.lock();
            } else {
                robot.unlock();
            }

            ////////////////////////////////   capstone subsystem   ////////////////////////////////

            ////////////////////////////////////   telemetry   ////////////////////////////////////
        }

        /**
         * Stop
         */
        robot.stopDriveMotors();
        robot.stopIntakeMotors();
    }
}