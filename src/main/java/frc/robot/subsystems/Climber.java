package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private final SparkMax m_climber;

    public Climber() {
        m_climber = new SparkMax(Constants.kClimberId, MotorType.kBrushless);
    }

    public Command climbCommand() {
        return this.startEnd(() -> {
            // m_climber.setVoltage(6.7); // this is unintentional, creep
            m_climber.set(.1);
            System.out.println("test!");
        }, () -> {
           //  m_climber.setVoltage(0);
           m_climber.set(0);
           System.out.println("Interrupted Command.");
        });
    }
}
