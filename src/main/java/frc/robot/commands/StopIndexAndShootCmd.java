// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.ShooterSub;

public class StopIndexAndShootCmd extends CommandBase {
  IndexerSub indexer;
  ShooterSub shooter;
  private boolean finish = false;
  /** Creates a new StopIndexCmd. */
  public StopIndexAndShootCmd(IndexerSub idx, ShooterSub scs) {
    indexer = idx;
    shooter = scs;
    addRequirements(shooter);
    //addRequirements(indexer);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    indexer.indexStop();
    shooter.stop();
    finish = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
