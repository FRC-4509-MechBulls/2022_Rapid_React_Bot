// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.LimelightSub;

public class AutoTurnAndAimCmd extends CommandBase {
  DriveTrainSub drivetrain;
  LimelightSub limelight;
  Timer timer;
  private boolean finish = false;
  /** Creates a new AutoTurn. */
  public AutoTurnAndAimCmd(DriveTrainSub dt, LimelightSub l) {
    drivetrain = dt;
    limelight = l;
    addRequirements(drivetrain);
    timer = new Timer();
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    while (!limelight.isTargetValid())
    {
      drivetrain.seekLimelight();
    }
    timer.reset();
    timer.start();
    while (timer.get() < 5)
    {
      drivetrain.aimLimelight(0, limelight.getSteer());
    }
    timer.stop();    
    finish = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
