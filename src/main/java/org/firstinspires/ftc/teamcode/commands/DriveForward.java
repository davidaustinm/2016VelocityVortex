package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.HSPID;
import org.firstinspires.ftc.teamcode.utilities.IO;

/**
 * Created by David Austin on 10/20/2016.
 */
public class DriveForward extends BasicCommand{
    double targetPosition;
    double endDistance;
    double driveSpeed;
    HSPID distancePID;
    HSPID headingPID;
    public static final int YGREATERTHAN = 0;
    public static final int XGREATERTHAN = 1;
    public static final int YLESSTHAN = 2;
    public static final int XLESSTHAN = 3;
    int test;
    long endTime;
    public DriveForward(double targetPosition, int test, double spd, double targetHeading){
        headingPID = new HSPID(0.08, 0.02, 0);
        headingPID.setTarget(targetHeading);
        distancePID = new HSPID(.2,0,0);
        distancePID.setTarget(targetPosition);
        this.targetPosition = targetPosition;
        this.test = test;
        driveSpeed = spd;
    }
    public DriveForward(double dist) {
        this(dist, YGREATERTHAN, 0.5, 0.0);
    }

    public void init(){
        endTime = System.currentTimeMillis() + 5000;
    }

    public void execute(){
        double heading = io.getHeading();
        double correction = headingPID.getCorrection(heading);
        double distanceCorrection;
        switch(test) {
            case XGREATERTHAN:
            case XLESSTHAN:
                distanceCorrection = distancePID.getCorrection(io.getX());
                break;
            case YGREATERTHAN:
            case YLESSTHAN:
            default:
                distanceCorrection = distancePID.getCorrection(io.getY());
                break;
        }
        distanceCorrection = Range.clip(Math.abs(distanceCorrection),0,1);
        double leftSpeed = (driveSpeed * distanceCorrection) - correction;
        double rightSpeed = (driveSpeed * distanceCorrection) + correction;
        leftSpeed = Range.clip(leftSpeed, 0, 1);
        rightSpeed = Range.clip(rightSpeed,0,1);

        io.setDrivePower(leftSpeed,rightSpeed);
        telemetry.addData("x: ",io.getX());
        telemetry.addData("y: ",io.getY());
        telemetry.addData("Heading:", heading);
        telemetry.addData("Mode:", "Drive Forward");
    }

    public boolean isFinished(){
        if (System.currentTimeMillis() >= endTime) return true;
        switch(test) {
            case XGREATERTHAN:
                return io.getX() > targetPosition;
            case XLESSTHAN:
                return io.getX() < targetPosition;
            case YGREATERTHAN:
                return io.getY() > targetPosition;
            case YLESSTHAN:
            default:
                return io.getY() < targetPosition;
        }
    }

    public void stop(){
        io.setDrivePower(0.0,0.0);
    }
}
