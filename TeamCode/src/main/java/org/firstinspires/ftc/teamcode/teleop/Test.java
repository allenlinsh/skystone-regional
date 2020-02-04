package org.firstinspires.ftc.teamcode.teleop;

import com.kinetix.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
