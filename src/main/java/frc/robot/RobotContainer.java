// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.beans.beancontext.BeanContextMembershipEvent;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DeployIntakeLeftCmd;
import frc.robot.commands.DeployIntakeRightCmd;
import frc.robot.commands.IndexBallLeftCmd;
import frc.robot.commands.IndexBallRightCmd;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.RetractIntakeLeftCmd;
import frc.robot.commands.RetractIntakeRightCmd;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.commands.ShiftInCmd;
import frc.robot.commands.ShiftOutCmd;
import frc.robot.commands.ShootShootersCmd;
import frc.robot.commands.ShootTopCmd;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.SonarSub;


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
  ShootShootersCmd shootShooters;
  ShootTopCmd shootTop;

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
  IndexBallLeftCmd indexBall1;
  IndexBallRightCmd indexBall2;
  
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

    joystickDrive = new JoystickDriveCmd(driveTrain, limelight);
    joystickDrive.addRequirements(driveTrain, limelight);
    driveTrain.setDefaultCommand(joystickDrive);

    
    //Initializing all Intake Components
    intake = new IntakeSub();

    
    //Intitializing all Shooter Components
    shooter = new ShooterSub();
    shootShooters = new ShootShootersCmd(shooter);
    shootShooters.addRequirements(shooter);
    shootTop = new ShootTopCmd(shooter);
    shootTop.addRequirements(shooter);

    //Initializing sonar sub
    sonar = new SonarSub();

    //Inititalizing all Indexer Components
    indexer = new IndexerSub();

    //runs indexer when sonar conditions are true
    //Trigger indexer1Trigger = new Trigger(() -> sonar.canRun1());
    //indexer1Trigger.whileActiveContinuous(new IndexBall1Cmd(indexer));

    //Trigger indexer2Trigger = new Trigger(() -> sonar.canRun2());
    //indexer2Trigger.whileActiveContinuous(new IndexBall2Cmd(indexer));

    //Indexer beam break triggers
    //Each trigger represents an individual status, which determines which indexer should be ran
    Trigger beamBreakDetector1 = new Trigger(() -> indexer.getBreakStatus1());
    beamBreakDetector1.whileActiveContinuous(new IndexBallLeftCmd(indexer));
    beamBreakDetector1.whileActiveContinuous(new IndexBallRightCmd(indexer));

    Trigger beamBreakDetector2 = new Trigger(() -> indexer.getBreakStatus2());
    beamBreakDetector2.whileActiveContinuous(new IndexBallLeftCmd(indexer));

    Trigger beamBreakDetector3 = new Trigger(() -> indexer.getBreakStatus3());
    //beamBreakDetector3.whileActiveContinuous();

    Trigger beamBreakDetector4 = new Trigger(() -> indexer.getBreakStatus4());
    beamBreakDetector4.whileActiveContinuous(new IndexBallRightCmd(indexer));

    Trigger beamBreakDetector5 = new Trigger(() -> indexer.getBreakStatus5());
    beamBreakDetector5.whileActiveContinuous(new IndexBallLeftCmd(indexer));

    Trigger beamBreakDetector6 = new Trigger(() -> indexer.getBreakStatus6());
    beamBreakDetector6.whileActiveContinuous(new IndexBallLeftCmd(indexer));
    beamBreakDetector6.whileActiveContinuous(new IndexBallRightCmd(indexer));


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
    /* Intake */
    JoystickButton deployIntakeLeftButton = new JoystickButton(shooterController, XboxController.Button.kRightBumper.value);
    deployIntakeLeftButton.whenPressed(new DeployIntakeLeftCmd(intake));
   
    JoystickButton retractIntakeLeftButton = new JoystickButton(shooterController,XboxController.Button.kLeftBumper.value);
    retractIntakeLeftButton.whenPressed(new RetractIntakeLeftCmd(intake));

    JoystickButton deployIntakeRightButton = new JoystickButton(shooterController,XboxController.Button.kRightBumper.value);
    deployIntakeRightButton.whenPressed(new DeployIntakeRightCmd(intake));

    JoystickButton retractIntakeRightButton = new JoystickButton(shooterController,XboxController.Button.kRightBumper.value);
    retractIntakeRightButton.whenPressed(new RetractIntakeRightCmd(intake));
    
    /* Shooter */
    JoystickButton shootShootersButton = new JoystickButton(shooterController, XboxController.Button.kA.value);
    shootShootersButton.whileHeld(new ShootShootersCmd(shooter));

    /* JoystickButton shootTopButton = new JoystickButton(shooterController, XboxController.Button.kB.value);
    shootTopButton.whileHeld(new ShootTopCmd(shooter)); */

    /* Shifting */
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
