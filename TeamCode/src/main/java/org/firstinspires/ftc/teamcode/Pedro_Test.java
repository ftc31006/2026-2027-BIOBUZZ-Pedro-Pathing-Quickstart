package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Pedro_Test", group = "Autonomous")
public class Pedro_Test extends LinearOpMode {


    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2609, 8, -90);
    private final Pose path1 = poseFactory.of(59.028, 18.065, -90);
    private final Pose point2 = poseFactory.of(57.2203, 121.7529, -90);
    private final Pose point3 = poseFactory.of(9.3022, 122.0961, -90);


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

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                
                follow(follower, path2()),
                follow(follower, path3())
        );
    }

    @Override
    public void runOpMode() {
        Nectar_Cannon = getMotor("Nectar_Cannon",DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        Pollen_Cannon = getMotor("Pollen_Cannon", DcMotor.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER, DcMotor.ZeroPowerBehavior.FLOAT);
        Pollen_Kicker = hardwareMap.get(ServoImplEx.class, "Pollen_Kicker");   // must match your config name
        Pollen_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Pollen_Gate = hardwareMap.get(ServoImplEx.class, "Pollen_Gate");   // must match your config name
        Pollen_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Kicker = hardwareMap.get(ServoImplEx.class, "Nectar_Kicker");   // must match your config name
        Nectar_Kicker.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°
        Nectar_Gate = hardwareMap.get(ServoImplEx.class, "Nectar_Gate");   // must match your config name
        Nectar_Gate.setPwmRange(new PwmControl.PwmRange(500, 2500));     // goBILDA full range, gives the full 300°

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
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        waitForStart();

        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).constant(path1);
    }

    public Path path2() {
        return Paths.line(path1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
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

