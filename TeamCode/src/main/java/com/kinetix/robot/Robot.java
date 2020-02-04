package com.kinetix.robot;

import com.kinetix.util.MathUtils;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot extends LinearOpMode{
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
     * cap: capstone
     * arm: arm
     * grip: grip
     * tilt: tilt
     * tl: top left
     * bl: bottom left
     * tr: top right
     * br: bottom right
     * arm: arm
     * hl: hook left
     * hr: hook right
     */
    public BNO055IMU imu;
    public DcMotor lb, rb, lf, rf;
    public DcMotor il, ir;
    public DcMotor lift;
    public DcMotor cap;
    public Servo grip, tilt;
    public Servo tl, bl, tr, br;
    public Servo arm;
    public Servo hl, hr;

    /**
     * Declare drive variables
     */
    private final double MM_PER_INCH = 25.4;
    private final double WHEEL_DIAMETER = MathUtils.round(100/MM_PER_INCH, 2); // specific for GoBilda Mecanum wheels
    private final double TICKS_PER_REV = 723.24; // specific for GoBilda 26:1 motors
    private final double IN_PER_REV = Math.PI * WHEEL_DIAMETER / TICKS_PER_REV; // ticks = inches / IN_PER_REV;
    private final double IN_PER_BLOCK = 23.625;
    public DcMotor driveMotors[] = new DcMotor[4];

    /**
     * Declare intake variables
     */
    public DcMotor intakeMotors[] = new DcMotor[2];

    /**
     * Declare hook variables
     */
    private Servo hookServos[] = new Servo[2];
    private int duration = 300; // time to complete servo movement (in milliseconds)

    /**
     * Declare imu variables
     */
    private double globalHeading;
    private double lastHeading;

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
        cap = (DcMotor)hardwareMap.get("capstone");
        arm = (Servo)hardwareMap.get("arm");
        grip = hardwareMap.get(Servo.class, "grip");
        //tilt = hardwareMap.get(Servo.class, "tilt");
        tl = (Servo)hardwareMap.get("top left");
        bl = (Servo)hardwareMap.get("bottom left");
        tr = (Servo)hardwareMap.get("top right");
        br = (Servo)hardwareMap.get("bottom right");
        hl = (Servo)hardwareMap.get("hook left");
        hr = (Servo)hardwareMap.get("hook right");
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

        for (int i = 0; i < 2; i++) {
            intakeMotors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            intakeMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            intakeMotors[i].setPower(0);
        }
    }

    /**
     * Initialize hook servos
     */
    public void initHook() {
        unlock();
    }

    /**
     * Initializes imu parameters
     */
    public void initImu() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
    }

    ////////////////////////////////////   drive subsystem   ////////////////////////////////////

    /**
     * Drive to an {x,y} position based on the global coordinates and turn to {heading} angle
     * with motor powers set to {speed}
     * @param x desired global x position
     * @param y desired global y position
     * @param heading desired heading
     * @param speed desired speed
     */
    public void goToPosition(double x, double y, double heading, double speed) {

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
        return inches/IN_PER_REV;
    }

    ////////////////////////////////////   intake subsystem   ////////////////////////////////////

    /**
     * Set power for intake motors
     * @param p0
     * @param p1
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

    /////////////////////////////////////   lift subsystem   /////////////////////////////////////

    /////////////////////////////////////   arm subsystem   /////////////////////////////////////

    /////////////////////////////////////   hook subsystem   /////////////////////////////////////

    /**
     * Set position for all servos
     * @param p0 position for left hook
     * @param p1 position for right hook
     */
    public void setHookPositionAll(double p0, double p1) {
        hookServos[0].setPosition(p0);
        hookServos[1].setPosition(p1);
    }

    /**
     * Set position to lock the foundation
     */
    public void lock() {
        setHookPositionAll(1, 0);
    }

    /**
     * Set position to unlock the foundation
     */
    public void unlock() {
        setHookPositionAll(0, 1);
    }

    ///////////////////////////////////   capstone subsystem   ///////////////////////////////////

    //////////////////////////////////////   imu subsystem   //////////////////////////////////////

    /**
     * Get the angle on the x-axis
     * @return angle on the x-axis
     */
    public double getXAngle() {
        return imu.getAngularOrientation().thirdAngle;
    }

    /**
     * Get the angle on the y-axis
     * @return angle on the y-axis
     */
    public double getYAngle() {
        return imu.getAngularOrientation().secondAngle;
    }

    /**
     * Get the angle on the z-axis
     * @return angle on the z-axis
     */
    public double getZAngle() {
        return imu.getAngularOrientation().firstAngle;
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

        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        globalHeading += deltaAngle;
        lastHeading = getZAngle();
        return globalHeading;
    }

    @Override
    public void runOpMode(){}
}