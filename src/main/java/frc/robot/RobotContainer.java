// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final SwerveDrivetrain drivetrain = new SwerveDrivetrain();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
  private final FeederSubsystem feederSubsystem = new FeederSubsystem();

  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  private final CommandJoystick m_joystick = new CommandJoystick(0);
  private final CommandJoystick m_buttons = new CommandJoystick(1);
  private final SlewRateLimiter m_limiter = new SlewRateLimiter(0.1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    SmartDashboard.putData("Swerve Drive", drivetrain);
    SmartDashboard.putData("Command Scheduler", CommandScheduler.getInstance());
    m_autoChooser.setDefaultOption("Win The Game", Autos.doNothing(drivetrain));
    m_autoChooser.addOption("Lose The Game", Autos.goBackAndScore(drivetrain, shooterSubsystem));
    m_autoChooser.addOption("Go Forward", Autos.driveForward(drivetrain, 5.0));
    m_autoChooser.addOption("Go Forward Longer", Autos.driveForward(drivetrain, 50.0));
    m_autoChooser.addOption("Spin In Place", Autos.spinInPlace(drivetrain, 50.0));
    m_autoChooser.addOption("Go Forward And Spin", Autos.driveThenTurn(drivetrain));
    m_autoChooser.addOption("Go To Blue Start", Autos.goToBlueStart(drivetrain));
    //m_autoChooser.addOption("Go Forward And Go Right", Autos.driveThenRight(m_drivetrain));
    m_autoChooser.addOption("Go To All April Tags", Autos.goToAllAprilTags(drivetrain));
    m_autoChooser.addOption("Drive System Id", Autos.driveSystemId(drivetrain));
    m_autoChooser.addOption("Steer System Id", Autos.steerSystemId(drivetrain));
    
    SmartDashboard.putData("Autonomous Chooser", m_autoChooser);
    DriverStation.silenceJoystickConnectionWarning(true);
    CameraServer.startAutomaticCapture();
    configureBindings();
  }

  /**
   * Configures joystick bindings to commands.
   */
  private void configureBindings() {
    m_joystick.setXChannel(Constants.kAxisX);
    m_joystick.setYChannel(Constants.kAxisY);
    m_joystick.setZChannel(Constants.kAxisZ);
    
    if (Constants.kUseHeading) {
      m_joystick.axisMagnitudeGreaterThan(m_joystick.getXChannel(), 0.01)
        .or(m_joystick.axisMagnitudeGreaterThan(m_joystick.getYChannel(), 0.01))
        .or(m_joystick.axisMagnitudeGreaterThan(m_joystick.getZChannel(), 0.01))
        .or(m_joystick.axisMagnitudeGreaterThan(Constants.kAxisA, 0.01))
        .whileTrue(
          drivetrain.driveHeadingCommand(
          () -> Math.abs(m_joystick.getY()) < Constants.kDeadzone ? 0.0 : -m_joystick.getY() * Constants.kMaxVelocity,
          () -> Math.abs(m_joystick.getX()) < Constants.kDeadzone ? 0.0 : -m_joystick.getX() * Constants.kMaxVelocity,
          () -> DegreesPerSecond.of(Constants.kMaxInPlaceAngularVelocity * (Math.abs(m_joystick.getZ()) < Constants.kDeadzone ? 0.0 : m_joystick.getZ())))
          // () ->
          //   Rotation2d.fromRadians(Math.atan2(m_joystick.getRawAxis(Constants.kAxisA), m_joystick.getZ()))
          //   // TODO: CHECK FOR ALLIANCE ERROR
          //     .plus(!DriverStation.isFMSAttached() ? Constants.kElasticOffset : DriverStation.getAlliance().get().equals(Alliance.Blue) ? 
          //       Constants.kBlueOffset : Constants.kRedOffset))
          .withName("Joystick Controlling Robot"));
    } else {
      m_joystick.axisMagnitudeGreaterThan(m_joystick.getXChannel(), 0.01)
        .or(m_joystick.axisMagnitudeGreaterThan(m_joystick.getYChannel(), 0.01)
        .or(m_joystick.axisMagnitudeGreaterThan(m_joystick.getZChannel(), 0.01)))
        .whileTrue(
          drivetrain.driveCommand(
            () -> Math.abs(m_joystick.getY()) < Constants.kDeadzone ? 0.0 : m_joystick.getY() * Constants.kMaxVelocity,
            () -> Math.abs(m_joystick.getX()) < Constants.kDeadzone ? 0.0 : m_joystick.getX() * Constants.kMaxVelocity,
            () -> DegreesPerSecond.of(Constants.kMaxInPlaceAngularVelocity * (Math.abs(m_joystick.getZ()) < Constants.kDeadzone ? 0.0 : m_joystick.getZ())))
          .withName("Joystick Controlling Robot"));
    }
    
    m_joystick.button(8).toggleOnTrue(drivetrain.resetGyroscope());
    m_joystick.button(5).toggleOnTrue(intakeSubsystem.intakeCommand());
    m_joystick.button(4).whileTrue(intakeSubsystem.reverseIntakeCommand());
    m_joystick.button(2).whileTrue(feederSubsystem.ejectCommand());

    m_joystick.button(6).whileTrue(feederSubsystem.launchCommand());
    m_joystick.button(1).toggleOnTrue(shooterSubsystem.activeShooterCommand());

    m_joystick.button(3).toggleOnTrue(shooterSubsystem.shortLaunchCommand());
    //m_joystick.axisMagnitudeGreaterThan(3, 0.01).whileTrue(m_fuel.ejectCommand());
    //m_joystick.axisMagnitudeGreaterThan(2, 0.01).whileTrue(m_climber.climbCommand());
    m_joystick.povUp().whileTrue(climberSubsystem.climbUpCommand());
    m_joystick.povDown().whileTrue(climberSubsystem.climbDownCommand());
  }

  /**
   * @return The command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autoChooser.getSelected();
  }

  public SwerveDrivetrain getDrivetrain() {
    return drivetrain;
  }

  // TODO - DE:
  // Do not need this
  /*
   public Command getResetEncodersCommand() {
    return drivetrain.resetEncoders();
   }
   */

  /**
   * @return The currently simulated voltage of the entire robot
   */
  public Voltage getCurrentDraw() {
    return drivetrain.getCurrentDraw();
  }

  public CommandJoystick getJoystick() {
    return m_joystick;
  }
}
