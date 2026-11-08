package org.firstinspires.ftc.teamcode.mechanisms;

import dev.nextftc.control.filters.Debouncer;
import dev.nextftc.hardware.RobotController;
import dev.nextftc.hardware.sensors.NextColorDistanceSensor;
import dev.nextftc.hardware.sensors.colors.ColorProfile;
import dev.nextftc.hardware.sensors.colors.ColorSpace;
import dev.nextftc.hardware.sensors.colors.NextColor;
import dev.nextftc.robot.Mechanism;
import dev.nextftc.robot.triggers.Trigger;

public class ColorSensor implements Mechanism {
    NextColorDistanceSensor colorSensor = new NextColorDistanceSensor(RobotController.controlHub(), 0);
    ColorProfile blueProfile = new ColorProfile(ColorSpace.HSV,
            NextColor.Companion.hsv(220f, 0.7f, 0.6f),
            NextColor.Companion.hsv(20f, 0.3f, 0.1f)
    );

    public final Trigger blue = new Trigger(() -> colorSensor.isColor(blueProfile))
            .debounce(.07, Debouncer.DebounceType.FALLING);

    @Override
    public void periodic() {
        colorSensor.update();
    }
}
