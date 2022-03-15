// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterClimbSub;

public class AutoShoot extends CommandBase {

  ShooterClimbSub shoot;
  private boolean isFinished = false;
  Timer timer;
 
  /** Creates a new AutoShoot. */
  public AutoShoot(ShooterClimbSub scs) {
    shoot = scs;
    addRequirements(shoot);

    timer = new Timer();
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

    // While the command runs for 3 seconds, call fender shoot (arbitrary amount can change)
    if (timer.get() < 3) {
      shoot.fenderShot();
    } else {
      isFinished = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shoot.stop();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
