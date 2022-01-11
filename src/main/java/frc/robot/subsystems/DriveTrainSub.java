// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSub extends SubsystemBase {
  //Declaring all MotorControllers, MotorControllerGroups, and XboxController
  DifferentialDrive drive;
  MotorController mc_frontLeft;
  MotorController mc_frontRight;
  MotorController mc_backLeft;
  MotorController mc_backRight;
  MotorControllerGroup left;
  MotorControllerGroup right;
  XboxController driverJoystick;
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
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  //Creating a Command to drive and steer with the controller
  public void joystickDrive(XboxController controller, double speed) {
    drive.arcadeDrive(((controller.getRawAxis(Constants.RIGHT_TRIGGER))-(controller.getRawAxis(Constants.LEFT_TRIGGER)))*speed, controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*-speed);
  }
}
