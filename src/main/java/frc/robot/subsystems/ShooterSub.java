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
  WPI_TalonFX shooterWheel;
  WPI_TalonFX shooterWheelInverted;

  // shooter2, shooter3, and shooter4 are spun by pressing the B button
  WPI_TalonFX topWheel;
 

  WPI_TalonSRX hood;
  private final double kHoodTick2Degree = 360 / 4096 * 26 / 42 * 18 / 60 * 18 / 84;

  /** Creates a new ExampleSubsystem. */
  public ShooterSub() {
    shooterWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_1);
    shooterWheel.setInverted(false);
    shooterWheelInverted = new WPI_TalonFX(Constants.SHOOTER_FALCON_2);
    shooterWheelInverted.setInverted(true);

    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);
    topWheel.setInverted(false);
   
    hood = new WPI_TalonSRX(Constants.HOOD_TALON);
    // configuring which encoder is being used - ctre mag encoder, relative
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 10);
    hood.setSensorPhase(false); // decides which direction is positive

    // reset encoders to zero
    hood.setSelectedSensorPosition(0, 0, 10);

    // makes sure both shooter wheels spin together, but one is inverted
    shooterWheelInverted.follow(shooterWheel); //maybe only works for motor controller, we'll see
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Encoder Value", hood.getSelectedSensorPosition() * kHoodTick2Degree);
  }

  // spins shooterWheels
  public void shootShooters(double speed) {
    shooterWheel.set(TalonFXControlMode.PercentOutput, speed);
  }

  // spins top Wheel
  public void shootTop(double speed) {
    topWheel.set(TalonFXControlMode.PercentOutput, speed);
  }

  public void adjustHood() {
    System.out.println("Sensor Vel:" + hood.getSelectedSensorVelocity());
    System.out.println("Sensor Pos:" + hood.getSelectedSensorPosition());
    System.out.println("Out %" + hood.getMotorOutputPercent());
  }

  // stops shooter1
  public void stop1() {
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  // stops shooter2, shooter3, and shooter4
  public void stop2() {
    topWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

