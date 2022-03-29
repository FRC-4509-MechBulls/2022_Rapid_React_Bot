// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IndexerSub;

public class AutoIndexCmd extends CommandBase {
  IndexerSub indexer;
  Timer timer;
  private boolean finish = false;
  /** Creates a new AutoIntake. */
  public AutoIndexCmd(IndexerSub idx) {
    indexer = idx;
    timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (timer.get() < 1.5){
      indexer.indexBall(0.4); // this was the speed before I changed the speed in the method in the subsystem
      //indexer.indexBall(Constants.INDEXER_SPEED);
    }
    else{
      finish = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    indexer.indexStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
