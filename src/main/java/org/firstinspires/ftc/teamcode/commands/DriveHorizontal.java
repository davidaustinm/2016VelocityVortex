package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.HSPID;

/**
 * Created by David Austin on 10/25/2016.
 */
public class DriveHorizontal extends BasicCommand {
    double x, y, speed;
    long endTime;
    HSPID headingPID, distancePID, coordinatePID;
    public DriveHorizontal(double x,double y,double speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        headingPID = new HSPID(0.08, 0.02, 0);
        headingPID.setTarget(0);
        distancePID = new HSPID(.2,0,0);
        distancePID.setTarget(x);
        coordinatePID = new HSPID(.2,0,0);
        coordinatePID.setTarget(y);
    }
    public void init(){
        endTime = System.currentTimeMillis() + 8000;
    }
    public void execute(){
        double heading = io.getHeading();
        double correction = headingPID.getCorrection(heading);
        correction += coordinatePID.getCorrection(io.getY());
        double distanceCorrection = distancePID.getCorrection(io.getX());
        distanceCorrection = Range.clip(distanceCorrection,0,1);
        double leftSpeed = (speed * distanceCorrection) - correction;
        double rightSpeed = (speed * distanceCorrection) + correction;
        leftSpeed = Range.clip(leftSpeed, 0, 1);
        rightSpeed = Range.clip(rightSpeed,0,1);
        io.setDrivePower(leftSpeed,rightSpeed);

        telemetry.addData("Left Speed: ",leftSpeed);
        telemetry.addData("Right Speed: ",rightSpeed);
        telemetry.addData("Heading:", heading);
        telemetry.addData("Mode:", "Drive Forward");
    }

    public boolean isFinished(){
        return Math.abs(x - io.getX()) <= 2 || endTime <= System.currentTimeMillis();
    }

    public void stop(){
        io.setDrivePower(0,0);
    }
}
