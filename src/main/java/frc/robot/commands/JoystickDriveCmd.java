// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.LimelightSub;

public class JoystickDriveCmd extends CommandBase {
  private DriveTrainSub driveTrain;
  private LimelightSub limelight;

  /** Creates a new joystickDrive. */
  public JoystickDriveCmd(DriveTrainSub dt, LimelightSub l) {
    // Use addRequirements() here to declare subsystem dependencies.
    driveTrain = dt;
    limelight = l;

    addRequirements(driveTrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (RobotContainer.driverController.getYButton()) {
      if (limelight.isTargetValid()) {
        // if target is detected, run aim command
        driveTrain.aimLimelight(limelight.getDistanceAdjust(), limelight.getSteer());
        
      } else {
        // robot turns until target is detected
        driveTrain.seekLimelight();
      }
    } else {
      // if a button is not pressed, run drive command as usual
      driveTrain.joystickDrive(RobotContainer.driverController, Constants.DRIVETRAIN_SPEED);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
