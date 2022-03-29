// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {
  //#region Declarations!
  // Declaring all MotorControllers, MotorControllerGroups, and XboxController
  private MotorController mc_leftFront;
  private MotorController mc_leftBack;

  // private WPI_TalonFX leftFront;
  // private WPI_TalonFX leftBack;

  // private WPI_TalonFX rightFront;
  // private WPI_TalonFX rightBack;

  private MotorController mc_rightFront;
  private MotorController mc_rightBack;

  private MotorControllerGroup left;
  private MotorControllerGroup right;

  private DifferentialDrive drive;

  private Solenoid shifter;
  private String shiftStatus; 
  Timer timer;
  double speed;
  SlewRateLimiter filter = new SlewRateLimiter(0.96);

 // private PneumaticsModuleType REVPH;

  //private XboxController driverJoystick;
  //#endregion

  /** Creates a new DriveTrain. */
  public DriveTrainSub() {
    //Initializing all objects, as well as the DifferentialDrive
    mc_leftFront = new WPI_TalonFX(Constants.LEFT_FRONT_TALON);
    mc_leftFront.setInverted(false);
    mc_leftBack = new WPI_TalonFX(Constants.LEFT_BACK_TALON);
    mc_leftBack.setInverted(false);

    // leftFront = new WPI_TalonFX(Constants.LEFT_FRONT_TALON);
    // leftFront.setInverted(false);
    // leftBack = new WPI_TalonFX(Constants.LEFT_BACK_TALON);
    // leftBack.setInverted(false);

    mc_rightFront = new WPI_TalonFX(Constants.RIGHT_FRONT_TALON);
    mc_rightFront.setInverted(true);
    mc_rightBack = new WPI_TalonFX(Constants.RIGHT_BACK_TALON);
    mc_rightBack.setInverted(true);

    // rightFront = new WPI_TalonFX(Constants.RIGHT_FRONT_TALON);
    // rightFront.setInverted(true);
    // rightBack = new WPI_TalonFX(Constants.RIGHT_BACK_TALON);
    // rightBack.setInverted(true);

    // leftBack.follow(leftFront);
    // rightBack.follow(rightFront);

    // leftFront.configOpenloopRamp(2);
    // rightFront.configOpenloopRamp(2);
    
    left = new MotorControllerGroup(mc_leftFront, mc_leftBack);
    right = new MotorControllerGroup(mc_rightFront, mc_rightBack);

    drive = new DifferentialDrive(left, right);

    //drive = new DifferentialDrive(leftFront, rightFront);

    shifter = new Solenoid(PneumaticsModuleType.REVPH, Constants.SHIFTER_CHANNEL);
  }

  //Creating a Command to drive and steer with the controller
  public void joystickDrive(XboxController controller, double speed) {
    double zRotation;
    double xSpeed = ((controller.getRightTriggerAxis())-(controller.getLeftTriggerAxis()))*-speed;
    if (Math.abs(controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)) > 0.12) {
      zRotation = (controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*-speed) * 0.7;
    } else {
      zRotation = 0.0;
    }
    drive.arcadeDrive(filter.calculate(xSpeed), zRotation);
    drive.feed();
  }

  public void joystickDriveInverted(XboxController controller, double speed) {
    double zRotation;
    double xSpeed = ((controller.getRightTriggerAxis())-(controller.getLeftTriggerAxis()))*speed;
    if (Math.abs(controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)) > 0.12) {
      zRotation = controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*-speed;
    } else {
      zRotation = 0.0;
    }
    drive.arcadeDrive(xSpeed, zRotation);
    drive.feed();
  }
  
  public void autoDriveTwo(){
    drive.tankDrive(Constants.AUTO_SPEED, Constants.AUTO_SPEED);
  }
  
  public void autoDrive(){
     drive.tankDrive(Constants.AUTO_SPEED*-1, Constants.AUTO_SPEED*-1);
  }

  public void autoTurn(){
    drive.tankDrive(Constants.AUTO_SPEED, Constants.AUTO_SPEED * -1);
  }

  // high gear?
  public void shiftIn() {
    shifter.set(false);  
    shiftStatus = "Low Gear";
  }

  //low gear?
  public void shiftOut() {
    shifter.set(true);
    shiftStatus = "High Gear";
  }

  public void aimLimelight(double driveCommand, double steerCommand) {
    drive.arcadeDrive(driveCommand, steerCommand);
  }

  public void seekLimelight() {
    drive.arcadeDrive(0, 0.35);
  }

  public void stop() {
    drive.stopMotor();
  }

  @Override
  public void periodic() {
    //SmartDashboard.putString("Drivetrain Status", shiftStatus);
    // This method will be called once per scheduler run
  }
}
