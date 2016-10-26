package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.utilities.HSPID;

/**
 * Created by David Austin on 10/20/2016.
 */
public class Rotate extends BasicCommand {

    double heading,leftSpd,rightSpd;
    HSPID headingPID;
    long timeOut;

    public  Rotate (double heading,double leftSpd,double rightSpd){
        this.heading = heading;
        this.leftSpd = leftSpd;
        this.rightSpd = rightSpd;
        headingPID = new HSPID(0.05,0,0);
        headingPID.setTarget(heading);
    }

    public void init() {
        timeOut = System.currentTimeMillis() + 4000;
    }

    public void execute(){
        double correction = headingPID.getCorrection(io.getHeading());
        correction = Range.clip(correction,-1,1);
        io.setDrivePower(-correction*leftSpd,correction*rightSpd);
        telemetry.addData("Heading: ", io.getHeading());
        telemetry.addData("Mode:", "Rotate");
    }

    public boolean isFinished(){
        return Math.abs(io.getHeading() - heading) <=3 || System.currentTimeMillis() >= timeOut;
    }
    public void stop() {
        io.setDrivePower(0,0);
    }
}
