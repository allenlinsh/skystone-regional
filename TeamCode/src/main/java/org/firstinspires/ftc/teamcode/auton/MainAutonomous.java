package org.firstinspires.ftc.teamcode.auton;

import org.firstinspires.ftc.teamcode.robot.RobotSystem;
import org.firstinspires.ftc.teamcode.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class MainAutonomous extends RobotSystem {
    /**
     * Declare auton variables
     */
    private PIDController drive = new PIDController(0, 0, 0);
    private PIDController rotate = new PIDController(0, 0, 0);

    private String alliances[] = {"Blue Alliance", "Red Alliance"};
    private String modes[] = {"Park (Wall)", "Park (Bridge)", "Foundation (Wall)", "Foundation (Bridge)",
            "Skystone{1}", "Skystone{2}", "Skystone{3}", "Skystone{4}", "Full{1}", "Full{2}"};

    private int allianceIndex = 0;
    private int modeIndex = 0;
    private boolean was_dpad_up = false;
    private boolean was_dpad_down = false;
    private boolean allianceSelected = false;
    private boolean modeSelected = false;
    private boolean done = false;

    @Override
    public void runOpMode() {
        /**
         * Init
         */
        initHardwareMap();
        initSubsystem();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /**
         * Init Loop
         */
        while (!isStopRequested()) {
            if (gamepad1.dpad_down && !was_dpad_down) {
                if (!allianceSelected) {
                    allianceIndex = (allianceIndex + 1) % alliances.length;
                } else if (allianceSelected && !modeSelected) {
                    modeIndex = (modeIndex + 1) % modes.length;
                }
            }
            if (gamepad1.dpad_up && !was_dpad_up) {
                if (!allianceSelected) {
                    allianceIndex = (allianceIndex + alliances.length - 1) % alliances.length;
                } else if (allianceSelected && !modeSelected) {
                    modeIndex = (modeIndex + modes.length - 1) % modes.length;
                }
            }
            if (gamepad1.right_bumper && !allianceSelected) {
                allianceSelected = true;
            } else if (gamepad1.right_bumper && allianceSelected && !modeSelected) {
                modeSelected = true;
            } else if (gamepad1.right_bumper && allianceSelected && modeSelected && !done) {
                done = true;
            }
            if (gamepad1.left_bumper && allianceSelected) {
                allianceSelected = false;
            } else if (gamepad1.left_bumper && allianceSelected && modeSelected) {
                modeSelected = false;
            }

            // Remember the last state of the dpad to detect changes
            was_dpad_up = gamepad1.dpad_up;
            was_dpad_down = gamepad1.dpad_down;

            // Display the current alliance choice and mode choice and the selected status
            if (!done) {
                telemetry.addData("", "Use DPAD up/down to choose.");
                telemetry.addData("", "Press Right Bumper to select.");
                telemetry.addData("", "Press Left Bumper to undo.");
                telemetry.addData("", "");
                if (!allianceSelected) {
                    telemetry.addData("Alliance", alliances[allianceIndex]);
                } else if (allianceSelected && !modeSelected) {
                    telemetry.addData("Autonomous Mode >", modes[modeIndex]);
                }
                telemetry.addData("", "");
                telemetry.addData("Status", (allianceSelected && modeSelected)
                        ? String.format("%s: %s", alliances[allianceIndex], modes[modeIndex])
                        : (allianceSelected && !modeSelected)
                        ? String.format("%s: {Autonomous Mode}", alliances[allianceIndex])
                        : "{Alliance}: {Autonomous Mode}");
            } else {
                break;
            }
            telemetry.update();
        }

        /**
         * Run loop
         */
        waitForStart();
        if (opModeIsActive()) {
            // alliance: blue
            if (allianceIndex == 0) {
                switch (modeIndex) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                }
            } else {
                switch (modeIndex) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                }
            }
        }

        /**
         * Stop
         */
        stopAllMotors();
    }
}
