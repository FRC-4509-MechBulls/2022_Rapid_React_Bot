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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.SetHoodToAngleCmd;

public class ShooterClimbSub extends SubsystemBase {
  private TalonFX shooterWheel;
  private TalonFX shooterWheelInverted;
  private TalonFX topWheel;
 
  private WPI_TalonSRX hood;
  private final double kHoodTick2Degree = 360 / 4096 * 26 / 42 * 18 / 60 * 18 / 84;

  private DoubleSolenoid enable;
  private DoubleSolenoid actuation;

  private DigitalInput limitSwitch;
  private ServoSub servo;
  
  // need to be found
  private final double position1 = 0;
  private final double position2 = 0;
  private final double position3 = 0;
  private final double position4 = 0;

  public ShooterClimbSub() {
    shooterWheel = new TalonFX(Constants.SHOOTER_FALCON);
    shooterWheel.setInverted(false);
    shooterWheelInverted = new TalonFX(Constants.SHOOTER_FALCON_INVERTED);
    shooterWheelInverted.setInverted(true);

    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);
    topWheel.setInverted(false);
   
    servo = new ServoSub();
    
    limitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_DI);

    enable = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ENABLE_FORWARD, Constants.ENABLE_REVERSE);
    actuation = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ACTUATION_FORWARD, Constants.ACTUATION_REVERSE);

    // makes sure both shooter wheels spin together, but one is inverted
    shooterWheelInverted.follow(shooterWheel); //maybe only works for motor controller, we'll see

    //configVelocLoop();
    //configPosLoop();
  }

  public void climbStage1() {
    /* parameters of wait commands will be changed later */
    configPosLoop(); //configures position closed loop
    enable.set(DoubleSolenoid.Value.kForward); //forward or reverse? do we need to initialize a direction?
    new WaitCommand(1);
    while (!limitSwitch.get()) { //rotate down until limit switch is toggled
      shooterWheel.set(TalonFXControlMode.PercentOutput, 0.1); //need to be set to negative?
    }
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0); //stops motor
    shooterWheel.setSelectedSensorPosition(0, 0, 10); //resets encoder position
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position1); //moves to position1
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kForward); //extends pneumatics
  }

  public void climbStage2() {
    /* parameters of wait commands will be changed later */
    configPosLoop(); //configures position closed loop
    shooterWheel.set(TalonFXControlMode.Position, position2); //moves to position 2
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position3); //moves to position 3
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kReverse); //retracts pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position4); //moves to position 4
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kForward); //extends pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position2); //moves to position 2
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position3); //moves to position 3
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kReverse); //retracts pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position4); //moves to position 4
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kForward); //extends pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position2); //moves to position 2
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position3); //moves to position 3
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kReverse); //retracts pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position4); //moves to position 4
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kForward); //extends pneumatics
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position2); //moves to position 2
    new WaitCommand(1);
    shooterWheel.set(TalonFXControlMode.Position, position3); //moves to position 3
    new WaitCommand(1);
    actuation.set(DoubleSolenoid.Value.kReverse); //retracts pneumatics
  }

  // spins shooterWheels
  // might need to be separate from topWheel??
  public void shootShooters(double distance) {
    configVelocLoop();
    /* shooterWheel and topWheel spin at different velocities,
    * but should spin at the same time, so they are in the same method */
    double targetVelocity_UnitsPer100ms_shooterWheel = distance * 1; // use polynomial regression line
    double targetVelocity_UnitsPer100ms_topWheel = distance * 1; // use polynomial regression line
    // we'll figure out these equations later^^ (and changed "variable" to something passed throught by limelight)
    //shooterWheel.set(TalonFXControlMode.PercentOutput, speed);
    shooterWheel.set(TalonFXControlMode.Velocity, targetVelocity_UnitsPer100ms_shooterWheel);
    topWheel.set(TalonFXControlMode.Velocity, targetVelocity_UnitsPer100ms_topWheel);
  }

  public void FenderShot() {
    new SetHoodToAngleCmd(servo, 180); //or use empty constructor
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0.5); //change value
    topWheel.set(TalonFXControlMode.PercentOutput, 0.5);
  }

  public void RejectBall(){
    new SetHoodToAngleCmd(servo, servo.MAX_SERVO_ANGLE);
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0.2);
    topWheel.set(TalonFXControlMode.PercentOutput, 0.2);
  }
  /* spins top Wheel
  public void shootTop(double speed) {
    topWheel.set(TalonFXControlMode.PercentOutput, speed);
  } */
  //uncomment this in case we need a separate method for the topWheel


  // stops shooter1
  public void stop() {
    shooterWheel.set(TalonFXControlMode.PercentOutput, 0);
    topWheel.set(TalonFXControlMode.PercentOutput, 0);
  }

  /*
  public void stop2() {
    topWheel.set(TalonFXControlMode.PercentOutput, 0);
  } */

  public void configVelocLoop() {
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

  // configures loop for position for climb
  public void configPosLoop() {
    /* shooterWheel and topWheel */
    //configuring integrated encoders
    shooterWheel.configFactoryDefault();
    shooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    //climb.setSelectedSensorPosition(0, 0, 10);

    /* newer config API */
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings */
    shooterWheel.configAllSettings(configs);
    shooterWheelInverted.configAllSettings(configs);

    /*config nominal outputs */
    shooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		shooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		shooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		shooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		shooterWheel.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		shooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		shooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		shooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		shooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);
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

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

