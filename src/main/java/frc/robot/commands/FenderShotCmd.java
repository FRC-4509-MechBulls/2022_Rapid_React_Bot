// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSub;

public class FenderShotCmd extends CommandBase {
  /** Creates a new FenderShotCmd. */
  ShooterSub shooter;
  public FenderShotCmd(ShooterSub sc) {
    shooter = sc;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.SetServoFender();
  }

  @Override
  public void execute(){
    //shooter.SetServoFender();
    shooter.fenderShot();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
