package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by David Austin on 10/17/2016.
 */
public class IO {
    DcMotor left1, left2, right1, right2;
    GyroSensor gyro;
    OpticalDistanceSensor lightSensor;
    double gyroOffset = 0;
    double leftOffset = 0, rightOffset = 0;
    double lastLeftEncoder = 0, lastRightEncoder = 0;
    double x = 0, y = 0;
    double COUNTSPERINCH = 84;
    public IO(HardwareMap map) {
        left1 = map.dcMotor.get("left1");
        left2 = map.dcMotor.get("left2");
        right1 = map.dcMotor.get("right1");
        right2 = map.dcMotor.get("right2");
        left1.setDirection(DcMotorSimple.Direction.REVERSE);
        left2.setDirection(DcMotorSimple.Direction.REVERSE);
        gyro = map.gyroSensor.get("gyro");
        lightSensor = map.opticalDistanceSensor.get("lightSensor");
    }

    public void setGyroOffset() {
        gyroOffset = gyro.getHeading();
    }

    public void resetDriveEncoders() {
        leftOffset = left1.getCurrentPosition();
        rightOffset = right1.getCurrentPosition();
        lastLeftEncoder = 0;
        lastRightEncoder = 0;
    }
    public void updatePosition() {
        double leftEncoder = getLeftDriveEncoder();
        double rightEncoder = getRightDriveEncoder();
        double averageChange = (leftEncoder - lastLeftEncoder + rightEncoder - lastRightEncoder)/2.0;
        double heading = Math.toRadians(getHeading());
        x += averageChange * Math.cos(heading);
        y += averageChange * Math.sin(heading);
        lastLeftEncoder = leftEncoder;
        lastRightEncoder = rightEncoder;
    }
    public double getX() {
        return x / COUNTSPERINCH;
    }
    public double getY() {
        return y / COUNTSPERINCH;
    }

    public double getHeading() {
        double heading = gyro.getHeading() - gyroOffset;
        while (heading > 180) {
            heading -= 360;
        }
        while (heading <= - 180) {
            heading += 360;
        }
        return -heading;
    }

    public double getLeftDriveEncoder() {
        return left1.getCurrentPosition() - leftOffset;
    }

    public double getRightDriveEncoder() {
        return right1.getCurrentPosition() - rightOffset;
    }

    public void setDrivePower(double left, double right) {
        left1.setPower(left);
        left2.setPower(left);
        right1.setPower(right);
        right2.setPower(right);
    }
}
