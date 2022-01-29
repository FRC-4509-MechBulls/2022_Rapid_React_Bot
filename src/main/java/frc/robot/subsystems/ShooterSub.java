// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {
  TalonFX shooterWheel;
  TalonFX shooterWheelInverted;
  TalonFX topWheel;
 
  WPI_TalonSRX hood;
  private final double kHoodTick2Degree = 360 / 4096 * 26 / 42 * 18 / 60 * 18 / 84;

  public ShooterSub() {
    shooterWheel = new TalonFX(Constants.SHOOTER_FALCON);
    shooterWheel.setInverted(false);
    shooterWheelInverted = new TalonFX(Constants.SHOOTER_FALCON_INVERTED);
    shooterWheelInverted.setInverted(true);

    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);
    topWheel.setInverted(false);
   
    hood = new WPI_TalonSRX(Constants.HOOD_TALON);

    // makes sure both shooter wheels spin together, but one is inverted
    shooterWheelInverted.follow(shooterWheel); //maybe only works for motor controller, we'll see

    configEncoders();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Hood Encoder Value", hood.getSelectedSensorPosition() * kHoodTick2Degree);
    /* get the selected sensor for PID0 */
    double motorOutput = shooterWheel.getMotorOutputPercent();
    double selSenPos = shooterWheel.getSelectedSensorPosition(0); /* position units */
    double selSenVel = shooterWheel.getSelectedSensorVelocity(0); /* position units per 100ms */
    SmartDashboard.putNumber("Motor Output Percent: ",  motorOutput);
    SmartDashboard.putNumber("Selected Sensor Positions: ", selSenPos);
    SmartDashboard.putNumber("Selected Sensor Velocity: ", selSenVel);
  }

  // spins shooterWheels
  // might need to be separate from topWheel??
  public void shootShooters(double variable) {
    /* shooterWheel and topWheel spin at different velocities,
    * but should spin at the same time, so they are in the same method */
    double targetVelocity_UnitsPer100ms_shooterWheel = variable * 2000.0 * 2048.0 / 600.0; // changed with limelight equation
    double targetVelocity_UnitsPer100ms_topWheel = variable * 2000.0 * 2048.0 / 600.0; // change the first number, not the rest!!
    // we'll figure out these equations later^^ (and changed "variable" to something passed throught by limelight)
    //shooterWheel.set(TalonFXControlMode.PercentOutput, speed);
    shooterWheel.set(TalonFXControlMode.Velocity, targetVelocity_UnitsPer100ms_shooterWheel);
    topWheel.set(TalonFXControlMode.Velocity, targetVelocity_UnitsPer100ms_topWheel);
  }

  /* spins top Wheel
  public void shootTop(double speed) {
    topWheel.set(TalonFXControlMode.PercentOutput, speed);
  } */
  //uncomment this in case we need a separate method for the topWheel

  public void adjustHood() {
    System.out.println("Sensor Vel:" + hood.getSelectedSensorVelocity());
    System.out.println("Sensor Pos:" + hood.getSelectedSensorPosition());
    System.out.println("Out %" + hood.getMotorOutputPercent());
    hood.set(ControlMode.Velocity, 0.4); // ???
  }

  // stops shooter1
  public void stop1() {
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  // stops shooter2, shooter3, and shooter4
  public void stop2() {
    topWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  public void configEncoders() {
    /* shooterWheel and topWheel */
    //configuring integrated encoders
    shooterWheel.configFactoryDefault();
    shooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    shooterWheel.setSelectedSensorPosition(0, 0, 10);

    topWheel.configFactoryDefault();
    topWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    topWheel.setSelectedSensorPosition(0, 0, 10);

    /* newer config API */
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings */
    shooterWheel.configAllSettings(configs);
    shooterWheelInverted.configAllSettings(configs);

    topWheel.configAllSettings(configs);

    /*config nominal outputs */
    shooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		shooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		shooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		shooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    topWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		topWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		topWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		topWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		shooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kF, Constants.kTimeoutMs);
		shooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kP, Constants.kTimeoutMs);
		shooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kI, Constants.kTimeoutMs);
		shooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kD, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		topWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kF, Constants.kTimeoutMs);
		topWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kP, Constants.kTimeoutMs);
		topWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kI, Constants.kTimeoutMs);
		topWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kD, Constants.kTimeoutMs);

    /* hood */
    /*
    // configuring which encoder is being used - ctre mag encoder, relative
    hood.configFactoryDefault();
    hood.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0 , 10);
    hood.setSensorPhase(false); // decides which direction is positive
 
    // reset encoders to zero
    hood.setSelectedSensorPosition(0, 0, 10);
    */
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

