// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;

public class AutoIntakeAndIndexCmd extends CommandBase {
  IntakeSub intake;
  IndexerSub indexer;
  /** Creates a new AutoIntake. */
  public AutoIntakeAndIndexCmd(IntakeSub i, IndexerSub idx) {
    intake = i;
    indexer = idx;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake, indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.deployIntake();
   // indexer logic goes here
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
