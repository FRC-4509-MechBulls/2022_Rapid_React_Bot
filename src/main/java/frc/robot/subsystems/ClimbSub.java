// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimbSub extends SubsystemBase {
  private WPI_TalonFX climb;
  private WPI_TalonFX climbInverted;
  private DoubleSolenoid hook;

  private BangBangController controller; //https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/bang-bang.html
  
  /** Creates a new ClimbSub. */
  public ClimbSub() {
    climb = new WPI_TalonFX(Constants.CLIMB_FALCON);
    climb.setInverted(false);
    climbInverted = new WPI_TalonFX(Constants.CLIMB_FALCON_INVERTED);
    climbInverted.setInverted(true);

    hook = new DoubleSolenoid(PneumaticsModuleType.REVPH,Constants.HOOK_FORWARD, Constants.HOOK_REVERSE);

    controller = new BangBangController();
  }

  public void activateClimb()
  {
    climb.set(Constants.CLIMB_SPEED);
    climbInverted.set(Constants.CLIMB_SPEED);
    hook.set(DoubleSolenoid.Value.kForward);
  }

  public void stopClimb()
  {
    climb.set(0);
    climbInverted.set(0);
    hook.set(DoubleSolenoid.Value.kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
