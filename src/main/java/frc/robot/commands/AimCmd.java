// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.ShooterSub;

public class AimCmd extends CommandBase {
  private ShooterSub shooter;
  private DriveTrainSub driveTrain;
  /** Creates a new AimCmd. */
  public AimCmd(ShooterSub s, DriveTrainSub dt) {
    shooter = s;
    driveTrain = dt;
    addRequirements(shooter, driveTrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (shooter.isTargetValid()) {
      //     // if target is detected, run aim command
      driveTrain.aimLimelight(/*limelight.getDistanceAdjust()*/0, shooter.getSteer());
          
    } else {
    // robot turns until target is detected
      driveTrain.seekLimelight();
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
