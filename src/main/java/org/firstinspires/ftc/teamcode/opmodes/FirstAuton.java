package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.utilities.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by David Austin on 10/13/2016.
 */
@Autonomous(name="FirstAuton", group="Auton")
public class FirstAuton extends OpMode {
    IO io;
    final int INIT = 0;
    final int EXECUTE = 1;
    final int STOP = 2;
    final int FINISHED = 3;
    int state = INIT;
    ArrayList<BasicCommand> commands = new ArrayList<BasicCommand>();
    BasicCommand currentCommand;
    Iterator<BasicCommand> iterator;
    public void init() {
        io = new IO(hardwareMap);
        BasicCommand.setIO(io);
        BasicCommand.setTelemetry(telemetry);
        commands.add(new Rotate(45, 0, 0.7));
        commands.add(new WaitForTime(100));
        commands.add(new DriveForward(48, DriveForward.YGREATERTHAN, 0.5, 45));
        commands.add(new Rotate(0, 0.5, 0.5));
        commands.add(new WaitForTime(100));
        commands.add(new DriveHorizontal(78,48,.5));
        iterator = commands.iterator();
        currentCommand = iterator.next();
    }

    public void start() {
        io.setGyroOffset();
        io.resetDriveEncoders();
    }

    public void loop() {
        io.updatePosition();
        switch(state){
            case INIT:
                currentCommand.init();
                state = EXECUTE;
                break;
            case EXECUTE:
                if(currentCommand.isFinished()){
                    currentCommand.stop();
                    if (iterator.hasNext()) {
                        currentCommand = iterator.next();
                        state = INIT;
                    } else state = FINISHED;
                    break;
                }
                currentCommand.execute();
                break;
            case STOP:
                currentCommand.stop();
                if(iterator.hasNext()){
                    currentCommand = iterator.next();
                    state = INIT;
                }else state = FINISHED;
                break;
            case FINISHED:
                break;

        }
        telemetry.addData("x:", io.getX());
        telemetry.addData("y:", io.getY());

    }

    public void stop() {
        io.setDrivePower(0,0);
    }
}
