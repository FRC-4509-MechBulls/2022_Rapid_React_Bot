// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
//import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.SetHoodToAngleCmd;

public class ShooterClimbSub extends SubsystemBase {
  private TalonFX leftShooterWheel;
  private TalonFX rightShooterWheel;
  private TalonFX topWheel;
  private TalonFX kickWheel;
  private ServoSub servo;


  public ShooterClimbSub() {
    leftShooterWheel = new TalonFX(Constants.LEFT_SHOOTER_FALCON);
    leftShooterWheel.setInverted(true);   // MRNOTE This has been tested, true is good
    rightShooterWheel = new TalonFX(Constants.RIGHT_SHOOTER_FALCON);
    rightShooterWheel.setInverted(false);  // MRNOTE This has been tested, false is good

    kickWheel = new TalonFX(Constants.KICKER_FALCON);
    topWheel = new WPI_TalonFX(Constants.SHOOTER_FALCON_TOP);
    topWheel.setInverted(false);
   
    servo = new ServoSub();
  }


  // spins shooterWheels
  // might need to be separate from topWheel??
  public void shootShooters() {
    /* shooterWheel and topWheel spin at different velocities,
    * but should spin at the same time, so they are in the same method */
    //double targetVelocity_UnitsPer100ms_shooterWheel = distance * 1; // use polynomial regression line
    //double targetVelocity_UnitsPer100ms_topWheel = distance * 1; // use polynomial regression line
    // we'll figure out these equations later^^ (and changed "variable" to something passed throught by limelight)
    //shooterWheel.set(TalonFXControlMode.PercentOutput, speed);
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, Constants.SHOOTER_SPEED);
    rightShooterWheel.set(TalonFXControlMode.PercentOutput, Constants.SHOOTER_SPEED);
    topWheel.set(TalonFXControlMode.PercentOutput, -0.40);
    new WaitCommand(2);
    kickWheel.set(TalonFXControlMode.PercentOutput, Constants.KICK_SPEED);

  }

  public void FenderShot() {
    //new SetHoodToAngleCmd(servo, 180); //or use empty constructor
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, 0.5); //change value
    //rightShooterWheel.set(TalonFXControlMode.PercentOutput, 0.5); //change value
    topWheel.set(TalonFXControlMode.PercentOutput, 0.5);
    new WaitCommand(1);
    kickWheel.set(TalonFXControlMode.PercentOutput, Constants.KICK_SPEED);
    
  }

  public void RejectBall(){
    new SetHoodToAngleCmd(servo, servo.MIN_SERVO_ANGLE);
    leftShooterWheel.set(TalonFXControlMode.PercentOutput, 0.2);  // robot barf
    rightShooterWheel.set(TalonFXControlMode.PercentOutput, 0.2);
    topWheel.set(TalonFXControlMode.PercentOutput, 0.2);
    new WaitCommand(1);
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
    /* shooterWheel and topWheel */
    //configuring integrated encoders
    leftShooterWheel.configFactoryDefault();
    leftShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    leftShooterWheel.setSelectedSensorPosition(0, 0, 10);

    rightShooterWheel.configFactoryDefault();
    rightShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    rightShooterWheel.setSelectedSensorPosition(0, 0, 10);

    topWheel.configFactoryDefault();
    topWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    topWheel.setSelectedSensorPosition(0, 0, 10);

    /* newer config API */
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings */
    leftShooterWheel.configAllSettings(configs);
    rightShooterWheel.configAllSettings(configs);
    //shooterWheelInverted.configAllSettings(configs);

    topWheel.configAllSettings(configs);

    /*config nominal outputs */
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

    /* Config the Velocity closed loop gains in slot0 */
		leftShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kF, Constants.kTimeoutMs);
		leftShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kP, Constants.kTimeoutMs);
		leftShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kI, Constants.kTimeoutMs);
		leftShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kD, Constants.kTimeoutMs);
//mrnote
    rightShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kF, Constants.kTimeoutMs);
    rightShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kP, Constants.kTimeoutMs);
    rightShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kI, Constants.kTimeoutMs);
    rightShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit_shooterWheel.kD, Constants.kTimeoutMs);

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
    leftShooterWheel.configFactoryDefault();
    leftShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    //climb.setSelectedSensorPosition(0, 0, 10);
    rightShooterWheel.configFactoryDefault();
    rightShooterWheel.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    
    /* newer config API */
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings */
    leftShooterWheel.configAllSettings(configs);
    rightShooterWheel.configAllSettings(configs);
    //shooterWheelInverted.configAllSettings(configs);

    /*config nominal outputs */
    leftShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
		leftShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
		leftShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /*config nominal outputs */
    rightShooterWheel.configNominalOutputForward(0, Constants.kTimeoutMs);
    rightShooterWheel.configNominalOutputReverse(0, Constants.kTimeoutMs);
    rightShooterWheel.configPeakOutputForward(1, Constants.kTimeoutMs);
    rightShooterWheel.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    
    /**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		leftShooterWheel.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    rightShooterWheel.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		leftShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		leftShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		leftShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		leftShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		rightShooterWheel.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		rightShooterWheel.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		rightShooterWheel.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		rightShooterWheel.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);
  }
  /* not doing the fancy climb for now so commenting out all this stuff unless we need it later lol */

  /*
  public void climbStage1() {
    /* parameters of wait commands will be changed later 
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
  } */

  /* 
  public void climbStage2() {
    /* parameters of wait commands will be changed later
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
  } */

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
    SmartDashboard.putNumber("Shooter Wheel Velocity: ", shooterWheelVel);
    SmartDashboard.putNumber("Top Wheel Velocity: ", topWheelVel);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

