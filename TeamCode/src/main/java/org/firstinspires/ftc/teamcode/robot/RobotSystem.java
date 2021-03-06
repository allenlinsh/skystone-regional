package org.firstinspires.ftc.teamcode.robot;

import org.firstinspires.ftc.teamcode.util.MathUtils;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotSystem extends LinearOpMode{
    /**
     * Declare robot variables
     * imu: imu
     * lb: left back
     * rb: right back
     * lf: left front
     * rf: right front
     * il: intake left
     * ir: intake right
     * lift: lift
     * arm: arm
     * grip: grip
     * tl: top left
     * bl: bottom left
     * tr: top right
     * br: bottom right
     * hl: hook left
     * hr: hook right
     * cap: capstone
     */
    private BNO055IMU imu;
    private DcMotor lb, rb, lf, rf;
    private DcMotor il, ir;
    private DcMotor lift;
    private DcMotor arm;
    private Servo grip;
    private Servo tl, bl, tr, br;
    private Servo hl, hr;
    private CRServo cap;

    /**
     * Declare drive variables
     */
    private final double MM_PER_INCH = 25.4;
    private final double WHEEL_DIAMETER = MathUtils.round(100/MM_PER_INCH, 2); // specific for GoBilda Mecanum wheels
    private final double TICKS_PER_REV = 723.24; // specific for GoBilda 26:1 motors
    private final double IN_PER_TICK = Math.PI * WHEEL_DIAMETER / TICKS_PER_REV; // ticks = inches / IN_PER_TICK;
    private final double IN_PER_BLOCK = 23.625;
    private DcMotor driveMotors[] = new DcMotor[4];

    /**
     * Declare intake variables
     */
    private DcMotor intakeMotors[] = new DcMotor[2];

    /**
     * Declare lift variables
     */
    private Servo grabServos[] = new Servo[4];

    /**
     * Declare arm variables
     */

    /**
     * Declare hook variables
     */
    private Servo hookServos[] = new Servo[2];
    private int servoPause = 300; // time to complete servo movement (in milliseconds)

    /**
     * Declare imu variables
     */
    private double globalHeading;
    private double lastHeading;

    /**
     * Declare odometry variables
     */
    private double wheelDistanceApart = 10; // specific for our odometry setup
    private double globalX = 0;
    private double globalY = 0;
    private double theta = 0;
    private DcMotor encoders[] = new DcMotor[3];

    private int leftEncoderPos = 0;
    private int centerEncoderPos = 0;
    private int rightEncoderPos = 0;
    private double deltaLeft = 0;
    private double deltaRight = 0;
    private double deltaCenter = 0;

    ////////////////////////////////////////   robot    ////////////////////////////////////////
    /*
     * Get the hardware map for each hardware
     */
    public void initHardwareMap() {

        imu = (BNO055IMU)hardwareMap.get("imu");
        lb = (DcMotor)hardwareMap.get("left back");
        rb = (DcMotor)hardwareMap.get("right back");
        lf = (DcMotor)hardwareMap.get("left front");
        rf = (DcMotor)hardwareMap.get("right front");
        il = (DcMotor)hardwareMap.get("intake left");
        ir = (DcMotor)hardwareMap.get("intake right");
        lift = (DcMotor)hardwareMap.get("lift");
        arm = (DcMotor)hardwareMap.get("arm");
        cap = (CRServo)hardwareMap.get("capstone");
        grip = (Servo)hardwareMap.get("grip");
        tl = (Servo)hardwareMap.get("top left");
        bl = (Servo)hardwareMap.get("bottom left");
        tr = (Servo)hardwareMap.get("top right");
        br = (Servo)hardwareMap.get("bottom right");
        hl = (Servo)hardwareMap.get("hook left");
        hr = (Servo)hardwareMap.get("hook right");
    }

    /**
     * Initialize each hardware subsystem
     */
    public void initSubsystem() {
        initDrive();
        initIntake();
        initLift();
        initArm();
        initHook();
        initCapstone();
    }

    /**
     * Initialize each hardware subsystem and the IMU
     */
    public void initSubsystemIMU() {
        initSubsystem();
        initIMU();
    }

    /**
     * Initialize each hardware subsystem and the odometry
     */
    public void initSubsystemOdometry() {
        initSubsystem();
        initOdometry();
    }

    /**
     * Initialize drive motors
     */
    public void initDrive() {
        driveMotors[0] = lb;
        driveMotors[1] = rb;
        driveMotors[2] = lf;
        driveMotors[3] = rf;

        driveMotors[0].setDirection(DcMotorSimple.Direction.FORWARD);
        driveMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[2].setDirection(DcMotorSimple.Direction.FORWARD);
        driveMotors[3].setDirection(DcMotorSimple.Direction.REVERSE);

        for (int i = 0; i < 4; i++) {
            driveMotors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            driveMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            driveMotors[i].setPower(0);
        }
    }

    /**
     * Initialize intake motors
     */
    public void initIntake() {
        intakeMotors[0] = il;
        intakeMotors[1] = ir;

        intakeMotors[1].setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        for (int i = 0; i < 2; i++) {
            intakeMotors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            intakeMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intakeMotors[i].setPower(0);
        }
    }

    /**
     * Initialize lift motors
     */
    public void initLift() {
        grabServos[0] = tl;
        grabServos[1] = bl;
        grabServos[2] = tr;
        grabServos[3] = br;

        open();

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        lift.setPower(0);
    }

    /**
     * Initialize arm continuous servos
     */
    public void initArm() {
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        arm.setPower(0);
    }

    /**
     * Initialize hook servos
     */
    public void initHook() {
        hookServos[0] = hl;
        hookServos[1] = hr;

        unlock();
    }

    /**
     * Initialize capstone motors
     */
    public void initCapstone() {
        cap.setPower(0);
    }

    /**
     * Initialize imu parameters
     */
    public void initIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    /**
     * Initialize odometry encoders
     * Left encoder uses motor port 0
     * Right encoder uses motor port 1
     * Center encoder uses motor port 2
     */
    public void initOdometry() {
        encoders[0] = lb;
        encoders[1] = rb;
        encoders[2] = lf;
    }

    /**
     * Set power for all motors to zero
     */
    public void stopAllMotors() {
        stopDriveMotors();
        stopIntakeMotors();
    }

    /**
     * Wait for the servo to complete its action
     */
    public void pauseServo() {
        sleep(servoPause);
    }

    ////////////////////////////////////   drive subsystem   ////////////////////////////////////

    public void updatePosition() {
        deltaLeft = getLeftTicks() * IN_PER_TICK;
        deltaRight = getRightTicks() * IN_PER_TICK;
        deltaCenter = getCenterTicks() * IN_PER_TICK;

    }

    /**
     * Drive to an {x,y} position based on the global coordinates and turn to absolute {heading}
     * with motor powers set to {speed}
     * @param x desired global x position
     * @param y desired global y position
     * @param heading desired heading
     * @param speed desired speed
     */
    public void goToPosition(double x, double y, double heading, double speed) {}

    /**
     * Drive to an {x,y} position based on the relative coordinates with motor powers set to {speed}
     * @param x desired relative x position (+ for right, - for left)
     * @param y desired relative y position (+ for forward, - for backward)
     * @param turnHeading true if robot calculates heading then move towards target position
     *                    false if robot moves Y distance then strafes X distance
     * @param speed desired speed
     */
    public void moveToPosition(double x, double y, boolean turnHeading, double speed) {
        double deltaX, deltaY, deltaHeading;
        double p = speed;
        double headingSign = 1;
        double travelDistance = 0;
        double encoderDistance = 0;
        boolean strafing = false;

        if (x == 0) {
            deltaHeading = 0;
            travelDistance = y;
        } else if (y == 0) {
            strafing = true;
            deltaHeading = 0;
            travelDistance = x;
        } else {
            if (y > 0) {
                deltaHeading = Math.abs(Math.atan(x/y));
                if (x > 0) {
                    headingSign = 1;
                } else if (x < 0){
                    headingSign = -1;
                }
            } else if (y < 0) {
                deltaHeading = Math.PI / 2.0 + Math.abs(Math.atan(y / x));
                if (x > 0) {
                    headingSign = 1;
                } else if (x < 0) {
                    headingSign = -1;
                }
            }
        }

        resetAllTicks();
        setDriveModeAll("RUN_TO_POSITION");
        setEncoderTargetPositionY();
        setDriveModeAll("RUN_WITHOUT_ENCODER");

        do {
            setDrivePowerAll(speed, speed, speed, speed);
        } while ();
    }

    /**
     * Set mode for all drive motors
     * @param mode desired mode for all motors
     */
    public void setDriveModeAll(String mode) {
        switch (mode) {
            case "RESET":
                for (int i = 0; i < 4; i++) {
                    driveMotors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                }
                break;
            case "RUN_TO_POSITION":
                for (int i = 0; i < 4; i++) {
                    driveMotors[i].setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                break;
            case "RUN_WITHOUT_ENCODER":
                for (int i = 0; i < 4; i++) {
                    driveMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                }
                break;
            case "RUN_USING_ENCODER":
                for (int i = 0; i < 4; i++) {
                    driveMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
                break;
        }
    }

    /**
     * Set power for all drive motors
     * @param p0 power for left back motor
     * @param p1 power for right back motor
     * @param p2 power for left front motor
     * @param p3 power for right front motor
     */
    public void setDrivePowerAll(double p0, double p1, double p2, double p3) {
        driveMotors[0].setPower(p0);
        driveMotors[1].setPower(p1);
        driveMotors[2].setPower(p2);
        driveMotors[3].setPower(p3);
    }

    /**
     * Set target position for all drive motor encoders
     * @param t0 target position for left back motor encoder
     * @param t1 target position for right back motor encoder
     * @param t2 target position for left front motor encoder
     * @param t3 target position for right front motor encoder
     */
    public void setDriveTargetPositionAll(int t0, int t1, int t2, int t3) {
        driveMotors[0].setTargetPosition(t0);
        driveMotors[1].setTargetPosition(t1);
        driveMotors[2].setTargetPosition(t2);
        driveMotors[3].setTargetPosition(t3);
    }

    /**
     * Set target position for encoders in Y direction
     * @param t0 target position for left encoder
     * @param t1 target position for right encoder
     */
    public void setEncoderTargetPositionY(int t0, int t1) {
        encoders[0].setTargetPosition(t0);
        encoders[1].setTargetPosition(t1);
    }

    /**
     * Set target position for encoder in X direction
     * @param t2 target position for center encoder
     */
    public void setEncoderTargetPositionX(int t2) {
        encoders[2].setTargetPosition(t2);
    }

    /**
     * Set the power for all drive motors to 0
     */
    public void stopDriveMotors() {
        for (int i = 0; i < 4; i++) {
            driveMotors[i].setPower(0);
        }
    }

    /**
     * Convert block units to inch units
     * @param blocks desired block units to be converted
     * @return inch units
     */
    public double blockToInch(double blocks) {
        return blocks*IN_PER_BLOCK;
    }

    /**
     * Convert inch units to tick units
     * @param inches desired inch units to be converted
     * @return tick units
     */
    public double inchToTick(double inches) {
        return inches/IN_PER_TICK;
    }

    public void move

    /**
     * Get the drivemotor object
     * @param index index of the desired motor
     * @return drive motor object
     */
    public DcMotor getDriveMotor(int index) {
        return driveMotors[index];
    }

    /**
     * Reset ticks for all odometry encoder
     */
    public void resetAllTicks() {
        resetLeftTicks();
        resetCenterTicks();
        resetRightTicks();
    }

    /**
     * Reset ticks for left encoder
     */
    public void resetLeftTicks() {
        leftEncoderPos = encoders[0].getCurrentPosition();
    }

    /**
     * Get ticks for left encoder
     */
    public int getLeftTicks() {
        return encoders[0].getCurrentPosition() - leftEncoderPos;
    }

    /**
     * Reset ticks for right encoder
     */
    public void resetRightTicks() {
        rightEncoderPos = encoders[1].getCurrentPosition();
    }

    /**
     * Get ticks for right encoder
     */
    public int getRightTicks() {
        return encoders[1].getCurrentPosition() - rightEncoderPos;
    }

    /**
     * Reset ticks for center encoder
     */
    public void resetCenterTicks() {
        centerEncoderPos = encoders[2].getCurrentPosition();
    }

    /**
     * Get ticks for center encoder
     */
    public int getCenterTicks() {
        return encoders[2].getCurrentPosition() - centerEncoderPos;
    }

    ////////////////////////////////////   intake subsystem   ////////////////////////////////////

    /**
     * Set power for intake motors
     * @param p0 power for left intake
     * @param p1 power for right intake
     */
    public void setIntakePowerAll(double p0, double p1) {
        intakeMotors[0].setPower(p0);
        intakeMotors[1].setPower(p1);
    }

    /**
     * Intake stones into the robot
     */
    public void intake() {
        setIntakePowerAll(1, -1);
    }

    /**
     * Eject stones out of the robot
     */
    public void eject() {
        setIntakePowerAll(-1, 1);
    }

    /**
     * Stop all intake motors
     */
    public void stopIntakeMotors() {
        for (int i = 0; i < 2; i++) {
            intakeMotors[i].setPower(0);
        }
    }

    /**
     * Get the intake motor object
     * @param index index of the desired motor
     * @return intake motor object
     */
    public DcMotor getIntakeMotor(int index) {
        return intakeMotors[index];
    }

    /////////////////////////////////////   lift subsystem   /////////////////////////////////////
    /**
     * Set power for the lift motor
     * @param p power for the lift motor
     */
    public void setLiftPower(double p) {
        lift.setPower(p);
    }

    /**
     * Set position for all grab servos
     * @param p0 position for top left grab
     * @param p1 position for bottom left grab
     * @param p2 position for top right grab
     * @param p3 position for bottom right grab
     */
    public void setGrabPositionAll(double p0, double p1, double p2, double p3) {
        grabServos[0].setPosition(p0);
        grabServos[1].setPosition(p1);
        grabServos[2].setPosition(p2);
        grabServos[3].setPosition(p3);
    }

    /**
     *  Set grab position to close and hold the tower
     */
    public void close() {
        setGrabPositionAll(0.5, 0.5, 0.5, 0.5);
    }

    /**
     * Set grab position to open and release the tower
     */
    public void open() {
        setGrabPositionAll(0, 0, 0, 0);
    }

    /**
     *  Get the lift motor object
     * @return lift motor object
     */
    public DcMotor getLiftMotor() {
        return lift;
    }

    /////////////////////////////////////   arm subsystem   /////////////////////////////////////
    /**
     * Set power for the arm motor
     * @param p power for the arm motor
     */
    public void setArmPower(double p) {
        arm.setPower(p);
    }

    /**
     *  Get the arm motor object
     * @return arm motor object
     */
    public DcMotor getArmMotor() {
        return arm;
    }

    /**
     * Set the grip position to hold the stone
     */
    public void hold() {
        grip.setPosition(1);
    }

    /**
     * Set the grip position to release the stone
     */
    public void release() {
        grip.setPosition(0.75);
    }
    /////////////////////////////////////   hook subsystem   /////////////////////////////////////

    /**
     * Set position for all hook servos
     * @param p0 position for left hook
     * @param p1 position for right hook
     */
    public void setHookPositionAll(double p0, double p1) {
        hookServos[0].setPosition(p0);
        hookServos[1].setPosition(p1);
    }

    /**
     * Set hook position to lock the foundation
     */
    public void lock() {
        setHookPositionAll(1, 0);
    }

    /**
     * Set hook position to unlock the foundation
     */
    public void unlock() {
        setHookPositionAll(0, 1);
    }

    /**
     * Get the hook servo object
     * @param index index of the desired servo
     * @return hook servo object
     */
    public Servo getHookServo(int index) {
        return hookServos[index];
    }

    ///////////////////////////////////   capstone subsystem   ///////////////////////////////////
    /**
     * Set power for the capstone continuous servo
     * @param p power for the capstone continuous servo
     */
    public void setCapPower(double p) {
        cap.setPower(p);
    }

    /**
     * Set power for capstone to extend the linear slides
     */
    private void extend() {
        cap.setPower(1);
    }

    /**
     * Set power for capstone to retract the linear slides
     */
    private void retract() {
        cap.setPower(-1);
    }

    //////////////////////////////////////   imu subsystem   //////////////////////////////////////

    /**
     * Get the angle on the x-axis
     * @return angle on the x-axis
     */
    public double getXAngle() {
        return -imu.getAngularOrientation().thirdAngle;
    }

    /**
     * Get the angle on the y-axis
     * @return angle on the y-axis
     */
    public double getYAngle() {
        return -imu.getAngularOrientation().secondAngle;
    }

    /**
     * Get the angle on the z-axis
     * @return angle on the z-axis
     */
    public double getZAngle() {
        return -imu.getAngularOrientation().firstAngle;
    }

    /**
     * Get the last stored heading
     * @return last heading
     */
    public double getLastAngle() {
        return lastHeading;
    }

    /**
     * Reset the global heading to the current heading
     */
    public void resetHeading() {
        lastHeading = getZAngle();
        globalHeading = 0;
    }

    /**
     * Get the adjusted heading
     * @return global heading
     */
    public double getHeading() {
        double deltaAngle = getZAngle() - getLastAngle();

        if (deltaAngle < degreeToRadian(-180)) {
            deltaAngle += degreeToRadian(360);
        } else if (deltaAngle > degreeToRadian(180)) {
            deltaAngle -= degreeToRadian(360);
        }

        globalHeading += deltaAngle;
        lastHeading = getZAngle();
        return globalHeading;
    }

    public double degreeToRadian(double degree) {
        return degree * Math.PI / 180.0;
    }

    @Override
    public void runOpMode(){}
}