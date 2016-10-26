package org.firstinspires.ftc.teamcode.commands;

/**
 * Created by David Austin on 10/25/2016.
 */
public class WaitForTime extends BasicCommand {
    long endTime;
    int length;
    public WaitForTime(int length) {
        this.length = length;
    }
    public void init() {
        endTime = System.currentTimeMillis() + length;
    }

    public void execute() {
        telemetry.addData("Mode:", "WaitForTime");
    }
    public boolean isFinished() {
        return System.currentTimeMillis() >= endTime;
    }
}
