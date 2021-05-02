
// THIS IS FROM THE NEW CODE CLAIRE MADE ON SUNDAY 3/7/21, but now Abby changed the wrist and servo controls on 3/17/21

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.util.Range;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.DigitalChannel;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.util.Range;
        import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="current teleop", group = "TeleOP")
public class TeleopNewCurrent2 extends OpMode {
    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor raiseArm1;
    DcMotor raiseArm2;
    DcMotor extendArm;
    Servo claw1;
    Servo claw2;
    Servo wrist;
    boolean powerControl = false;
    double powerGiven =0;
    boolean clamp = false;
    int powerButton;
    CRServo drag1, drag2;

    double armPowerMultiplier = 0.5;


    public void init() {
        // Hardware map is for phone

        //touchSense = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        rightFront = hardwareMap.dcMotor.get("front right");
        leftFront = hardwareMap.dcMotor.get("front left");
        backRight = hardwareMap.dcMotor.get("back right");
        backLeft = hardwareMap.dcMotor.get("back left");
        raiseArm1 = hardwareMap.dcMotor.get("raise arm");
        //raiseArm2 = hardwareMap.dcMotor.get("raise arm 2");
        extendArm = hardwareMap.dcMotor.get("extend arm");
        claw1 = hardwareMap.servo.get("claw 1");
        claw2 = hardwareMap.servo.get("claw 2");
        //drag1 = hardwareMap.crservo.get("drag front");
        //drag2 = hardwareMap.crservo.get("drag back");
        wrist = hardwareMap.servo.get("wrist");
        claw2.setPosition(-.5);

    }


    private void setRaiseArmPower(double armPower, double multiplier){
        raiseArm1.setPower(armPower*multiplier);
        //raiseArm2.setPower(armPower*multiplier);
        return;
    }


    public void loop() {
        //              -----STICK VARIABLES-----
        // For driving
        float move = -gamepad1.left_stick_y;
        float crabWalk = gamepad1.left_stick_x;
        float rotation = -gamepad1.right_stick_x;

        // For arm raising
        float rawRaiseValue = -gamepad2.left_stick_y;




        //              -----WHEEL LOGIC-----

        // Wheels: Holonomic drive formula uses values of gamestick position to move
        double fLeftPower = Range.clip(move + rotation + crabWalk, -1.0, 1.0);
        double bLeftPower = Range.clip(move + rotation - crabWalk, -1.0, 1.0);
        double fRightPower = Range.clip(move - rotation - crabWalk, -1.0, 1.0);
        double bRightPower = Range.clip(move - rotation + crabWalk, -1.0, 1.0);
        // Assignment of motor power in relation to wheels
        frontLeft.setPower(fLeftPower/powerButton);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft.setPower(bLeftPower/powerButton);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setPower(fRightPower/powerButton);

        backRight.setPower(bRightPower/powerButton);

        raiseArm1.setDirection(DcMotorSimple.Direction.FORWARD);

        //raiseArm2.setDirection(DcMotorSimple.Direction.REVERSE);




        //          -----GAME PAD 1-----

        //              ###SPEED BOOST###
        if (gamepad1.right_trigger > 0.1)
            powerButton = 1;
        else
            powerButton = 2;


        //              ###DRAG SERVOS###
        /*
        if (gamepad1.a) {
            drag1.setPower(.5);
            drag2.setPower(-.5);
        }
        else if (gamepad1.b) {
            drag1.setPower(-.5);
            drag2.setPower(.5);
        }
        else {
            drag1.setPower(0);
            drag2.setPower(0);
        }
        */

        /*
        if (gamepad1.x) {
            drag2.setPower(.5);
        }
        else if (gamepad1.y) {
            drag2.setPower(-.5);
        }
        else {
            drag2.setPower(0);
        }
        */




        //          -----GAME PAD 2-----

        //              ###CLAMPS###

        // Commented because setPower only works on CRServos and not Servos.
        /*
        if (gamepad2.x)
            clamp = true;
        if (gamepad2.y) {
            claw1.setPower(1);
            claw2.setPower(-1);
            clamp = false;
        }
        else if (!clamp) {
            claw1.setPower(0);
            claw2.setPower(0);
        }
        if (clamp) {
            claw1.setPower(-1);
            claw2.setPower(1);
        }
        */


        // claw1: 1=open, 0=closed
        // claw2: 0=open, 1=closed

        // Open Left (claw 1 is on the left side of the bot)
        if (gamepad2.y) {
            claw1.setPosition(0.6);
        }
        // Close
        else if (gamepad2.x) {
            claw1.setPosition(0.4);
        }

        // Open Right (only OpenLeft will be used primarily) (claw 2 is the 'right' side of the bot)
        if (gamepad2.b) {
            claw2.setPosition(0.4);
        }
        // Close
        else if (gamepad2.a) {
            claw2.setPosition(0.6);
        }


        //              ###ARM EXTENSION###
        extendArm.setPower(-gamepad2.right_stick_y);



        //              ###WRIST###
        if (gamepad2.right_bumper)
            wrist.setPosition(.8);
        else if (gamepad2.left_bumper)
            wrist.setPosition(.2);
        else
            wrist.setPosition(0.4
            );


        //              ###ARM RAISING###

        // Fast raise arm mode
        if (gamepad2.right_trigger > 0) {
            // If the driver is trying to move the arm up:
            if (rawRaiseValue > 0) {
                setRaiseArmPower(rawRaiseValue, 0.6);
            }
            // If the driver is trying to move the arm down:
            else if (rawRaiseValue < 0) {
                setRaiseArmPower(-1.0, 0.35);  // Maybe change armPower
            }
            // If the driver is not moving the arm:
            else{
                setRaiseArmPower(0.0, 1);
            }
        }
        // Slow raise arm mode
        else {
            // If the driver is trying to move the arm up:
            if (rawRaiseValue > 0) {
                setRaiseArmPower(rawRaiseValue, 0.35);
            }
            // If the driver is trying to move the arm down:
            else if (rawRaiseValue < 0) {
                setRaiseArmPower(-0.25, 1);  // Maybe change armPower
            }
            // If the driver is not moving the arm:
            else {
                setRaiseArmPower(0.0, 1);
            }
        }


    }


}
