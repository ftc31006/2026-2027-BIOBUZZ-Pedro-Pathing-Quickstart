package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.ColorSensor;
import org.firstinspires.ftc.teamcode.mechanisms.NectarGate;
import org.firstinspires.ftc.teamcode.mechanisms.NectarKicker;
import org.firstinspires.ftc.teamcode.mechanisms.PollenKicker;

import java.util.Set;

import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.NextRobot;

public class RampageRobot implements NextRobot {
    public final NectarKicker nectarKicker = new NectarKicker();
    public final PollenKicker pollenKicker = new PollenKicker();
    public final NectarGate nectarGate = new NectarGate();
    public final ColorSensor colorSensor = new ColorSensor();

    @Override
    public Set<Mechanism> getMechanisms() {
        return Set.of(nectarKicker, pollenKicker, nectarGate, colorSensor);
    }
}
