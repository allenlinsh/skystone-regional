package com.kinetix.util;

import com.qualcomm.robotcore.util.Range;

public class PIDController {

    private static double MAX_I = 0.25;
    private double kP = 0, kI = 0, kD = 0;
    private double p, i, d;
    private double setPoint = 0;
    private double lowerBound = 0, upperBound = 0;
    private boolean isBounded = false;
    private double minOutput = -1, maxOutput = 1;
    private boolean updated = false;
    private double lastTimeStamp = 0;
    private double lastError = 0;

    /**
     * Constructor for PIDController
     * @param kP the proportional coefficient
     * @param kI the integral coefficient
     * @param kD the derivative coefficient
     */
    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    /**
     * Set the setpoint for PIDController
     * @param setPoint desired setpoint
     */
    public void setSetPoint(double setPoint) {
        if (isBounded) {
            this.setPoint = MathUtils.clip(setPoint, lowerBound, upperBound);
        } else {
            this.setPoint = setPoint;
        }
    }

    /**
     * Set the setpoint bounds for PIDController
     * @param lowerBound desired setpoint lowerbound
     * @param upperBound desired setpoint upperbound
     */
    public void setBounds(double lowerBound, double upperBound) {
        if (upperBound > lowerBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            isBounded = true;
        }
    }

    /**
     * Set the power output range for PIDController
     * @param minOutput desired power minimum output
     * @param maxOutput desired power maximum output
     */
    public void setOutputRange(double minOutput, double maxOutput) {
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }

    /**
     * Calculate the adjusted power output based on the feedback provided using PIDController
     * @param feedback desired feedback to be calculated
     * @return adjusted power output using PIDController
     */
    public double calculate(double feedback) {
        double error = feedback - setPoint;
        double now, dt;
        double integrand = 0, derivative;
        double output;

        p = error * kP;
        if (!updated) {
            lastTimeStamp = System.currentTimeMillis();
            lastError = error;
            updated = true;
            return p;
        }

        now = System.currentTimeMillis();
        dt = now - lastTimeStamp;
        lastTimeStamp = now;

        integrand += dt * error;
        i = Range.clip(integrand * kI, -MAX_I, MAX_I);

        derivative = (error - lastError / dt);
        lastError = error;
        d = derivative * kD;

        output = p + i + d;

        return MathUtils.clip(output, minOutput, maxOutput);
    }

    /**
     * Get the adjusted proportional power output
     * @return proportional output
     */
    public double getP() {
        return this.p;
    }

    /**
     * Get the adjusted integral power output
     * @return integral output
     */
    public double getI() {
        return this.i;
    }

    /**
     * Get the adjusted derivative power output
     * @return derivative output
     */
    public double getD() {
        return this.d;
    }

}
