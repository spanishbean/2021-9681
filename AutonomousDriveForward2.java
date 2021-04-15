package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import java.util.ArrayList;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
9681 Parallel Lions 2021
author: 9681 Software
GOALS: Place the wobble goal
DESCRIPTION: This code is used for our autonomous when we are located on the side of with the foundation tray.
 */
@Autonomous(name="Auto Drive Forward", group="Iterative Opmode")
public class AutonomousDriveForward2 extends OpMode
{


    /*
    ---MOTORS---
     */
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;

    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();

    private StateMachine machine;

    driveState strafeLeft;


    public void init() {

        /*
        ---HARDWARE MAP---
         */
        rightFront = hardwareMap.dcMotor.get("front right");
        leftFront = hardwareMap.dcMotor.get("front left");
        rightBack = hardwareMap.dcMotor.get("back right");
        leftBack = hardwareMap.dcMotor.get("back left");

        /*
        ---MOTOR DIRECTIONS---
         */
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        /*
        ---GROUPING---
         */
        motors.add(rightFront);
        motors.add(leftFront);
        motors.add(rightBack);
        motors.add(leftBack);


        /*
        ---USING STATES---
         */
            
        strafeLeft = new driveState(70, 1.0, motors, "left"); //here we are using encoders to strafe left

        strafeLeft.setNextState(null); //after strafing to the line, we park and our code is complete

    }


    @Override
    public void start(){

        machine = new StateMachine(strafeLeft);

    }



    public void loop()  {

        machine.update();

    }

    public void wait(int time) {
        try {
            Thread.sleep(time * 1000);//milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
