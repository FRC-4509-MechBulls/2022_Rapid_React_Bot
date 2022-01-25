// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.DeployIntakeLeftCmd;
import frc.robot.commands.DeployIntakeRightCmd;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.RetractIntakeLeftCmd;
import frc.robot.commands.RetractIntakeRightCmd;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.ShiftInCmd;
import frc.robot.commands.ShiftOutCmd;
import frc.robot.commands.Shoot1Cmd;
import frc.robot.commands.Shoot2Cmd;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ShooterSub;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  //Controllers
  public static XboxController driverController;
  public static XboxController shooterController;

  //Shooter
  ShooterSub shooter;
  Shoot1Cmd shoot1;
  Shoot2Cmd shoot2;

  //DriveTrain
  ShiftInCmd shiftIn;
  ShiftOutCmd shiftOut;
  DriveTrainSub driveTrain;
  JoystickDriveCmd joystickDrive;

  //Intake
  private final IntakeSub intake;
  public static DeployIntakeLeftCmd deployIntake;

  //Indexer
  IndexerSub indexer;
  
  //Limelight
  LimelightSub limelight;
  
  //Sonar
  SonarSub sonar;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Controllers
    driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    shooterController = new XboxController(Constants.SHOOTER_CONTROLLER);

    //Initializing all DriveTrain Components
    driveTrain = new DriveTrainSub();
    limelight = new LimelightSub();
    shiftIn.addRequirements(driveTrain);
    shiftOut.addRequirements(driveTrain);
    joystickDrive.addRequirements(driveTrain);
    driveTrain.setDefaultCommand(joystickDrive);

    
    //Initializing all Intake Components
    intake = new IntakeSub();

    
    //Intitializing all Shooter Components
    shooter = new ShooterSub();
    shoot1 = new Shoot1Cmd(shooter);
    shoot1.addRequirements(shooter);
    shoot2 = new Shoot2Cmd(shooter);
    shoot2.addRequirements(shooter);

    //Initializing sonar sub
    sonar = new SonarSub(Constants.SONAR_CHANNEL);

    //Inititalizing all Indexer Components
    indexer = new IndexerSub();

    // runs indexer when sonar conditions are true
    Trigger indexer1Trigger = new Trigger(() -> sonar.isRequiredDistance());
    indexer1Trigger.whileActiveContinuous(new IndexBallCmd(indexer));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Intake
    JoystickButton deployIntakeLeftButton = new JoystickButton(shooterController, XboxController.Button.kRightBumper.value);
    deployIntakeLeftButton.whenPressed(new DeployIntakeLeftCmd(intake));
   
    JoystickButton retractIntakeLeftButton = new JoystickButton(shooterController,XboxController.Button.kLeftBumper.value);
    retractIntakeLeftButton.whenPressed(new RetractIntakeLeftCmd(intake));

    JoystickButton deployIntakeRightButton = new JoystickButton(shooterController,XboxController.Button.kRightBumper.value);
    deployIntakeRightButton.whenPressed(new DeployIntakeRightCmd(intake));

    JoystickButton retractIntakeRightButton = new JoystickButton(shooterController,XboxController.Button.kRightBumper.value);
    retractIntakeRightButton.whenPressed(new RetractIntakeRightCmd(intake));
    
    //Shooter
    JoystickButton shootButton1 = new JoystickButton(shooterController, XboxController.Button.kA.value);
    shootButton1.whileHeld(new Shoot1Cmd(shooter));

    JoystickButton shootButton2 = new JoystickButton(shooterController, XboxController.Button.kB.value);
    shootButton2.whileHeld(new Shoot2Cmd(shooter));

    //DriveTrain
    JoystickButton shiftInButton = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
    shiftInButton.whileHeld(new ShiftInCmd(driveTrain));

    JoystickButton shiftOutButton = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
    shiftOutButton.whileHeld(new ShiftOutCmd(driveTrain));
  
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
