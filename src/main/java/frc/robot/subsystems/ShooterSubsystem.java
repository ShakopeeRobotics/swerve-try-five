package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.RobotBase;
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
       
        
        // TODO - DE: Not sure if this is needed.
        if (RobotBase.isSimulation()) {}
    }



    public Command activeShooterCommand() {
        return this.run(() -> {
            intakeLauncherRollerMotor.set(launchFuelIntakeLauncherMotorSpeed);
        }).andThen(this.run(()->{}).repeatedly()).finallyDo(() -> {
            intakeLauncherRollerMotor.set(Constants.motorOffSpeed);
        });
    }

    public Command shortLaunchCommand() {
        return this.run(() -> {
            intakeLauncherRollerMotor.set(launchFuelShortIntakeLauncherMotorSpeed);
        //}).withTimeout(1).andThen(() -> {
           // intakeLauncherRoller.setVoltage(10.6*0.8);
           // feederRoller.setVoltage(9*0.6);
        }).andThen(this.run(()->{}).repeatedly()).finallyDo(() -> {
            intakeLauncherRollerMotor.set(Constants.motorOffSpeed);
        });
    }

    
}
