package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// TODO - DE:
// It's easier to read in code when the class name reflects if it's a Command/Subsystem/Motor etc.
public class ClimberSubsystem extends SubsystemBase {
    private final SparkMax climberMotor;

    public ClimberSubsystem() {
        climberMotor = new SparkMax(Constants.kClimberId, MotorType.kBrushless);
    }

    public Command climbCommand() {
        return this.startEnd(() -> {
            climberMotor.set(.1);
            System.out.println("Climbing...");
        }, () -> {
           climberMotor.set(0);
           System.out.println("Stopped climbing.");
        });
    }
}
