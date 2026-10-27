/* Copyright (c) 2025 FIRST. All rights reserved.
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
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/*
 * This OpMode illustrates how to program your robot to drive field relative.  This means
 * that the robot drives the direction you push the joystick regardless of the current orientation
 * of the robot.
 *
 * This OpMode assumes that you have four mecanum wheels each on its own motor named:
 *   front_left_motor, front_right_motor, back_left_motor, back_right_motor
 *
 *   and that the left motors are flipped such that when they turn clockwise the wheel moves backwards
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 *
 */
@TeleOp(name = "Teleop Test", group = "Robot")
public class TeleopTest extends OpMode {
    // This declares the four motors needed
    DcMotor frontLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backLeftDrive;
    DcMotor backRightDrive;
    DcMotorEx intake;
    NormalizedColorSensor colorSensor;
    DcMotorEx Pollen_Cannon;
    DcMotorEx Nectar_Cannon;
    ServoImplEx Pollen_Kicker;
    ServoImplEx Pollen_Gate;
    ServoImplEx Nectar_Kicker;
    ServoImplEx Nectar_Gate;

    double pollen_Kicker_Open = .45;
    double pollen_Kicker_Closed = .7;
    double nectar_Kicker_Open = .35;
    double nectar_Kicker_Closed = .6;
    double pollen_Gate_Open = .6;
    double pollen_Gate_Closed = .4;
    double nectar_Gate_Open = .45;
    double nectar_Gate_Closed = .65;

    // This declares the IMU needed to get the current direction the robot is facing
    //IMU imu;


    @Override
    public void init() {


        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        Nectar_Cannon = hardwareMap.get(DcMotorEx.class, "Nectar_Cannon");
        Pollen_Cannon = hardwareMap.get(DcMotorEx.class, "Pollen_Cannon");
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "Rev_Color_Sensor");
        colorSensor.setGain(2);

        Pollen_Kicker = hardwareMap.get(ServoImplEx.class, "Pollen_Kicker");   // must match your config name
        Pollen_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Pollen_Gate = hardwareMap.get(ServoImplEx.class, "Pollen_Gate");   // must match your config name
        Pollen_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Kicker = hardwareMap.get(ServoImplEx.class, "Nectar_Kicker");   // must match your config name
        Nectar_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Gate = hardwareMap.get(ServoImplEx.class, "Nectar_Gate");   // must match your config name
        Nectar_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°

        // We set the left motors in reverse which is needed for drive trains where the left
        // motors are opposite to the right ones.
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        Pollen_Cannon.setDirection(DcMotor.Direction.REVERSE);
        Nectar_Cannon.setDirection(DcMotor.Direction.FORWARD);

        // This uses RUN_USING_ENCODER to be more accurate.   If you don't have the encoder
        // wires, you should remove these
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Pollen_Cannon.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Nectar_Cannon.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        imu = hardwareMap.get(IMU.class, "imu");
//        // This needs to be changed to match the orientation on your robot
//        RevHubOrientationOnRobot.LogoFacingDirection logoDirection =
//                RevHubOrientationOnRobot.LogoFacingDirection.UP;
//        RevHubOrientationOnRobot.UsbFacingDirection usbDirection =
//                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
//
//        RevHubOrientationOnRobot orientationOnRobot = new
//                RevHubOrientationOnRobot(logoDirection, usbDirection);
//        imu.initialize(new IMU.Parameters(orientationOnRobot));


    }

    @Override
    public void start() {
        Pollen_Kicker.setPosition(.55);
        Pollen_Gate.setPosition(.4);
        Nectar_Kicker.setPosition(.35);
        Nectar_Gate.setPosition(.65);
    }

    @Override
    public void loop() {
        telemetry.addLine("Press A to reset Yaw");
        telemetry.addLine("Hold left bumper to drive in robot relative");
        telemetry.addLine("The left joystick sets the robot direction");
        telemetry.addLine("Moving the right joystick left and right turns the robot");

//        color sensor code
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        final float[] hsvValues = new float[3];
        Color.colorToHSV(colors.toColor(), hsvValues);

        if (colorSensor instanceof DistanceSensor) {
            telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
        }
        telemetry.addLine()
                .addData("Red", "%.3f", colors.red)
                .addData("Green", "%.3f", colors.green)
                .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0])
                .addData("Saturation", "%.3f", hsvValues[1])
                .addData("Value", "%.3f", hsvValues[2]);
        telemetry.addData("Alpha", "%.3f", colors.alpha);


        // If you press the A button, then Qyou reset the Yaw to be zero from the way
        // the robot is currently pointing
        if (gamepad1.a) {
            //imu.resetYaw();
        }
        // If you press the left bumper, you get a drive from the point of view of the robot
        // (much like driving an RC vehicle)
//        if (gamepad1.left_bumper) {
            drive(gamepad1.right_stick_x, -gamepad1.left_stick_x, -gamepad1.left_stick_y);
//        } else {
//            driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
//        }
       // intake.setPower(gamepad1.right_stick_x);

        if (gamepad2.left_trigger >= .5) {
            Pollen_Kicker.setPosition(pollen_Kicker_Closed);
        }
        else {
            Pollen_Kicker.setPosition(pollen_Kicker_Open);
        }
        if (gamepad2.right_trigger >= .5) {
            Nectar_Kicker.setPosition(nectar_Kicker_Closed);
        }
        else {
            Nectar_Kicker.setPosition(nectar_Kicker_Open);
        }
        if (gamepad2.a){
            Pollen_Gate.setPosition(pollen_Gate_Closed);
            Nectar_Gate.setPosition(nectar_Gate_Open);
        }
        else{
            Pollen_Gate.setPosition(pollen_Gate_Open);
            Nectar_Gate.setPosition(nectar_Gate_Closed);
        }


//        if (gamepad2.left_bumper) {
//            Pollen_Gate.setPosition(pollen_Gate_Open);
//        }
//        else {
//            Pollen_Gate.setPosition(pollen_Gate_Closed);
//        }
//        if (gamepad2.right_bumper) {
//            Nectar_Gate.setPosition(nectar_Gate_Open);
//        }
//        else {
//            Nectar_Gate.setPosition(nectar_Gate_Closed);
//
//        }





        telemetry.addData("leftbumper", gamepad1.left_bumper);
        telemetry.addData("Servo position", Pollen_Kicker.getPosition());

    }

    // This routine drives the robot field relative
//    private void driveFieldRelative(double forward, double right, double rotate) {
//        // First, convert direction being asked to drive to polar coordinates
//        double theta = Math.atan2(forward, right);
//        double r = Math.hypot(right, forward);
//
//        // Second, rotate angle by the angle the robot is pointing
//        theta = AngleUnit.normalizeRadians(theta -
//                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
//
//        // Third, convert back to cartesian
//        double newForward = r * Math.sin(theta);
//        double newRight = r * Math.cos(theta);
//
//        // Finally, call the drive method with robot relative forward and right amounts
//        drive(newForward, newRight, rotate);
//    }

    // Thanks to FTC16072 for sharing this code!!
    public void drive(double forward, double right, double rotate) {
        // This calculates the power needed for each wheel based on the amount of forward,
        // strafe right, and rotate
        double multiplier = gamepad1.left_bumper ? .65 : 1;
        double frontLeftPower = multiplier * (forward + right + rotate);
        double frontRightPower = multiplier * (forward - right - rotate);
        double backRightPower = multiplier * (forward + right - rotate);
        double backLeftPower = multiplier * (forward - right + rotate);

        double maxPower = 1;


        // This is needed to make sure we don't pass > 1.0 to any wheel
        // It allows us to keep all of the motors in proportion to what they should
        // be and not get clipped
        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));

        // We multiply by maxSpeed so that it can be set lower for outreaches
        // When a young child is driving the robot, we may not want to allow full
        // speed.
        telemetry.addData("maxpower", maxPower);
        frontLeftDrive.setPower( (frontLeftPower / maxPower));
        frontRightDrive.setPower( (frontRightPower / maxPower));
        backLeftDrive.setPower( (backLeftPower / maxPower));
        backRightDrive.setPower( (backRightPower / maxPower));
        intake.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(1.2, 0.12, 0, 11.8));
        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(1.2, 0.12, 0, 11.8));
        Pollen_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(1.2, 0.12, 0, 11.8));


        double intakeTarget = 0.7 * 2780 * gamepad2.left_stick_y;   // ~70% of max, about 1,950 ticks/sec
        intake.setVelocity(intakeTarget);
        telemetry.addData("Intake target", intakeTarget);
        telemetry.addData("Intake actual", intake.getVelocity());

        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1.2, 0.12, 0, 11.8));

        double Ncannon = getCannonTicksPerSecond(4200);
        Nectar_Cannon.setVelocity(Ncannon);
        telemetry.addData("Nectar_Cannon target", Ncannon);
        telemetry.addData(" actual", Nectar_Cannon.getVelocity());
        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1.2, 0.12, 0, 11.8));

        double Pcannon = getCannonTicksPerSecond(3500);
        Pollen_Cannon.setVelocity(Pcannon);
        telemetry.addData("Pollen_Cannon target", Pcannon);
        telemetry.addData(" actual", Pollen_Cannon.getVelocity());
    }

    private double getCannonTicksPerSecond(double targetRpms) {
        double motorPulsesPerRevolution = 28;
        double targetTicksPerMinute = targetRpms * motorPulsesPerRevolution;
        return targetTicksPerMinute / 60;
    }
}
