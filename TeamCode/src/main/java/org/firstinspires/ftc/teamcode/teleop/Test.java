package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.robot.Robot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test extends Robot {
    @Override
    public void runOpMode() {
        initHardwareMap();
        initSubsystemIMU();
        waitForStart();
        while (opModeIsActive()) {
            lock();
            sleep(1000);
            unlock();
            sleep(1000);
        }
    }
}
