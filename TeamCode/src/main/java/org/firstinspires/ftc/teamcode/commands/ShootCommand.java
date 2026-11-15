package org.firstinspires.ftc.teamcode.commands;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.RampageConstants;

public class ShootCommand {
    public static Command shoot(ServoImplEx kicker) {
        return sequential(
                setKickerPosition(kicker, RampageConstants.PollenKickerClosedPosition),
                waitMs(1000),
                setKickerPosition(kicker, RampageConstants.PollenKickerOpenPosition),
                waitMs(1000)
        );
    }

    private static Command setKickerPosition(ServoImplEx kicker, double position) {
        return Command.build()
            .setExecute(() -> {
                kicker.setPosition(position);
            });

    }
}
