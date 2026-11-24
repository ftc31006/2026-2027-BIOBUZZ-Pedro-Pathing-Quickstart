package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.ks_Tuner.ks;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.FlywheelPID;
@TeleOp
public class kv_Tuner extends OpMode {
    FlywheelPID flywheel = new FlywheelPID();
    public static double kv = 0.01;
    public double ks = .05;
    public double goalRPM = RampageConstants.PollenCannonVelocity;
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

        if (gamepad1.a){
            goalRPM = RampageConstants.PollenCannonVelocity;
        }
        else if (gamepad1.b) {
            goalRPM = 1500;
        }


        double currentStep = increments[incrementsIdx];

        if (gamepad1.dpadUpWasPressed()) {kv += currentStep; }
        if (gamepad1.dpadDownWasPressed()) {kv -= currentStep; }

        double power = (kv * goalRPM) + ks;
        flywheel.setMotorPower(power);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kv", "%.6f", kv);
        telemetry.addData("RPM", flywheel.getRPM());
        telemetry.addData("Ticks per sec", flywheel.getTicksPerSec());
    }
}
