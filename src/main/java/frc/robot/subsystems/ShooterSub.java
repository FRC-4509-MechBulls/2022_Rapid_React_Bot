// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {
  // shooter1 is spun by pressing the A button
  WPI_TalonFX shooter1;

  // shooter2, shooter3, and shooter4 are spun by pressing the B button
  WPI_TalonFX shooter2;
  WPI_TalonSRX shooter3;
 

  WPI_TalonSRX hood;
  private final double kHoodTick2Degree = 360 / 4096 * 26 / 42 * 18 / 60 * 18 / 84;

  /** Creates a new ExampleSubsystem. */
  public ShooterSub() {
    shooter1 = new WPI_TalonFX(Constants.SHOOTER_FALCON_1);
    shooter1.setInverted(false);

    shooter2 = new WPI_TalonFX(Constants.SHOOTER_FALCON_2);
    shooter2.setInverted(false);
    shooter3 = new WPI_TalonSRX(Constants.SHOOTER_TALON);
    shooter3.setInverted(true);
   

    hood = new WPI_TalonSRX(Constants.HOOD_TALON);
    // configuring which encoder is being used - ctre mag encoder, relative
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 10);
    hood.setSensorPhase(false); // decides which direction is positive

    // reset encoders to zero
    hood.setSelectedSensorPosition(0, 0 , 10);

    // makes sure shooter2, shooter3, and shooter4 all spin together, 
    // however shooter2 spins in the opposite direction
    shooter3.follow(shooter2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Encoder Value", hood.getSelectedSensorPosition() * kHoodTick2Degree);
  }

  // spins shooter1
  public void shootGroup1(double speed) {
    shooter1.set(TalonFXControlMode.PercentOutput, speed);
  }

  // spins shooter2, shooter3, and shooter4
  public void shootGroup2(double speed1, double speed2) {
    shooter2.set(TalonFXControlMode.PercentOutput, speed1);
  }

  public void adjustHood() {
    System.out.println("Sensor Vel:" + hood.getSelectedSensorVelocity());
    System.out.println("Sensor Pos:" + hood.getSelectedSensorPosition());
    System.out.println("Out %" + hood.getMotorOutputPercent());
  }

  // stops shooter1
  public void stop1() {
    shooter1.set(TalonFXControlMode.PercentOutput, 0);
  }

  // stops shooter2, shooter3, and shooter4
  public void stop2() {
    shooter2.set(TalonFXControlMode.PercentOutput, 0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

