// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSub extends SubsystemBase {
  private WPI_TalonSRX indexerBeltLeft;
  private WPI_TalonSRX indexerBeltRight;
  private WPI_TalonFX indexerKickLeft;
  private WPI_TalonFX indexerKickRight;

  private DigitalInput bb1;
  //private DigitalInput bbTop1;
  private DigitalInput bb2;
  //private DigitalInput bbTop2;
  private DigitalInput bb3;
  //private DigitalInput bbTop3;
 
  /** Creates a new IndexerSub. */
  public IndexerSub() {
    indexerBeltLeft = new WPI_TalonSRX(Constants.INDEX_BELT_LEFT_TALON);
    indexerBeltRight = new WPI_TalonSRX(Constants.INDEX_BELT_RIGHT_TALON);
    indexerKickLeft = new WPI_TalonFX(Constants.INDEX_KICK_LEFT_TALON);
    indexerKickRight = new WPI_TalonFX(Constants.INDEX_KICK_RIGHT_TALON);

    bb1 = new DigitalInput(Constants.BB_1_CHANNEL);
    //bbTop1 = new DigitalInput(Constants.BB_TOP_1_CHANNEL);
    bb2 = new DigitalInput(Constants.BB_2_CHANNEL);
    //bbTop2 = new DigitalInput(Constants.BB_TOP_2_CHANNEL);
    bb3 = new DigitalInput(Constants.BB_3_CHANNEL);
    //bbTop3 = new DigitalInput(Constants.BB_TOP_3_CHANNEL);
  }

  public void indexBallLeft(double speed) {
    indexerBeltLeft.set(speed);
  }

  public void indexBallRight(double speed) {
    indexerBeltRight.set(speed);
  }

  public void indexStopLeft() {
    indexerBeltLeft.set(0);
  }

  public void indexStopRight() {
    indexerBeltRight.set(0);
  }

  //if only beambreak 1 is brokekn
  public boolean getBreakStatus1() {
    if (!bb1.get() && bb2.get() && bb3.get()) {
      return true;
    }
    return false;
  }

  //if beambreak 1 and 2 broken, but not 3
  public boolean getBreakStatus2() {
    if (!bb1.get() && !bb2.get() && bb3.get()) {
      return true;
    }
    return false;
  }

  //if all three beambreaks are broken
  public boolean getBreakStatus3() {
    return true; //logic still being worked on
  }

  //if beambreaks 1 and 3 are broken
  public boolean getBreakStatus4() {
    if (!bb1.get() && bb2.get() && !bb3.get()) {
      return true;
    }
    return false;
  }

  //if beambreaks 2 and 3 are broken
  public boolean getBreakStatus5() {
    if (bb1.get() && !bb2.get() && !bb3.get()) {
      return true;
    }
    return false;
  }

  //if only beambreak3 is broken
  public boolean getBreakStatus6() {
    if (bb1.get() && bb2.get() && !bb3.get()) {
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("BeamBreak1", !bb1.get());
    SmartDashboard.putBoolean("BeamBreak2", !bb2.get());
    SmartDashboard.putBoolean("BeamBreak3", !bb3.get());
  }
}
