// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DeployIntakeCmd;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.RetractIntakeCmd;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IntakeSub;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static XboxController driverJoystick;
  public static XboxController shooterJoystick;
  private final IntakeSub intake;
  public static DeployIntakeCmd deployIntake;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Initializing all DriveTrain Components
    DriveTrainSub driveTrain = new DriveTrainSub();
    JoystickDriveCmd joystickDrive = new JoystickDriveCmd(driveTrain);
    joystickDrive.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(joystickDrive);
    driverJoystick = new XboxController(Constants.DRIVER_JOYSTICK);
    
    //Initializing all Intake Components
    intake = new IntakeSub();
    DeployIntakeCmd deployIntake = new DeployIntakeCmd(intake);
    RetractIntakeCmd retractIntake = new RetractIntakeCmd(intake);

    shooterJoystick = new XboxController(Constants.SHOOTER_JOYSTICK);
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton deployIntakeButton = new JoystickButton(shooterJoystick, XboxController.Button.kRightBumper.value);
    deployIntakeButton.whenPressed(new DeployIntakeCmd(intake));
   
   
    JoystickButton retractIntakeButton = new JoystickButton(shooterJoystick,XboxController.Button.kLeftBumper.value);
    retractIntakeButton.whenPressed(new RetractIntakeCmd(intake));
  
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand all run in autonomous
    return null;
  }
}
