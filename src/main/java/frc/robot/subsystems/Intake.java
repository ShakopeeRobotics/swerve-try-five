package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    //private final SparkMax feederRoller;
    //private final SparkMax intakeLauncherRoller;
    private final SparkMax secondIntakeRoller;

    public Intake() {
        
        secondIntakeRoller = new SparkMax(Constants.kSecondIntakeId, MotorType.kBrushless);

        SparkFlexConfig config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);

        secondIntakeRoller.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        if (RobotBase.isSimulation()) {
            
        }
    }
    public Command intakeCommand() {
        return this.startEnd(() -> {
            //intakeLauncherRoller.setVoltage(10*.9);
            secondIntakeRoller.set(-0.40);
            //feederRoller.setVoltage(-9.5);
        }, () -> {
            secondIntakeRoller.setVoltage(0);
            //feederRoller.setVoltage(0);
        });
    }
    
}
