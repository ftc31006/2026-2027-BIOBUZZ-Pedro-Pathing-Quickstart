package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Autonomous(name = "Autonomous Shoot", group = "Robot")
public class AutonomousOpModeShoot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        double drivePower = .15;
        DcMotor frontLeftDrive;
        DcMotor frontRightDrive;
        DcMotor backLeftDrive;
        DcMotor backRightDrive;
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
        Nectar_Cannon = getMotor("Nectar_Cannon", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        Pollen_Cannon = getMotor("Pollen_Cannon", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        Pollen_Kicker = hardwareMap.get(ServoImplEx.class, "Pollen_Kicker");   // must match your config name
        Pollen_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Pollen_Gate = hardwareMap.get(ServoImplEx.class, "Pollen_Gate");   // must match your config name
        Pollen_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Kicker = hardwareMap.get(ServoImplEx.class, "Nectar_Kicker");   // must match your config name
        Nectar_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Gate = hardwareMap.get(ServoImplEx.class, "Nectar_Gate");   // must match your config name
        Nectar_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(1.2, 0.12, 0, 11.8));
        Pollen_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,
                new PIDFCoefficients(1.2, 0.12, 0, 11.8));

        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1, 0.12, 0, 11.8));

        double Ncannon = getCannonTicksPerSecond(4200);
        Nectar_Cannon.setVelocity(Ncannon);
        telemetry.addData("Nectar_Cannon target", Ncannon);
        telemetry.addData(" actual", Nectar_Cannon.getVelocity());
        Nectar_Cannon.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER,

                new PIDFCoefficients(1, 0.12, 0, 11.8));

        double Pcannon = getCannonTicksPerSecond(4100);
        Pollen_Cannon.setVelocity(Pcannon);
        telemetry.addData("Pollen_Cannon target", Pcannon);
        telemetry.addData(" actual", Pollen_Cannon.getVelocity());
        backLeftDrive.setPower(-drivePower);
        backRightDrive.setPower(-drivePower);
        frontLeftDrive.setPower(-drivePower);
        frontRightDrive.setPower(-drivePower);
        sleep(250);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        sleep(150);
        Pollen_Kicker.setPosition(pollen_Kicker_Closed);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Open);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Closed);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Open);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Closed);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Open);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Closed);
        sleep(1000);
        Pollen_Kicker.setPosition(pollen_Kicker_Open);
        sleep(1000);
        backLeftDrive.setPower(50);
        backRightDrive.setPower(-50);
        frontLeftDrive.setPower(50);
        frontRightDrive.setPower(-50);
        sleep(5000);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
//        backLeftDrive.setPower(drivePower);
//        backRightDrive.setPower(drivePower);
//        frontLeftDrive.setPower(drivePower);
//        frontRightDrive.setPower(drivePower);
//        sleep(1400);
//        backLeftDrive.setPower(0);
//        backRightDrive.setPower(0);
//        frontLeftDrive.setPower(0);
//        frontRightDrive.setPower(0);
    }

    private DcMotorEx getMotor(String name, DcMotor.Direction direction, DcMotor.RunMode mode, DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, name);
        motor.setDirection(direction);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setMode(mode);
        motor.setZeroPowerBehavior(zeroPowerBehavior);
        return motor;
    }

    private double getCannonTicksPerSecond(double targetRpms) {
        double motorPulsesPerRevolution = 28;
        double targetTicksPerMinute = targetRpms * motorPulsesPerRevolution;
        return targetTicksPerMinute / 60;
    }
}
