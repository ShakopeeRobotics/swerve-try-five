package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// TODO - DE:
// It's easier to read in code when the class name reflects if it's a Command/Subsystem/Motor etc.
public class FeederSubsystem extends SubsystemBase {
    private final SparkMax feederRollerMotor; // Kicker

    public static final double launchFuelFeederRollerMotorSpeed = -0.5; // -2/12

    public static final double postLaunchFeederRollerMotorSpeed = -0.45; //   -9*0.6/12

    public static final double launchFuelShortFeederRollerMotorSpeed = -0.5; // -6/12    

    public static final double ejectFuelShortFeederRollerMotorSpeed = 1.0; // 12/12
        
    public FeederSubsystem(){

        feederRollerMotor = new SparkMax(Constants.kFeederId, MotorType.kBrushless);
    }
    public Command launchCommand() {
        return this.run(() -> {
            feederRollerMotor.set(launchFuelFeederRollerMotorSpeed);
        }).andThen(this.run(()->{}).repeatedly()).finallyDo(() -> {
            feederRollerMotor.set(Constants.motorOffSpeed);
        });
    }
    public Command ejectCommand() {
        return this.startEnd(() -> {
            //intakeLauncherRollerMotor.set(ejectFuelShortIntakeLauncherMotorSpeed);
            feederRollerMotor.set(ejectFuelShortFeederRollerMotorSpeed);
        }, () -> {
            //intakeLauncherRollerMotor.set(Constants.motorOffSpeed);
            feederRollerMotor.set(Constants.motorOffSpeed);
        });
    }


}
