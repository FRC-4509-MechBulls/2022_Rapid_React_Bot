// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ShooterSub;

public class Shoot1Cmd extends CommandBase {
  private ShooterSub shooter;
  /** Creates a new Shoot1. */
  public Shoot1Cmd(ShooterSub s) {
    shooter = s;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.shootGroup1(Constants.SHOOTER_SPEED_1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop1();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}