package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by David Austin on 10/4/2016.
 */
@TeleOp(name="Template: First Opmode", group="Teleop")
//@Disabled
public class FirstOpMode extends OpMode {
    DcMotor left1, left2, right1, right2;
    Servo servo1, servo2, servo3;
    OpticalDistanceSensor lightSensor;
    GyroSensor gyro;
    public void init() {
        left1 = hardwareMap.dcMotor.get("left1");
        left2 = hardwareMap.dcMotor.get("left2");
        left1.setDirection(DcMotorSimple.Direction.REVERSE);
        left2.setDirection(DcMotorSimple.Direction.REVERSE);
        right1 = hardwareMap.dcMotor.get("right1");
        right2 = hardwareMap.dcMotor.get("right2");
        gyro = hardwareMap.gyroSensor.get("gyro");
        lightSensor = hardwareMap.opticalDistanceSensor.get("lightSensor");
        //servo1 = hardwareMap.servo.get("servo1");
        //servo2 = hardwareMap.servo.get("servo2");
        servo3 = hardwareMap.servo.get("servo3");
    }

    public void setDrivePower(double left, double right) {
        left1.setPower(left);
        left2.setPower(left);
        right1.setPower(right);
        right2.setPower(right);
    }
    int servoPosition = 0;
    public void loop() {
        double throttle = -gamepad1.left_stick_y;
        double wheel = -gamepad1.right_stick_x;
        double left = throttle + wheel;
        double right = throttle - wheel;
        left = Range.clip(left, -1,1);
        right = Range.clip(right, -1, 1);
        setDrivePower(left, right);
        /*
        if (gamepad1.b) {
            servo1.setPosition(0.0);
        } else {
            servo1.setPosition(0.5);
        }
        if (gamepad1.x) {
            servo2.setPosition(1.0);
        } else{
            servo2.setPosition(0.5);
        }
        */
        if (gamepad1.a) servo3.setPosition(0.0);
        if (gamepad1.y) servo3.setPosition(1.0);
        telemetry.addData("Light:", lightSensor.getLightDetected());
        telemetry.addData("Gyro:", gyro.getHeading());

    }
}
