package org.firstinspires.ftc.teamcode.mechanisms;

import com.pedropathing.ivy.Command;

import dev.nextftc.hardware.actuators.NextServo;
import dev.nextftc.robot.Mechanism;

public class PollenKicker implements Mechanism {
    NextServo servo = new NextServo("Pollen_Kicker");

    public Command open() { return instant(() -> servo.setPosition(0.45)); }
    public Command close() { return instant(() -> servo.setPosition(0.7)); }
}
