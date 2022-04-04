// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;

public class RetreatDriveCmd extends CommandBase {
  DriveTrainSub drivetrain;
  Timer timer;
  private boolean finish = false;
  /** Creates a new RetreatDriveCmd. */
  public RetreatDriveCmd(DriveTrainSub dt) {
    drivetrain = dt;
    timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    while (timer.get() < 3)
    {
      drivetrain.autoDriveRetreat();
    }
    finish = true;
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
    return finish;
  }
}
