package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.FlywheelPID;

@TeleOp
public class ks_Tuner extends OpMode {

    FlywheelPID flywheel = new FlywheelPID();
    public static double ks = 0;

    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementsIdx = 4;
    @Override
    public void init(){
        flywheel.init(hardwareMap, "Pollen_Cannon");
    }

    @Override
    public void loop(){
        if (gamepad1.dpadRightWasPressed() && incrementsIdx < 4){
            incrementsIdx++;
        }else if (gamepad1.dpadLeftWasPressed() && incrementsIdx > 0) {
            incrementsIdx--;
        }
        double currentStep = increments[incrementsIdx];

        if (gamepad1.dpadUpWasPressed()) {ks += currentStep; }
        if (gamepad1.dpadDownWasPressed()) {ks -= currentStep; }

        flywheel.setMotorPower(ks);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("ks", "%.6f", ks);
        telemetry.addData("RPM", flywheel.getRPM());
        telemetry.addData("Ticks per sec", flywheel.getTicksPerSec());
    }

}
