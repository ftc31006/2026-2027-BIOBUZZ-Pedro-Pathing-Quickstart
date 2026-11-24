package org.firstinspires.ftc.teamcode.pedro;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlywheelPID {
   DcMotorEx Cannon;


   private double encoderCPM = 28;

   private double kv = .000192, ks = .05, kp;

   public void init(HardwareMap hwMap, String cannonName) {
      Cannon = hwMap.get(DcMotorEx.class, cannonName);
      Cannon.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      Cannon.setDirection(DcMotorSimple.Direction.REVERSE);
   }

   public void setMotorPower(double power) {
      Cannon.setPower(power);
   }

   public double getTicksPerSec(){
      return Cannon.getVelocity();
   }

   public double getRPM(){
      return ((getTicksPerSec()/encoderCPM) * 60);
   }

}
