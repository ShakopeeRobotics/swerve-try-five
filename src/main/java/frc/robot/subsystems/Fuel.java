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

public class Fuel extends SubsystemBase {
    private final SparkMax feederRoller;
    private final SparkMax intakeLauncherRoller;
    //private final SparkMax secondIntakeRoller;

    public Fuel() {
        intakeLauncherRoller = new SparkMax(Constants.kLauncherId, MotorType.kBrushless); //launcher
        feederRoller = new SparkMax(Constants.kFeederId, MotorType.kBrushless); //kicker
        
        if (RobotBase.isSimulation()) {
            
        }
    }



    public Command launchCommand() {
        return this.run(() -> {
            intakeLauncherRoller.setVoltage(10.6*0.8);
            feederRoller.setVoltage(-6);
        //}).withTimeout(1).andThen(() -> {
           // intakeLauncherRoller.setVoltage(10.6*0.8);
           // feederRoller.setVoltage(9*0.6);
        }).andThen(this.run(()->{}).repeatedly()).finallyDo(() -> {
            intakeLauncherRoller.setVoltage(0);
            feederRoller.setVoltage(0);
        });
    }
    public Command shortLaunchCommand() {
        return this.run(() -> {
            intakeLauncherRoller.setVoltage(10.6*0.7);
            feederRoller.setVoltage(-6);
        //}).withTimeout(1).andThen(() -> {
           // intakeLauncherRoller.setVoltage(10.6*0.8);
           // feederRoller.setVoltage(9*0.6);
        }).andThen(this.run(()->{}).repeatedly()).finallyDo(() -> {
            intakeLauncherRoller.setVoltage(0);
            feederRoller.setVoltage(0);
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
