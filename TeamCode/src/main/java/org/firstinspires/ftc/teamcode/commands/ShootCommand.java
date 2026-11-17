package org.firstinspires.ftc.teamcode.commands;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.RampageConstants;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;

public class ShootCommand {
    public static Command shoot(ServoImplEx kicker) {
        return sequential(
                instant(() -> kicker.setPosition(RampageConstants.PollenKickerClosedPosition)),
                waitMs(2000),
                instant(() -> kicker.setPosition(RampageConstants.PollenKickerOpenPosition)),
                waitMs(2000)
        );
    }
}
