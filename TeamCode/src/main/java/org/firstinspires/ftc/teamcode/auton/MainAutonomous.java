package org.firstinspires.ftc.teamcode.auton;

import com.kinetix.robot.Robot;
import com.kinetix.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class MainAutonomous extends LinearOpMode {
    /**
     * Declare auton variables
     */

    @Override
    public void runOpMode() {
        PIDController move = new PIDController(0, 0, 0);
        PIDController rotate = new PIDController(0, 0, 0);
    }
}
