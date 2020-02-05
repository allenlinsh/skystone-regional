package org.firstinspires.ftc.teamcode.auton;

import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MainAutonomous extends Robot {
    /**
     * Declare auton variables
     */
    PIDController drive = new PIDController(0, 0, 0);
    PIDController rotate = new PIDController(0, 0, 0);

    @Override
    public void runOpMode() {
    }
}
