package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Fuel extends SubsystemBase {
    private final SparkMax feederRoller;
    private final SparkMax intakeLauncherRoller;

    public Fuel() {
        intakeLauncherRoller = new SparkMax(Constants.kLauncherId, MotorType.kBrushed);
        feederRoller = new SparkMax(Constants.kFeederId, MotorType.kBrushed);

        if (RobotBase.isSimulation()) {
            
        }
    }

    public Command intakeCommand() {
        return this.startEnd(() -> {
            intakeLauncherRoller.setVoltage(10);
            feederRoller.setVoltage(-12);
        }, () -> {
            intakeLauncherRoller.setVoltage(0);
            feederRoller.setVoltage(0);
        });
    }

    public Command launchCommand() {
        return this.run(() -> {
            intakeLauncherRoller.setVoltage(10.6);
            feederRoller.setVoltage(-6);
        }).withTimeout(1).andThen(() -> {
            intakeLauncherRoller.setVoltage(10.6);
            feederRoller.setVoltage(9);
        });
    }

    public Command ejectCommand() {
        return this.startEnd(() -> {
            intakeLauncherRoller.setVoltage(-10);
            feederRoller.setVoltage(12);
        }, () -> {
            intakeLauncherRoller.setVoltage(0);
            feederRoller.setVoltage(0);
        });
    }
}
