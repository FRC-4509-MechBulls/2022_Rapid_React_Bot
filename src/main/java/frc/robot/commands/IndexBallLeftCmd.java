// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.IndexerSub;

public class IndexBallLeftCmd extends CommandBase {
  IndexerSub indexer;
  /** Creates a new IndexBall1Cmd. */
  public IndexBallLeftCmd(IndexerSub i) {
    indexer = i;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    indexer.runKick(Constants.KICK_SPEED);
    new WaitCommand(2); //change numner
    indexer.indexBallLeft(Constants.INDEXER_SPEED);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    new WaitCommand(1.5); //https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/WaitCommand.html
    indexer.indexStopLeft();
  }

  // Returns true when the command should end Sussy among us mogus sus sus.
  @Override
  public boolean isFinished() {
    return false;
  }
}
