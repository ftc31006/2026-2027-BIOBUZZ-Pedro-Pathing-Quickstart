package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.FlywheelPID;

@TeleOp
public class kp_Tuner extends OpMode {
    FlywheelPID flywheel = new FlywheelPID();
    public static double kv = 0.01;
    public double ks = .05;
    public double kp = 0.0;
    public double goalRPM = RampageConstants.PollenCannonVelocity;
    double[] increments = {0.000001, 0.00001, 0.0001, 0.001, 0.01};
    int incrementsIdx = 2;
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

        if (gamepad1.dpadUpWasPressed()) {kp += currentStep; }
        if (gamepad1.dpadDownWasPressed()) {kp -= currentStep; }

        double feedForward = (kv * goalRPM) + ks;
        double error = goalRPM - flywheel.getRPM();
        double feedBack = error * kp;

        flywheel.setMotorPower(feedForward + feedBack);

        telemetry.addData("Step", "%.6f", currentStep);
        telemetry.addData("kp", "%.6f", kp);
        telemetry.addData("Error",error);
        telemetry.addData("RPM", flywheel.getRPM());
    }
}
