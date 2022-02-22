// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ServoSub;

public class SetHoodToAngleCmd extends CommandBase {
  private ServoSub servo;
  private double angle;
  /** Creates a new SetHoodToAngle. */
  public SetHoodToAngleCmd(ServoSub s) {
    servo = s;
    angle = 180;
    addRequirements(servo);
  }
  public SetHoodToAngleCmd(ServoSub s, double inputAngle) {
    servo = s;
    angle = inputAngle;
    addRequirements(servo);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    servo.setAngle(angle);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(servo.getServoAngle() == 25)
      return true;
    else
      return false;
  }
}
