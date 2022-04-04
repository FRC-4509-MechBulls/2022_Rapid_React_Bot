// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.TwoBallAuto;
import frc.robot.commands.AutoDriveCmd;
import frc.robot.commands.AutoDriveTwo;
import frc.robot.commands.AutoIndexCmd;
import frc.robot.commands.AutoShoot;
//import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DeployIntakeCmd;
import frc.robot.commands.ExtendClimbCmd;
import frc.robot.commands.FenderShotCmd;
import frc.robot.commands.HighBarClimbCmd;
import frc.robot.commands.IndexBackwardsCmd;
import frc.robot.commands.IndexBallCmd;
import frc.robot.commands.JoystickDriveCmd;
import frc.robot.commands.LLShotCmd;
import frc.robot.commands.OneBallAuto;
import frc.robot.commands.OneBallAutoShot;
import frc.robot.commands.RejectBallCmd;
import frc.robot.commands.RetractClimbCmd;
import frc.robot.commands.RetractIntakeCmd;
import frc.robot.commands.ServoFarShotCmd;
import frc.robot.commands.ServoFenderCmd;
import frc.robot.commands.ServoRejectBallCmd;
import frc.robot.subsystems.ClimbSub;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.commands.ShiftLowCmd;
import frc.robot.commands.ShiftHighCmd;
import frc.robot.commands.FarShotCmd;
import frc.robot.commands.StopIndexAndShootCmd;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ShooterSub;
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
  private ShooterSub shooterClimb;
  private FarShotCmd shootShooters;
  private ClimbSub climb;

  //DriveTrain
  private ShiftLowCmd shiftIn;
  private ShiftHighCmd shiftOut;
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

  //auto
  private TwoBallAuto autoTwoBall;
  private OneBallAuto autoOneBall;
  private AutoShoot autoShoot;
  private AutoDriveCmd autoDriveOne;
  private AutoDriveTwo autoDriveTwo;
  private AutoIndexCmd autoIndex;
  SendableChooser<Command> chooser = new SendableChooser<>();
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    //Controllers
    driverController = new XboxController(Constants.DRIVER_CONTROLLER);
    shooterController = new XboxController(Constants.SHOOTER_CONTROLLER);

    //Initializing all DriveTrain Components
    driveTrain = new DriveTrainSub();
    limelight = new LimelightSub();

    //Intializing USBcamera
    camera = new VisionSub();  //MRNOTE caused simulation to crash

    joystickDrive = new JoystickDriveCmd(driveTrain, limelight);
    joystickDrive.addRequirements(driveTrain, limelight);
    driveTrain.setDefaultCommand(joystickDrive);

    
    //Initializing all Intake Components
    intake = new IntakeSub();
    deployIntake = new DeployIntakeCmd(intake);
    retractIntake = new RetractIntakeCmd(intake);
    //commands are constructed in button bindings
    
    //Intitializing all ShooterClimb Components
    shooterClimb = new ShooterSub();    // MRNOTE Servo conflict
    shootShooters = new FarShotCmd(shooterClimb);
    shootShooters.addRequirements(shooterClimb);
    
    climb = new ClimbSub();

    indexer = new IndexerSub();
    indexBall1 = new IndexBallCmd(indexer);


    //Initializing auto
    autoShoot = new AutoShoot(shooterClimb);
    autoShoot.addRequirements(shooterClimb);
    autoDriveOne = new AutoDriveCmd(driveTrain, intake);
    autoDriveOne.addRequirements(driveTrain, intake);
    autoDriveTwo = new AutoDriveTwo(driveTrain, intake);
    autoDriveTwo.addRequirements(driveTrain, intake);
    autoIndex = new AutoIndexCmd(indexer);
    autoTwoBall = new TwoBallAuto(driveTrain, limelight, intake, indexer, shooterClimb);
    autoOneBall = new OneBallAuto(driveTrain, indexer, intake, shooterClimb);
    
    chooser.addOption("TwoBall", autoTwoBall);
    chooser.addOption("OneBall", autoOneBall);
    SmartDashboard.putData("Auto Chooser", chooser);

    //Initializing servo sub
    //servo = new ServoSub();  // MRNOTE Servo conflict
    //setHoodToAngle = new SetHoodToAngleCmd(servo); //don't know if this needs to be here

    //Inititalizing all Indexer Components
    
    //commands constructed in button bindings

    //Indexer beam break triggers
    //Each trigger represents an individual status, which determines which indexer should be ran

    // //BEAMBREAKS:

    //  Trigger beamBreakDetector1 = new Trigger(() -> indexer.getBreakStatusRun());
    //  beamBreakDetector1.whileActiveContinuous(new BBIndexBallCmd(indexer));
    //  Trigger beamBreakDetector2 = new Trigger(() -> indexer.getBreakStatusRun2());
    //  beamBreakDetector2.whileActiveOnce(new StopIndexAndShootCmd(indexer, shooterClimb));

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
    shootShootersButton.whileHeld(new FarShotCmd(shooterClimb));

    JoystickButton fenderShotButton = new JoystickButton(shooterController, XboxController.Button.kA.value);
    fenderShotButton.whileHeld(new FenderShotCmd(shooterClimb));

    JoystickButton rejectBallButton = new JoystickButton(shooterController, XboxController.Button.kX.value);
    rejectBallButton.whileHeld(new RejectBallCmd(shooterClimb));

    JoystickButton indexButton = new JoystickButton(shooterController, XboxController.Button.kY.value);
    indexButton.whileHeld(new IndexBallCmd(indexer));

    JoystickButton indexBackwardsButton = new JoystickButton(shooterController, XboxController.Button.kStart.value);
    indexBackwardsButton.whileHeld(new IndexBackwardsCmd(indexer));

    JoystickButton llShotButton = new JoystickButton(shooterController, XboxController.Button.kBack.value);
    llShotButton.whileHeld(new LLShotCmd(shooterClimb));

    // JoystickButton servoFender = new JoystickButton(shooterController, XboxController.Button.kBack.value);
    // servoFender.whenPressed(new ServoFenderCmd(shooterClimb));
    
    // JoystickButton servoFarShot = new JoystickButton(shooterController, XboxController.Button.kStart.value);
    // servoFarShot.whenPressed(new ServoFarShotCmd(shooterClimb));



    /* Driver */
     JoystickButton extendClimbButton = new JoystickButton(driverController, XboxController.Button.kRightBumper.value);
     extendClimbButton.whenPressed(new ExtendClimbCmd(climb));

     JoystickButton retractClimbButton = new JoystickButton(driverController, XboxController.Button.kLeftBumper.value);
     retractClimbButton.whenPressed(new RetractClimbCmd(climb));

     JoystickButton shiftLowButton = new JoystickButton(driverController, XboxController.Button.kX.value);
     shiftLowButton.toggleWhenPressed(new ShiftLowCmd(driveTrain));

     JoystickButton shiftHighButton = new JoystickButton(driverController, XboxController.Button.kB.value);
     shiftHighButton.whenPressed(new ShiftHighCmd(driveTrain));

     JoystickButton highBarButton = new JoystickButton(driverController, XboxController.Button.kStart.value);
     highBarButton.whenPressed(new HighBarClimbCmd(climb));
  }
  

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand all run in autonomous
    return chooser.getSelected();
  }
}
