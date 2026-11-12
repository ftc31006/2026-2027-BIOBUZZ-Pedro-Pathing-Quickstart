package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {




    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("back_left_drive");
        c.frontRightName.set("front_right_drive");
        c.backLeftName.set("front_left_drive");
        c.backRightName.set("back_right_drive");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });


    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("IMU");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-6.793126984844058);
        c.yPodOffset.set(-2.0199514374019594);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.34725770770978864);
                Controller secondaryTranslationalForward = Controller.proportional(0.128302444101925);
                Controller primaryTranslationalLateral = Controller.proportional(0.4844180143647553);
                Controller secondaryTranslationalLateral = Controller.proportional(0.17897951242004229);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.018058489844231855));
                c.brake.set(Controller.proportionalFeedforward(0.015349716367597077));

                c.headingFeedback.set(Controller.proportional(5.4463318815479935));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.05682178472359529, 0.005495402726605458));

                c.linearBrakeCoefficients.set(Matrix.diag(0.12135558644510787, 1.6466013019806816));
                c.quadraticBrakeCoefficients.set(Matrix.diag(4.6493509738690895E-4, -0.03430283300870347));

                c.maxAchievableForwardVelocity.set(61.96729224894582);
                c.maxAchievableStrafeVelocity.set(46.68575833203188);
                c.naturalForwardDeceleration.set(31.196067522583668);
                c.naturalStrafeDeceleration.set(65.79153146322943);
            }
    );
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

}