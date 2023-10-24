package org.firstinspires.ftc.teamcode.ftc_2023_2024;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/*
 * This OpMode executes a Tank Drive control TeleOp a direct drive robot
 * The code is structured as an Iterative OpMode
 *
 * In this mode, the left and right joysticks control the left and right motors respectively.
 * Pushing a joystick forward will make the attached motor drive forward.
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="Robot: drive", group="Robot")
//@Disabled
public class drive extends OpMode{

    /* Declare OpMode members. */
    public DcMotor  frontLeftDrive   = null;
    public DcMotor  frontRightDrive  = null;
    public DcMotor  backLeftDrive    = null;
    public DcMotor  backRightDrive   = null;
    public IMU      imu              = null;

    /*
     * Code to run ONCE when the driver hits INIT
     */

    public void init() {
        // Define and Initialize Motors
        frontLeftDrive  = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        backLeftDrive   = hardwareMap.get(DcMotor.class, "backLeftDrive");
        backRightDrive  = hardwareMap.get(DcMotor.class, "backRightDrive");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        //gotten from Game Manual 0
        // Retrieve the IMU from the hardware map

        imu = hardwareMap.get(IMU.class, "imu");


        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

        //Gotten from Game Manual 0
        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
       /* if (gamepad1.options) {
            imu.resetYaw();
        }*/
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
   // @Override
    public void loop() {
       /* //sets the robot mode
        int mode = 1;
        //field centric drive
        if(gamepad1.x){
            //makes the controller rumble once
            gamepad1.rumbleBlips(1);
            mode = 1;
        }
        //robot centric drive
        if(gamepad1.y){
            //makes the controller rumble twice
            gamepad1.rumbleBlips(2);
            mode = 2;
        }
        //field centric turn only drive
        if(gamepad1.y){
            //makes the controller rumble twice
            gamepad1.rumbleBlips(3);
            mode = 3;
        }*/

        //Gotten from Game Manual 0
       /* //gets the angle of the robot
        double robotRotation = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        //gets the desired angle from the controller
        double controllerRotation = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);

        //determines the speed to be used
        double power = (Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y)) / 2;

        //logs the two angles
        telemetry.addData("robot angle: ", robotRotation);
        telemetry.addData("desired angle: ", controllerRotation);

        //rotates the robot or moves it forwards if it is at the desired rotation
        if(controllerRotation == robotRotation && mode == 1){
            frontLeftDrive.setPower(power);
            frontRightDrive.setPower(power);
            backLeftDrive.setPower(power);
            backRightDrive.setPower(power);
        }
        //changes the angle to 360 degrees
        else if(mode != 2){
            //adds 180 degrees (pi radians) to the desired rotation if it is negative
            if(controllerRotation < 0){
                controllerRotation = Math.abs(controllerRotation) + 3.14159265359;
            }
            //adds 180 degrees (pi radians) to the robot rotation if it is negative
            if(robotRotation < 0){
                controllerRotation = Math.abs(robotRotation) + 3.14159265359;
            }

            //decides which way to rotate based on the 360 degree angle
            if(Math.abs(controllerRotation - robotRotation) < Math.abs(robotRotation - controllerRotation)){
                frontLeftDrive.setPower(power);
                frontRightDrive.setPower(-power);
                backLeftDrive.setPower(power);
                backRightDrive.setPower(-power);
            }
            else{
                frontLeftDrive.setPower(-power);
                frontRightDrive.setPower(power);
                backLeftDrive.setPower(-power);
                backRightDrive.setPower(power);
            }
        }*/

        //robot centric code
        //if(mode == 2) {
            //gets drive variables
            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;

            //drives and turns the robot
            frontLeftDrive.setPower((-drive + turn)*.7  );
            frontRightDrive.setPower((-drive - turn)*.7);
            backLeftDrive.setPower((-drive + turn)*.7);
            backRightDrive.setPower((- drive - turn)*.7);
        //}
        //strafing code
        double strafe = gamepad1.left_stick_x;
        if(strafe > .1 || strafe < -.1){
            frontLeftDrive.setPower(strafe);
            frontRightDrive.setPower(-strafe);
            backLeftDrive.setPower(-strafe);
            backRightDrive.setPower(strafe);
        }

        //Gotten from Game Manual 0
        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        /*if (gamepad1.options) {
            gamepad1.rumbleBlips(5);
            imu.resetYaw();
        }*/




    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    //@Override
    public void stop() {
    }
}
