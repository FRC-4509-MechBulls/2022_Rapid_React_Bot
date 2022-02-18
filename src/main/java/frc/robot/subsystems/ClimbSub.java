// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

//import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSub extends SubsystemBase {
  private WPI_TalonFX climb;
  private WPI_TalonFX climbInverted;

  private DoubleSolenoid hook;
  private DoubleSolenoid enable;
  private DoubleSolenoid actuation;

  //private BangBangController controller; //https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/bang-bang.html
  
  /** Creates a new ClimbSub. */
  public ClimbSub() {

    climb = new WPI_TalonFX(Constants.CLIMB_FALCON);
    climb.setInverted(false);
    climbInverted = new WPI_TalonFX(Constants.CLIMB_FALCON_INVERTED);
    climbInverted.setInverted(true);

    hook = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.HOOK_FORWARD, Constants.HOOK_REVERSE);

    //enable = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ENABLE_FORWARD, Constants.ENABLE_REVERSE);
    actuation = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.ACTUATION_FORWARD, Constants.ACTUATION_REVERSE);
    
    configPosLoop();
  }


  /*public void activateClimb()
  {
    climb.set(Constants.CLIMB_SPEED);
    climbInverted.set(Constants.CLIMB_SPEED);
    hook.set(DoubleSolenoid.Value.kForward);
  }
  */

  /*
  public void stopClimb()
  {
    climb.set(0);
    climbInverted.set(0);
    hook.set(DoubleSolenoid.Value.kReverse);
  }
  */

  public void configPosLoop() {
    /* shooterWheel and topWheel */
    //configuring integrated encoders
    climb.configFactoryDefault();
    climb.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    //climb.setSelectedSensorPosition(0, 0, 10);
    climb.setSensorPhase(false); //change maybe
    climb.setInverted(false);

    /* newer config API */
    TalonFXConfiguration configs = new TalonFXConfiguration();
    configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;

    /* config all the settings */
    climb.configAllSettings(configs);
    climbInverted.configAllSettings(configs);

    /*config nominal outputs */
    climb.configNominalOutputForward(0, Constants.kTimeoutMs);
		climb.configNominalOutputReverse(0, Constants.kTimeoutMs);
		climb.configPeakOutputForward(1, Constants.kTimeoutMs);
		climb.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    /**
		 * Config the allowable closed-loop error, Closed-Loop output will be
		 * neutral within this range. See Table in Section 17.2.1 for native
		 * units per rotation.
		 */
		climb.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

    /* Config the Velocity closed loop gains in slot0 */
		climb.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kF, Constants.kTimeoutMs);
		climb.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kP, Constants.kTimeoutMs);
		climb.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kI, Constants.kTimeoutMs);
		climb.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Posit_climb.kD, Constants.kTimeoutMs);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
