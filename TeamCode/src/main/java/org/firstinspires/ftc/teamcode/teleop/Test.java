package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test extends Robot {
    @Override
    public void runOpMode() {
        initHardwareMap();
        initDrive();
        initIntake();
        waitForStart();
    }
}
