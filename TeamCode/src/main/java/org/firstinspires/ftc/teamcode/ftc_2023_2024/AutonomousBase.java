package org.firstinspires.ftc.teamcode.ftc_2023_2024;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;



@Autonomous(name="Robot: AutonomousBase", group="Robot")
public class AutonomousBase extends LinearOpMode {

    /* Declare OpMode members. */
    public static DcMotor  frontLeftDrive   = null;
    public static DcMotor  frontRightDrive  = null;
    public static DcMotor  backLeftDrive    = null;
    public static DcMotor  backRightDrive   = null;
    public static IMU      imu              = null;

    //Encoder count per rotation:   560
    private static int encoderPerRotation   = 560;
    //Distance per rotation: 	    9.425"
    private static double inchesPerRotation = 9.425;

    public void runOpMode(){
        /* Define OpMode members. */
        frontLeftDrive   = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive  = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive    = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive   = hardwareMap.get(DcMotor.class, "backRightDrive");
        imu              = hardwareMap.get(IMU.class, "imu");

        //makes the motors run using encoder
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Drives a set distance in inches using encoder
    public static void drive(double inches){
        //a variable telling the desired encoder value
        int distance = (int)(inches * encoderPerRotation / inchesPerRotation);

        //runs the motors to the desired position
        frontLeftDrive.setTargetPosition(distance);
        frontRightDrive.setTargetPosition(distance);
        backLeftDrive.setTargetPosition(distance);
        backRightDrive.setTargetPosition(distance);

        //resets the encoders
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //Rotates the robot a set number of degrees
    public static void turn(double degrees){
        //a variable telling the rotation the robot wants to go to in radians
        double distance = degrees / 57.2957795131;

        //runs a loop until it reaches the desired location that slowly turns the robot

    }

}
