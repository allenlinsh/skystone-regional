package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.robot.RobotSystem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test extends RobotSystem {
    @Override
    public void runOpMode() {
        initHardwareMap();
        initSubsystemIMU();
        waitForStart();
        while (opModeIsActive()) {

        }
    }
}
