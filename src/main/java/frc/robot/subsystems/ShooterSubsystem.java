package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// TODO - DE:
// It's easier to read in code when the class name reflects if it's a Command/Subsystem/Motor etc.
public class ShooterSubsystem extends SubsystemBase {
   // private final SparkMax feederRollerMotor; // Kicker
    private final SparkMax intakeLauncherRollerMotor; // Launcher

    // TODO - DE:
    // I had these in the constants file but they're only used here.
    public static final double launchFuelIntakeLauncherMotorSpeed = 0.806; // 10.6*0.8/12
   // public static final double launchFuelFeederRollerMotorSpeed = -0.166; // -2/12

    //public static final double postLaunchFeederRollerMotorSpeed = -0.45; // -9*0.6/12

    public static final double launchFuelShortIntakeLauncherMotorSpeed = 0.618; // 10.6*0.7/12
    //public static final double launchFuelShortFeederRollerMotorSpeed = -0.5; // -6/12

    public static final double ejectFuelShortIntakeLauncherMotorSpeed = -0.833; // -10/12
    //public static final double ejectFuelShortFeederRollerMotorSpeed = 1.0; // 12/12

    public ShooterSubsystem() {
        intakeLauncherRollerMotor = new SparkMax(Constants.kLauncherId, MotorType.kBrushless);
       
        configureMotor(intakeLauncherRollerMotor, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, false);
        // TODO - DE: Not sure if this is needed.
        SmartDashboard.putData("Test Shooter", testCommand());
        if (RobotBase.isSimulation()) {}

        SmartDashboard.putBoolean("Shooter", false);
    }



    public Command activeShooterCommand() {
        return this.startEnd(() -> {
            intakeLauncherRollerMotor.set(launchFuelIntakeLauncherMotorSpeed);
            SmartDashboard.putBoolean("Shooter", true);
        }, () -> {
            intakeLauncherRollerMotor.set(Constants.motorOffSpeed);
            SmartDashboard.putBoolean("Shooter", false);
        });
    }

    public Command shortLaunchCommand() {
        return this.startEnd(() -> {
            intakeLauncherRollerMotor.set(launchFuelShortIntakeLauncherMotorSpeed);
            SmartDashboard.putBoolean("Shooter", true);
        }, () -> {
            intakeLauncherRollerMotor.set(Constants.motorOffSpeed);
            SmartDashboard.putBoolean("Shooter", false);
        });
    }

    public Command testCommand() {
        return this.startEnd( () ->
            intakeLauncherRollerMotor.getClosedLoopController().setSetpoint(50.0, ControlType.kVelocity),
            () ->
            intakeLauncherRollerMotor.getClosedLoopController().setSetpoint(0.0, ControlType.kVelocity)
        );
    }

    /**
     * @param motor Motor to configure.
     * @param p Proportional gain.
     * @param i Integral gain.
     * @param d Derivative gain.
     * @param ff Feed forward gain.
     * @param posFactor Position conversion factor.
     * @param velFactor Velocity conversion factor.
     */
    private void configureMotor(SparkMax motor, double p, double i, double d, double ff,
    double posFactor, double velFactor, double outputRange, boolean inverted) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.inverted(inverted);
        config.smartCurrentLimit(130);
        config.closedLoop
            .pid(p, i, d)
            .positionWrappingEnabled(true)
            .positionWrappingInputRange(0.0, 1.0)
            .outputRange(-outputRange, outputRange)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        config.closedLoop.feedForward.kV(ff);
        config.encoder.positionConversionFactor(posFactor)
            .velocityConversionFactor(velFactor);
        config.alternateEncoder
            .positionConversionFactor(posFactor)
            .velocityConversionFactor(velFactor);
        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }
}
