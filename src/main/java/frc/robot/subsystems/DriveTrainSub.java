// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {
  //Declaring all MotorControllers, MotorControllerGroups, and XboxController
  private DifferentialDrive drive;
  private MotorController mc_frontLeft;
  private MotorController mc_frontRight;
  private MotorController mc_backLeft;
  private MotorController mc_backRight;
  private MotorControllerGroup left;
  private MotorControllerGroup right;

  private DoubleSolenoid shifter;

  private PneumaticsModuleType REVPH;

  //private XboxController driverJoystick;

  /** Creates a new DriveTrain. */
  public DriveTrainSub() {
    //Initializing all objects, as well as the DifferentialDrive
    mc_backLeft = new WPI_TalonFX(Constants.BACK_LEFT_TALON);
    mc_backLeft.setInverted(false);
    mc_backRight = new WPI_TalonFX(Constants.BACK_RIGHT_TALON);
    mc_backRight.setInverted(false);
    mc_frontLeft = new WPI_TalonFX(Constants.FRONT_LEFT_TALON);
    mc_frontLeft.setInverted(false);
    mc_frontRight = new WPI_TalonFX(Constants.FRONT_RIGHT_TALON);
    mc_frontRight.setInverted(false);
    
    left = new MotorControllerGroup(mc_frontLeft, mc_backLeft);
    right = new MotorControllerGroup(mc_frontRight, mc_backRight);

    drive = new DifferentialDrive(left, right);

    shifter = new DoubleSolenoid(REVPH, Constants.SHIFTER_1_FORWARD_CHANNEL, Constants.SHIFTER_2_REVERSE_CHANNEL);
  }

  //Creating a Command to drive and steer with the controller
  public void joystickDrive(XboxController controller, double speed) {
    double xSpeed = ((controller.getRawAxis(Constants.RIGHT_TRIGGER))-(controller.getRawAxis(Constants.LEFT_TRIGGER)))*speed;
    double zRotation = controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*-speed;
    drive.arcadeDrive(xSpeed, zRotation);
  }

  //high gear?
  public void shiftIn() {
    shifter.set(DoubleSolenoid.Value.kReverse);
  }

  //low gear?
  public void shiftOut() {
    shifter.set(DoubleSolenoid.Value.kForward);
  }

  public void aimLimelight(double driveCommand, double steerCommand) {
    drive.arcadeDrive(driveCommand, steerCommand);
  }

  public void stop() {
    drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
