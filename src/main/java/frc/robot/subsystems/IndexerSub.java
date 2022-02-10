// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSub extends SubsystemBase {
  private WPI_TalonSRX indexerBelt1;
  private WPI_TalonSRX indexerBelt2;
  private WPI_TalonFX indexerKick1;
  private WPI_TalonFX indexerKick2;

  private DigitalInput bbBottom1;
  private DigitalInput bbTop1;
  private DigitalInput bbBottom2;
  private DigitalInput bbTop2;
  private DigitalInput bbBottom3;
  private DigitalInput bbTop3;
 
  /** Creates a new IndexerSub. */
  public IndexerSub() {
    indexerBelt1 = new WPI_TalonSRX(Constants.INDEX_BELT_1_TALON);
    indexerBelt2 = new WPI_TalonSRX(Constants.INDEX_BELT_2_TALON);
    indexerKick1 = new WPI_TalonFX(Constants.INDEX_KICK_1_TALON);
    indexerKick2 = new WPI_TalonFX(Constants.INDEX_KICK_2_TALON);

    bbBottom1 = new DigitalInput(Constants.BB_BOTTOM_1_CHANNEL);
    bbBottom1 = new DigitalInput(Constants.BB_TOP_1_CHANNEL);
    bbBottom1 = new DigitalInput(Constants.BB_BOTTOM_2_CHANNEL);
    bbBottom1 = new DigitalInput(Constants.BB_TOP_2_CHANNEL);
    bbBottom1 = new DigitalInput(Constants.BB_BOTTOM_3_CHANNEL);
    bbBottom1 = new DigitalInput(Constants.BB_TOP_3_CHANNEL);
  }

  public void indexBall1(double speed) {
    indexerBelt1.set(speed);
  }

  public void indexBall2(double speed) {
    indexerBelt2.set(speed);
  }

  public void indexStop1() {
    indexerBelt1.set(0);
  }

  public void indexStop2() {
    indexerBelt2.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
