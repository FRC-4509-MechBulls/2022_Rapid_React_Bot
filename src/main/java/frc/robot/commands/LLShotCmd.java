// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.Console;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSub;

public class LLShotCmd extends CommandBase {
  ShooterSub shooter;
  double servoPosition; 
  double shootVelocity;
  double topVelocity;
  /** Creates a new LLShotCmd. */
  public LLShotCmd(ShooterSub s) {
    shooter = s;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    servoPosition = Math.floor(0.0093*(Math.pow(Math.abs(shooter.getY()-30), 2.2595))+38.8705);
    shootVelocity = 100*(Math.floor(65.1796+1.3836*(Math.pow(10,-13))*Math.pow(2.7182,0.6367*Math.abs(shooter.getY()-30))));
    topVelocity = -1*(Math.floor(shootVelocity*1.3333));
    System.out.println("initialize");
    System.out.println(servoPosition);
    shooter.LLShotServo(servoPosition);
  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println(shootVelocity);
    // System.out.println(topVelocity);
    shooter.LLShot(shootVelocity, topVelocity);
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
