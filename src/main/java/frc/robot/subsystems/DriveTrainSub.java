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
  private MotorController mc_leftFront;
  private MotorController mc_leftBack;

  private MotorController mc_rightFront;
  private MotorController mc_rightBack;

  private MotorControllerGroup left;
  private MotorControllerGroup right;

  private DifferentialDrive drive;

  private DoubleSolenoid shifter;

 // private PneumaticsModuleType REVPH;

  //private XboxController driverJoystick;

  /** Creates a new DriveTrain. */
  public DriveTrainSub() {
    //Initializing all objects, as well as the DifferentialDrive
    mc_leftFront = new WPI_TalonFX(Constants.LEFT_FRONT_TALON);
    mc_leftFront.setInverted(false);
    mc_leftBack = new WPI_TalonFX(Constants.LEFT_BACK_TALON);
    mc_leftBack.setInverted(false);

    mc_rightFront = new WPI_TalonFX(Constants.RIGHT_FRONT_TALON);
    mc_rightFront.setInverted(true);
    mc_rightBack = new WPI_TalonFX(Constants.RIGHT_BACK_TALON);
    mc_rightBack.setInverted(true);
    
    left = new MotorControllerGroup(mc_leftFront, mc_leftBack);
    right = new MotorControllerGroup(mc_rightFront, mc_rightBack);

    drive = new DifferentialDrive(left, right);

    //shifter = new DoubleSolenoid(REVPH, Constants.SHIFTER_1_FORWARD_CHANNEL, Constants.SHIFTER_2_REVERSE_CHANNEL);
  }

  //Creating a Command to drive and steer with the controller
  public void joystickDrive(XboxController controller, double speed) {
    double zRotation;
    double xSpeed = ((controller.getRightTriggerAxis())-(controller.getLeftTriggerAxis()))*-speed;
    if (Math.abs(controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)) > 0.05) {
      zRotation = controller.getRawAxis(Constants.XBOX_LEFT_X_AXIS)*-speed;
    } else {
      zRotation = 0.0;
    }
    drive.arcadeDrive(xSpeed, zRotation);
  }

  public void autoDrive(){
     drive.tankDrive(-0.3, -0.3);
  }

  public void autoTurn(){
    drive.tankDrive(-0.3, 0.3);
  }

  //high gear?
  //  public void shiftIn() {
  //   shifter.set(DoubleSolenoid.Value.kReverse);
  //  }

  //low gear?
  //  public void shiftOut() {
  //   shifter.set(DoubleSolenoid.Value.kForward);
  //  }

  public void aimLimelight(double driveCommand, double steerCommand) {
    drive.arcadeDrive(driveCommand, steerCommand);
    
  }

  public void seekLimelight() {
    drive.arcadeDrive(0, 0.38);
  }

  public void stop() {
    drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
