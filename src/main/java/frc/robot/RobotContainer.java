// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ClimbStage1;
import frc.robot.commands.ClimbStage2;
import frc.robot.commands.DeployIntakeCmd;
import frc.robot.commands.FenderShotCmd;
import frc.robot.commands.IndexBallCmd;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.RejectBallCmd;
import frc.robot.commands.RetractIntakeCmd;
import frc.robot.commands.SetHoodToAngleCmd;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.commands.ShiftInCmd;
import frc.robot.commands.ShiftOutCmd;
import frc.robot.commands.ShootShootersCmd;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ServoSub;
import frc.robot.subsystems.ShooterClimbSub;
import frc.robot.subsystems.VisionSub;


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

  //ShooterClimb
  private ShooterClimbSub shooterClimb;
  private ShootShootersCmd shootShooters;
  private ClimbStage1 climbStage1;
  private ClimbStage2 climbStage2;

  //DriveTrain
  private ShiftInCmd shiftIn;
  private ShiftOutCmd shiftOut;
  private DriveTrainSub driveTrain;
  private JoystickDriveCmd joystickDrive;

  //Intake
  private IntakeSub intake;
  private DeployIntakeCmd deployIntake;
  private RetractIntakeCmd retractIntake;

  //Indexer
  private IndexerSub indexer;
  private IndexBallCmd indexBall1;
  
  //Limelight/Vision
  private LimelightSub limelight;
  private VisionSub camera;
  //Sonar

  //Servo
  private ServoSub servo;
  private SetHoodToAngleCmd setHoodToAngle;


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //Controllers
    driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    shooterController = new XboxController(Constants.SHOOTER_CONTROLLER);

    //Initializing all DriveTrain Components
    driveTrain = new DriveTrainSub();
    limelight = new LimelightSub();
    //shiftIn.addRequirements(driveTrain);
    //shiftOut.addRequirements(driveTrain);

    //Intializing USBcamera
    camera = new VisionSub();

    joystickDrive = new JoystickDriveCmd(driveTrain, limelight);
    joystickDrive.addRequirements(driveTrain, limelight);
    driveTrain.setDefaultCommand(joystickDrive);

    
    //Initializing all Intake Components
    intake = new IntakeSub();
    //commands are constructed in button bindings
    
    //Intitializing all ShooterClimb Components
    shooterClimb = new ShooterClimbSub();    // MRNOTE Servo conflict
    shootShooters = new ShootShootersCmd(shooterClimb);
    shootShooters.addRequirements(shooterClimb);


    climbStage1 = new ClimbStage1(shooterClimb);
    climbStage1.addRequirements(shooterClimb);
    climbStage2 = new ClimbStage2(shooterClimb);
    climbStage2.addRequirements(shooterClimb);


    //Initializing sonar sub

    //Initializing servo sub
    //servo = new ServoSub();  // MRNOTE Servo conflict
    //setHoodToAngle = new SetHoodToAngleCmd(servo); //don't know if this needs to be here

    //Inititalizing all Indexer Components
    indexer = new IndexerSub();
    //commands constructed in button bindings

    //Indexer beam break triggers
    //Each trigger represents an individual status, which determines which indexer should be ran

    //BEAMBREAKS:

    Trigger beamBreakDetector = new Trigger(() -> indexer.getBreakStatus());
    beamBreakDetector.whileActiveContinuous(new IndexBallCmd(indexer));

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
    deployIntakeLeftButton.whenPressed(new DeployIntakeCmd(intake));
   
    JoystickButton retractIntakeLeftButton = new JoystickButton(shooterController,XboxController.Button.kLeftBumper.value);
    retractIntakeLeftButton.whenPressed(new RetractIntakeCmd(intake));
    
    /* Shooter */
    JoystickButton shootShootersButton = new JoystickButton(shooterController, XboxController.Button.kB.value);
    shootShootersButton.whileHeld(new ShootShootersCmd(shooterClimb));

    JoystickButton fenderShotButton = new JoystickButton(shooterController, XboxController.Button.kA.value);
    fenderShotButton.whileHeld(new FenderShotCmd(shooterClimb));

    JoystickButton rejectBallButton = new JoystickButton(shooterController, XboxController.Button.kX.value);
    rejectBallButton.whileHeld(new RejectBallCmd(shooterClimb));

    JoystickButton indexButton = new JoystickButton(shooterController, XboxController.Button.kY.value);
    indexButton.whileHeld(new IndexBallCmd(indexer));

    JoystickButton servoButton = new JoystickButton(shooterController, XboxController.Button.kStart.value);
    servoButton.whenPressed(new SetHoodToAngleCmd(servo, 0));

    /* Shifting */
    JoystickButton shiftInButton = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
    shiftInButton.whenPressed(new ShiftInCmd(driveTrain));

    JoystickButton shiftOutButton = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
    shiftOutButton.whenPressed(new ShiftOutCmd(driveTrain));
  
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
