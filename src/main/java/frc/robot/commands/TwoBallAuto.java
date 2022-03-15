// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrainSub;
import frc.robot.subsystems.IndexerSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.LimelightSub;
import frc.robot.subsystems.ShooterClimbSub;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoBallAuto extends SequentialCommandGroup {
  /** Creates a new Autonomous. */
  public TwoBallAuto(DriveTrainSub dt, LimelightSub l, IntakeSub i, IndexerSub idx, ShooterClimbSub scs) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new OneBallAuto(scs, idx), new StopIndexAndShootCmd(idx, scs), new AutoDriveCmd(dt, i), new StopDriveCmd(dt), new AutoDriveTwo(dt, i), new OneBallAuto(scs, idx), new StopIndexAndShootCmd(idx, scs));
  }
}
