package com.kinetix.util;

public class PIDController {
    public static double MAX_I = 0.25;

    public double kP = 0;
    public double kI = 0;
    public double kD = 0;

    /**
     * Constructor for PID controller
     * @param kP the proportional coefficient
     * @param kI the integral coefficient
     * @param kD the derivative coefficient
     */
    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

}
