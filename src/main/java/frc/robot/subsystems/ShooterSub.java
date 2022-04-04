// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LinearServo;

public class ShooterSub extends SubsystemBase {
  private WPI_TalonFX leftShooterWheel;
  private WPI_TalonFX rightShooterWheel;
  private WPI_TalonFX topWheel;
  private WPI_TalonFX kickWheel;
  //private ServoSub servo;
  Timer timer;
  private static LinearServo leftHoodServo;
  private static LinearServo rightHoodServo;
  
  private ShuffleboardTab shooter_tune = Shuffleboard.getTab("Shooter Speeds");
  private NetworkTableEntry treadWheel_speed = shooter_tune.add("Tread Wheel RPM Speed", 6000).getEntry();
  private NetworkTableEntry servoSettings = shooter_tune.add("Servo Length", 70).getEntry();
  private NetworkTableEntry topWheel_speed = shooter_tune.add("Top Wheel RPM Speed", -8500).getEntry();


  public ShooterSub() {
  
   // compressor = new Compressor(module, moduleType)
    leftHoodServo = new LinearServo(Constants.LEFT_SERVO_CHANNEL, Constants.SERVO_LENGTH, Constants.SERVO_SPEED);
    rightHoodServo = new LinearServo(Constants.RIGHT_SERVO_CHANNEL, Constants.SERVO_LENGTH, Constants.SERVO_SPEED);

    leftShooterWheel = new WPI_TalonFX(Constants.LEFT_SHOOTER_FALCON);
    leftShooterWheel.setNeutralMode(NeutralMode.Coast);
    leftShooterWheel.setInverted(true);   // MRNOTE This has been tested, true is good
    //leftShooterWheel.configClosedloopRamp(10);
    //leftShooterWheel.configOpenloopRamp(1);
    //leftShooterWheel.configFactoryDefault();

    rightShooterWheel = new WPI_TalonFX(Constants.RIGHT_SHOOTER_FALCON);
    rightShooterWheel.setNeutralMode(NeutralMode.Coast);
    rightShooterWheel.setInverted(false);  // MRNOTE This has been tested, false is good
    rightShooterWheel.configClosedloopRamp(10);
    //rightShooterWheel.configOpenloopRamp(1);
    //rightShooterWheel.configFactoryDefault();

    kickWheel = new WPI_TalonFX(Constants.KICKER_FALCON);
    //kickWheel.configClosedloopRamp(1);
    //kickWheel.configOpenloopRamp(1);
    //kickWheel.configFactoryDefault();

    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);  //MRNOTE - why WPI for this one?
    topWheel.setInverted(false);
    //topWheel.configClosedloopRamp(1);
    //topWheel.configOpenloopRamp(1);
    //topWheel.configFactoryDefault();
   
    configVelocLoop();
  }


  // spins shooterWheels
  // might need to be separate from topWheel??
  public void SetServoFender(){
    leftHoodServo.setPosition(Constants.SERVO_FENDER_SHOT_LENGTH);
    rightHoodServo.setPosition(Constants.SERVO_FENDER_SHOT_LENGTH);
  }
  public void SetServoFarShot(){
    leftHoodServo.setPosition(Constants.SERVO_FAR_SHOT_LENGTH);
    rightHoodServo.setPosition(Constants.SERVO_FAR_SHOT_LENGTH);
  }
  public void farShot() {    
    leftShooterWheel.set(TalonFXControlMode.Velocity, 6500);
    rightShooterWheel.set(TalonFXControlMode.Velocity, 6500);
    topWheel.set(TalonFXControlMode.Velocity, -8500);
    kickWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void LLShot(){
    leftShooterWheel.set(TalonFXControlMode.Velocity, treadWheel_speed.getDouble(6000));
    rightShooterWheel.set(TalonFXControlMode.Velocity, treadWheel_speed.getDouble(6000));
    topWheel.set(TalonFXControlMode.Velocity, topWheel_speed.getDouble(-8500));
    kickWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void LLShotServo(){
    leftHoodServo.setPosition(servoSettings.getDouble(70));
    rightHoodServo.setPosition(servoSettings.getDouble(70));
  }

  public void fenderShot() {
    leftShooterWheel.set(TalonFXControlMode.Velocity, 6000); 
    rightShooterWheel.set(TalonFXControlMode.Velocity, 6000); 
    topWheel.set(TalonFXControlMode.Velocity, -7300);
    kickWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void autoFender(){
    leftHoodServo.setPosition(Constants.SERVO_FENDER_SHOT_LENGTH);
    rightHoodServo.setPosition(Constants.SERVO_FENDER_SHOT_LENGTH);
    leftShooterWheel.set(TalonFXControlMode.Velocity, 6000); 
    rightShooterWheel.set(TalonFXControlMode.Velocity, 6000); 
    topWheel.set(TalonFXControlMode.Velocity, -7300);
    kickWheel.set(TalonFXControlMode.Velocity, 6000);
  }

  public void rejectBall(){
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, 0.25);  // robot barf
    rightShooterWheel.set(TalonFXControlMode.PercentOutput, 0.25);
    topWheel.set(TalonFXControlMode.PercentOutput, -0.35);
    kickWheel.set(TalonFXControlMode.PercentOutput, Constants.KICK_SPEED);
  }


  // stops shooter1
  public void stop() {
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, 0);
    rightShooterWheel.set(TalonFXControlMode.PercentOutput, 0);
    topWheel.set(TalonFXControlMode.PercentOutput, 0);
    kickWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  public void configVelocLoop() {
    // shooterWheel and topWheel 
    //configuring integrated encoders
    leftShooterWheel.configFactoryDefault();
    //leftShooterWheel.configOpenloopRamp(1);
    leftShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    leftShooterWheel.setSelectedSensorPosition(0, 0, 10);

    rightShooterWheel.configFactoryDefault();
    //rightShooterWheel.configOpenloopRamp(1);
    rightShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    rightShooterWheel.setSelectedSensorPosition(0, 0, 10);

    topWheel.configFactoryDefault();
    //topWheel.configOpenloopRamp(1);
    topWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    topWheel.setSelectedSensorPosition(0, 0, 10);

    // newer config API 
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    // config all the settings 
    leftShooterWheel.configAllSettings(configs);
    rightShooterWheel.configAllSettings(configs);
    //shooterWheelInverted.configAllSettings(configs);

    topWheel.configAllSettings(configs);

    //config nominal outputs 
    leftShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    rightShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		rightShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		rightShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		rightShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    topWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		topWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		topWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		topWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    // Config the Velocity closed loop gains in slot0 
		leftShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kF, Constants.kTimeoutMs);
		leftShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kP, Constants.kTimeoutMs);
		leftShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kI, Constants.kTimeoutMs);
		leftShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kD, Constants.kTimeoutMs);
//mrnote
    rightShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kF, Constants.kTimeoutMs);
    rightShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kP, Constants.kTimeoutMs);
    rightShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kI, Constants.kTimeoutMs);
    rightShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kD, Constants.kTimeoutMs);

    // Config the Velocity closed loop gains in slot0 
		topWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kF, Constants.kTimeoutMs);
		topWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kP, Constants.kTimeoutMs);
		topWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kI, Constants.kTimeoutMs);
		topWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_topWheel.kD, Constants.kTimeoutMs);
  }

  // configures loop for position for climb
  /*
  public void configPosLoop() {
    /* shooterWheel and topWheel 
    //configuring integrated encoders
    leftShooterWheel.configFactoryDefault();
    leftShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    //climb.setSelectedSensorPosition(0, 0, 10);
    rightShooterWheel.configFactoryDefault();
    rightShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    
    /* newer config API 
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings 
    leftShooterWheel.configAllSettings(configs);
    rightShooterWheel.configAllSettings(configs);
    //shooterWheelInverted.configAllSettings(configs);

    /*config nominal outputs 
    leftShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /*config nominal outputs 
    rightShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
    rightShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
    rightShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
    rightShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    
    /**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 
		leftShooterWheel.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    rightShooterWheel.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0
		leftShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		leftShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		leftShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		leftShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 
		rightShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		rightShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		rightShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		rightShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);
  }
  */
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    /* get the selected sensor for PID0 */
    //double motorOutput = shooterWheel.getMotorOutputPercent();
    //double selSenPos = shooterWheel.getSelectedSensorPosition(0); /* position units */
    double shooterWheelVel = leftShooterWheel.getSelectedSensorVelocity(0); /* position units per 100ms */
    //double shooterWheelVel = leftShooterWheel.getSelectedSensorVelocity(0); /* position units per 100ms */
    double topWheelVel = topWheel.getSelectedSensorVelocity();
    //SmartDashboard.putNumber("Motor Output Percent: ",  motorOutput);
    //SmartDashboard.putNumber("Selected Sensor Positions: ", selSenPos);
    SmartDashboard.putNumber("Hood Position: ", leftHoodServo.getPosition());
    SmartDashboard.putNumber("Shooter Wheel Velocity: ", shooterWheelVel);
    SmartDashboard.putNumber("Top Wheel Velocity: ", topWheelVel);

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    // This method will be called once per scheduler run

  }
}

