package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "VelocityTuner", group = "Robot")
public class VelocityTuner extends OpMode {
    // This declares the four motors needed
    double NVelovity = 3000;
    double PVelocity = 3000;
    DcMotorEx frontLeftDrive;
    DcMotorEx frontRightDrive;
    DcMotorEx backLeftDrive;
    DcMotorEx backRightDrive;
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



    /** The relativeLayout field is used to aid in providing interesting visual feedback
     * in this sample application; you probably *don't* need this when you use a color sensor on your
     * robot. Note that you won't see anything change on the Driver Station, only on the Robot Controller. */
    View relativeLayout;



    @Override
    public void init() {
        frontLeftDrive = getMotor("front_left_drive", DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive = getMotor("front_right_drive", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive = getMotor("back_left_drive", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive = getMotor("back_right_drive", DcMotor.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER, DcMotor.ZeroPowerBehavior.BRAKE);

        intake = getMotor("intake", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        Nectar_Cannon = getMotor("Nectar_Cannon", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        Pollen_Cannon = getMotor("Pollen_Cannon", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
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

        // Get a reference to the RelativeLayout so we can later change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    }

    @Override
    public void start() {
        Pollen_Kicker.setPosition(pollen_Kicker_Open);
        Nectar_Kicker.setPosition(nectar_Kicker_Open);
        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1, 0.12, 0, 11.8));

        double Ncannon = getCannonTicksPerSecond(NVelovity);
        Nectar_Cannon.setVelocity(Ncannon);
        telemetry.addData("Nectar_Cannon target", Ncannon);
        telemetry.addData(" actual", Nectar_Cannon.getVelocity());
        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1, 0.12, 0, 11.8));

        double Pcannon = getCannonTicksPerSecond(PVelocity);
        Pollen_Cannon.setVelocity(Pcannon);
        telemetry.addData("Pollen_Cannon target", Pcannon);
        telemetry.addData(" actual", Pollen_Cannon.getVelocity());
    }

    @Override
    public void loop() {
        if (gamepad2.aWasPressed()) {
            PVelocity += 100;
            double Pcannon = getCannonTicksPerSecond(PVelocity);
            Pollen_Cannon.setVelocity(Pcannon);
        }

            if (gamepad2.bWasPressed()) {
                NVelovity += 100;
                double Ncannon = getCannonTicksPerSecond(NVelovity);
                Nectar_Cannon.setVelocity(Ncannon);
            }
            if (gamepad2.xWasPressed()) {
                PVelocity -= 100;
                double Pcannon = getCannonTicksPerSecond(PVelocity);
                Pollen_Cannon.setVelocity(Pcannon);
            }
            if (gamepad2.yWasPressed()) {
                NVelovity -= 100;
                double Pcannon = getCannonTicksPerSecond(PVelocity);
                Pollen_Cannon.setVelocity(Pcannon);
            }
        telemetry.addData("Pollen cannon velocity", PVelocity);
        telemetry.addData("Nectar cannon velocity", NVelovity);
    }

    private double getCannonTicksPerSecond(double targetRpms) {
        double motorPulsesPerRevolution = 28;
        double targetTicksPerMinute = targetRpms * motorPulsesPerRevolution;
        return targetTicksPerMinute / 60;
    }

    private DcMotorEx getMotor(String name, DcMotor.Direction direction, DcMotor.RunMode mode, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, name);
        motor.setDirection(direction);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(mode);
        motor.setZeroPowerBehavior(zeroPowerBehavior);
        return motor;
    }
}