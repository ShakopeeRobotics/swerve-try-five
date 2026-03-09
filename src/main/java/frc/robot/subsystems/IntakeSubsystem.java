package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// TODO - DE:
// It's easier to read in code when the class name reflects if it's a Command/Subsystem/Motor etc.
public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax secondIntakeRollerMotor;

    public static final double intakeMotorSpeed = -0.8;
    public static final double reverseIntakeMotorSpeed = 0.8;

    public IntakeSubsystem() {
        secondIntakeRollerMotor = new SparkMax(Constants.kSecondIntakeId, MotorType.kBrushless);
        //SparkFlexConfig config = new SparkFlexConfig();
        //config.idleMode(IdleMode.kCoast);

        //secondIntakeRollerMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        // TODO - DE: Not sure if this is needed.
        if (RobotBase.isSimulation()) {}
    }

    public Command intakeCommand() {
        return this.startEnd(() -> {
            secondIntakeRollerMotor.set(intakeMotorSpeed);
        }, () -> {
            secondIntakeRollerMotor.set(Constants.motorOffSpeed);
        });
    }

    public Command reverseIntakeCommand() {
        return this.startEnd(() -> {
            secondIntakeRollerMotor.set(reverseIntakeMotorSpeed);
        }, () -> {
            secondIntakeRollerMotor.set(Constants.motorOffSpeed);
        });
    }
}
