package org.firstinspires.ftc.teamcode.ftc_2023_2024;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@Autonomous(name="Robot: AutonomousBase", group="Robot")
@Disabled
public class AutonomousBase extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor  frontLeftDrive   = null;
    public DcMotor  frontRightDrive  = null;
    public DcMotor  backLeftDrive    = null;
    public DcMotor  backRightDrive   = null;
    public IMU      imu              = null;
    public DistanceSensor distanceSensor = null;

    //Encoder counts per rotation:   560
    private int encoderPerRotation   = 560;
    //Distance per rotation: 	    9.425"
    private double inchesPerRotation = 9.425;

    public void runOpMode(){

    }

    public void setup() {
        /* Define OpMode members. */
        frontLeftDrive   = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive  = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive    = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive   = hardwareMap.get(DcMotor.class, "backRightDrive");
        imu              = hardwareMap.get(IMU.class, "imu");
        distanceSensor   = hardwareMap.get(DistanceSensor.class, "distanceSensor");


        //makes the motors run using encoder
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //sets the motor direction to reverse
        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    //Drives a set distance in inches using encoder
    public void drive(double inches){
        telemetry.addData("drive - inches: ", inches);
        //a variable telling the desired encoder value
        int distance = (int)(inches * encoderPerRotation / inchesPerRotation);
        telemetry.addData("inches", inches);
        telemetry.addData("encoderPerRotation", encoderPerRotation);
        telemetry.addData("inchesPerRotation", inchesPerRotation);
        telemetry.addData("distance", distance);
        telemetry.update();

        //resets the encoders
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //runs the motors to the desired position
        frontLeftDrive.setTargetPosition(distance);
        frontRightDrive.setTargetPosition(distance);
        backLeftDrive.setTargetPosition(distance);
        backRightDrive.setTargetPosition(distance);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeftDrive.setPower(.5);
        frontRightDrive.setPower(.5);
        backLeftDrive.setPower(.5);
        backRightDrive.setPower(.5);
//        while(frontLeftDrive.getCurrentPosition() < distance && opModeIsActive()){
//            sleep(200);
//            telemetry.addData("front left", frontLeftDrive.getCurrentPosition());
//            telemetry.addData("front right", frontRightDrive.getCurrentPosition());
//            telemetry.addData("back left", backLeftDrive.getCurrentPosition());
//            telemetry.addData("back right", backRightDrive.getCurrentPosition());
//            telemetry.addData("distance", distance);
//            telemetry.update();
//        }
        telemetry.addLine("After While loop");
        telemetry.update();
//
//        //resets the encoders
//        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        telemetry.update();
        while(frontLeftDrive.isBusy()){
            sleep(100);
        }
        sleep(1000);
    }

    //Rotates the robot a set number of degrees
    public double turn(double degrees, boolean scan){
        telemetry.addLine("turn degrees: " + degrees + " scan: " + scan);

        //a variable telling the rotation the robot wants to go to in radians
        double position = degrees / 57.2957795131; //360/pi
        //a variable that will be used to measure the closest distance read during the turn. This will be used for deciding where the prop is.
        //sets distance to a high number that shouldn't matter but will allow math operations
        double distance = 50000;
        //subtracts the current imu position from the desired position to make it ignore the robot rotation when deciding where to turn to.
        position = position - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        //used to find the direction the robot should turn
        int direction = (int)(position/Math.abs(position));

        //sets the motor run mode to default
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //runs a loop until it reaches the desired location that slowly turns the robot
        while(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) != position && opModeIsActive()){
            //determines the smallest distance read during the turn
            telemetry.addData("Target Angle: ", position);
            telemetry.addData("Rotation direction: ", direction);
            telemetry.addData("Current Angle: ", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
            telemetry.update();
            if(scan){
                if(distance > distanceSensor.getDistance(DistanceUnit.INCH)){
                    distance = distanceSensor.getDistance(DistanceUnit.INCH);
                }
            }
            //has the robot turn
            frontLeftDrive.setPower(.5 * direction);
            frontRightDrive.setPower(-.5 * direction);
            backLeftDrive.setPower(.5 * direction);
            backRightDrive.setPower(-.5 * direction);
        }
        //stops the motors
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        //returns the motors to run by encoder
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //returns the farthest distance read during the turn if scan is true
        if(scan){
            sleep(1000);
            return distance;
        }
        //returns a number to large to mean much if scan was false. This is needed to fulfil the return requirement.
        sleep(1000);
        return 50000;
    }

    //finds the location of the team prop
    public double[] findProp(){
        telemetry.addLine("Finding prop");
        telemetry.update();
        //the array that will be returned. The first value is distance and the second value is position
        double[] returns = new double[]{50000, 2};
        //sets the distance to the position of the middle sensor -- position 2
        returns[0] = turn(-30, true);

        //checks to see if the left position reads closer. If it does, it is likely where the prop is -- position 1
//        double temp = turn(-90, true);
//        if(returns[0] > temp){
//            returns[0] = temp;
//            returns[1] = 1;
//        }
//
//        //checks to see if the right position reads closer. If it does, it is likely where the prop is -- position 3
//        turn(120, false);
//        temp = turn(90, true);
//        if(returns[0] > temp){
//            returns[0] = temp;
//            returns[1] = 3;
//        }
        return returns;
    }

    //uses other methods to fin the prop and place the pixel in the corresponding spot
    public void placePixel(String side){
        telemetry.addLine("Placing Pixel");
        telemetry.update();
        int direction = 1;
        if(side == "blue"){
            direction = -1;
        }
        drive(36);
        double[] position = findProp();
        //ensures that it doesn't run if the distance sensor scanned an unreasonable distance
//        if (position[0] < 20){
//            //turns to the correct position
//            if(position[1] == 1){
//                turn(-180 * direction, false);
//            }
//            else if(position[1] == 2){
//                turn(-90 * direction, false);
//            }
//            //drives the previously scanned distance to the tape
//            drive(position[0]);
//            //returns to facing backstage
//            drive(-position[0]);
//            if(position[1] == 1){
//                turn(180 * direction, false);
//            }
//            else if(position[1] == 2){
//                turn(90 * direction, false);
//            }
//        }
    }

    //drives the robot until it is a set distance from a wall
    public void driveToDistance(double distance)  {
        telemetry.addData("driveToDistance: ", distance);
        //sets robot run mode to default
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //drives the robot until it reaches the set distance
        frontLeftDrive.setPower(.5);
        frontRightDrive.setPower(.5);
        backLeftDrive.setPower(.5);
        backRightDrive.setPower(.5);

        //makes it wait until it reaches the correct distance
        while(distance != distanceSensor.getDistance(DistanceUnit.INCH) && opModeIsActive()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //stops the motors
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);

        //sets the runmode back to encoder
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }
}
