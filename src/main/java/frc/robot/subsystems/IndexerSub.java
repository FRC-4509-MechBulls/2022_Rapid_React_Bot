// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSub extends SubsystemBase {
  WPI_TalonSRX indexerBelt1;
  WPI_TalonSRX indexerBelt2;
  WPI_TalonFX indexerKick1;
  WPI_TalonFX indexerKick2;
  /** Creates a new IndexerSub. */
  public IndexerSub() {
    indexerBelt1 = new WPI_TalonSRX(Constants.INDEX_BELT_1_TALON);
    indexerBelt2 = new WPI_TalonSRX(Constants.INDEX_BELT_2_TALON);
    indexerKick1 = new WPI_TalonFX(Constants.INDEX_KICK_1_TALON);
    indexerKick2 = new WPI_TalonFX(Constants.INDEX_KICK_2_TALON);
  }

  public void indexBall1(double speed) {
    indexerBelt1.set(speed);
  }

  public void indexBall2(double speed) {
    indexerBelt2.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
