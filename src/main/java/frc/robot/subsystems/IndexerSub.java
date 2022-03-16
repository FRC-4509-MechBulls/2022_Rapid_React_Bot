// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project. Sussy Mogus mogus sussy hehehehehehehe Ian sussy mogus

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSub extends SubsystemBase {
  private WPI_TalonSRX indexerBeltLeft;
  private WPI_TalonSRX indexerBeltRight;

  private DigitalInput bbFirst;
  private DigitalInput bbSecond;
 
  /** Creates a new IndexerSub. */
  public IndexerSub() {
    indexerBeltLeft = new WPI_TalonSRX(Constants.INDEX_BELT_LEFT);
    indexerBeltLeft.setInverted(true);
    indexerBeltLeft.setNeutralMode(NeutralMode.Brake);
    indexerBeltRight = new WPI_TalonSRX(Constants.INDEX_BELT_RIGHT);
    indexerBeltRight.setInverted(true);
    indexerBeltRight.setNeutralMode(NeutralMode.Brake);

    bbFirst = new DigitalInput(Constants.BB_1_CHANNEL); //maybe 4
  }

  
  public void indexBall() {
    indexerBeltRight.set(Constants.INDEXER_SPEED);
    indexerBeltLeft.set(Constants.INDEXER_SPEED);
  }

  public void indexStop() {
    indexerBeltLeft.set(0);
    indexerBeltRight.set(0);
  }

  public boolean getBreakStatusRun() {
    if (!bbFirst.get()) {
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("BeamBreak1", !bbFirst.get());
    //SmartDashboard.putBoolean("BeamBreak2", !bbSecond.get());
  }
}
