package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import java.util.Optional;

import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.hardware.sensors.NextColorDistanceSensor;
import dev.nextftc.hardware.sensors.colors.ColorProfile;
import dev.nextftc.hardware.sensors.colors.ColorSpace;
import dev.nextftc.hardware.sensors.colors.NextColor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.triggers.Trigger;

public class NectarGate implements Mechanism {
    NextServo servo = new NextServo("Nectar_Gate");

    public Command open() { return instant(() -> servo.setPosition(0.45)); }
    public Command close() { return instant(() -> servo.setPosition(0.65)); }
}
