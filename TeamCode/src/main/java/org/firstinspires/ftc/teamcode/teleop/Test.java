package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.robot.RobotSystem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test extends RobotSystem {
    @Override
    public void runOpMode() {
        initHardwareMap();
        initSubsystemOdometry();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("left encoder", getLeftTicks());
            telemetry.addData("right encoder", getRightTicks());
            telemetry.update();
        }
    }
}
