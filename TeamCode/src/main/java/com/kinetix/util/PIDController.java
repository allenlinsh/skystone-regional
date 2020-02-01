package com.kinetix.util;

public class PIDController {
    public static double MAX_I = 0.25;

    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;

    /*
     * Construct a PIDController object and set each of the PID parameters
     */
    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}
