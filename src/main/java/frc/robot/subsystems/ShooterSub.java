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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
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
  Timer timer;
  private static LinearServo leftHoodServo;
  private static LinearServo rightHoodServo;
  
  private ShuffleboardTab shooter_tune = Shuffleboard.getTab("Shooter Speeds");
  private NetworkTableEntry treadWheel_speed = shooter_tune.add("Tread Wheel RPM Speed", 6000).getEntry();
  private NetworkTableEntry servoSettings = shooter_tune.add("Servo Length", 70).getEntry();
  private NetworkTableEntry topWheel_speed = shooter_tune.add("Top Wheel RPM Speed", -8500).getEntry();

  //LIMELIGHT
  private double STEER_K = -0.045; 
  private double DRIVE_K = 0.123; 
  private double min_cmd = 0.07;
  private double steer_cmd;
  private double drive_cmd;

  private double distance_error;

  private double current_distance;

  private NetworkTable limelight;
  private double tv;
  private double ta;
  private double tx;
  private double ty;

  public ShooterSub() {
    leftHoodServo = new LinearServo(Constants.LEFT_SERVO_CHANNEL, Constants.SERVO_LENGTH, Constants.SERVO_SPEED);
    rightHoodServo = new LinearServo(Constants.RIGHT_SERVO_CHANNEL, Constants.SERVO_LENGTH, Constants.SERVO_SPEED);

    leftShooterWheel = new WPI_TalonFX(Constants.LEFT_SHOOTER_FALCON);
    leftShooterWheel.setNeutralMode(NeutralMode.Coast);
    leftShooterWheel.setInverted(true);   // MRNOTE This has been tested, true is good
    rightShooterWheel = new WPI_TalonFX(Constants.RIGHT_SHOOTER_FALCON);
    rightShooterWheel.setNeutralMode(NeutralMode.Coast);
    rightShooterWheel.setInverted(false);  // MRNOTE This has been tested, false is good
    rightShooterWheel.configClosedloopRamp(10);
    kickWheel = new WPI_TalonFX(Constants.KICKER_FALCON);
    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);  //MRNOTE - why WPI for this one?
    topWheel.setInverted(false);


    //LIMELIGHT
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    configVelocLoop();
    setPipeline(Constants.SHOOTER_PIPELINE);
  }

  //LIMELIGHT
  public double getSteer() {
    // steer_cmd = (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)) * STEER_K;
    // // We do see the target, execute aiming code
    // //steer_cmd = 0;
    // if (tx > 1.0) {
    //   return steer_cmd = tx * STEER_K - min_cmd; //either + or -
    // } else if (tx < -1.0) {
    //   return steer_cmd = tx * STEER_K + min_cmd; //either + or -
    // }
    
    // return steer_cmd;
    if (tx > 8.0) {
      steer_cmd = -0.5;
    } 
    else if (tx > 1.5 && tx < 8.0) {
      steer_cmd = -0.36;
    } 
    else if (tx < -8.0) {
      steer_cmd = 0.5;
    }
    else if (tx < -1.5 && tx > -8.0) {
      steer_cmd = 0.36;
    } 
    else {
      steer_cmd = 0.0;
    }
    return steer_cmd;

  }

  public double getDistanceAdjust() {
    distance_error = ty + 12.83;
    drive_cmd = DRIVE_K * distance_error;
    return drive_cmd;
  }

  public double getX() {
    return tx;
  }

  public double getArea() {
    return ta;
  }

  public double getY(){
    return ty;
  }

  public boolean isTargetValid() { //ONLY RETURNS FALSE if you type tv == 1.0 (must not be updating enough)
    if (tv == 1.0) {
      return true;
    }
    return false;
  }

  public void setPipeline(Integer pipeline) {
    if(pipeline<0){
        pipeline = 0;
        throw new IllegalArgumentException("Pipeline can not be less than zero");
    }else if(pipeline>9){
        pipeline = 9;
        throw new IllegalArgumentException("Pipeline can not be greater than nine");
    }
    limelight.getEntry("pipeline").setValue(pipeline);
  }


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

  public void LLShot(double shootVelocity, double topVelocity){
    System.out.println(shootVelocity);
    System.out.println(topVelocity);
    leftShooterWheel.set(TalonFXControlMode.Velocity, shootVelocity);
    rightShooterWheel.set(TalonFXControlMode.Velocity, shootVelocity);
    topWheel.set(TalonFXControlMode.Velocity, topVelocity);

    kickWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void LLShotServo(double servoPosition){
    leftHoodServo.setPosition(servoPosition);
    rightHoodServo.setPosition(servoPosition);
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
    kickWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void rejectBall(){
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, 0.25);  // robot barf
    rightShooterWheel.set(TalonFXControlMode.PercentOutput, 0.25);
    topWheel.set(TalonFXControlMode.PercentOutput, -0.35);
    kickWheel.set(TalonFXControlMode.PercentOutput, Constants.KICK_SPEED);
  }

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
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    current_distance = 96 + -4.12*ty + 0.0981*(Math.pow(ty, 2)) + -1.22*(Math.pow(10, -3))*(Math.pow(ty, 3));

    SmartDashboard.putBoolean("Is Target Valid", isTargetValid());
    SmartDashboard.putNumber("Horizonatal Error (tx): ", tx);
    SmartDashboard.putNumber("Vertical Error (ty): ", ty);
    SmartDashboard.putNumber("Area of Screen (ta): ", ta);
    double shooterWheelVel = leftShooterWheel.getSelectedSensorVelocity(0); /* position units per 100ms */
    double topWheelVel = topWheel.getSelectedSensorVelocity();
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

