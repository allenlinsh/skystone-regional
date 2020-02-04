package org.firstinspires.ftc.teamcode.auton;

import com.kinetix.robot.Drive;
import com.kinetix.robot.Intake;
import com.kinetix.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class MainAutonomous extends LinearOpMode {
    /**
     * Declare auton variables
     */


    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive();
        Intake intake = new Intake();
        PIDController move = new PIDController(0, 0, 0);
        PIDController rotate = new PIDController(0, 0, 0);
    }
}
