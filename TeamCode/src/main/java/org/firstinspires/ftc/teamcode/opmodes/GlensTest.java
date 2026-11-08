package org.firstinspires.ftc.teamcode.opmodes;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.teamcode.RampageRobot;

import dev.nextftc.robot.Telemetry;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.opmode.NextTeleop;
import dev.nextftc.robot.triggers.CommandGamepad;

@NextTeleop(name = "Glen's Test")
public class GlensTest extends NextOpMode {
    private final RampageRobot robot;

    public GlensTest(@NonNull RampageRobot robot) {
        super(robot);

        this.robot = robot;

        CommandGamepad gp1 = new CommandGamepad(gamepad1);

        gp1.a().onTrue(robot.nectarKicker.close());
        gp1.a().onFalse(robot.nectarKicker.open());

        gp1.b().onTrue(robot.pollenKicker.close());
        gp1.b().onFalse(robot.pollenKicker.open());

        robot.colorSensor.blue.onTrue(robot.nectarGate.open());
        robot.colorSensor.blue.onFalse(robot.nectarGate.close());
    }

    @Override
    public void periodic() {
        Telemetry.log("Status", "Running");
    }
}
