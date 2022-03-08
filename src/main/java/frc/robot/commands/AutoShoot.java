// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ShooterClimbSub;

public class AutoShoot extends CommandBase {
  ShooterClimbSub shoot;
  LimelightSub limelight;
  /** Creates a new AutoShoot. */
  public AutoShoot(ShooterClimbSub scs, LimelightSub l) {
    shoot = scs;
    limelight = l;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shoot, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoot.shootShooters();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
