// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {
  DoubleSolenoid intakeSolenoidLeft;
  DoubleSolenoid intakeSolenoidRight;
  WPI_TalonSRX intakeLeft;
  WPI_TalonSRX intakeRight;
  /** Creates a new IntakeSub. */
  public IntakeSub() {
    intakeSolenoidLeft = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_1_FORWARD, Constants.INTAKE_1_REVERSE);
    intakeSolenoidRight = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_2_FORWARD, Constants.INTAKE_2_REVERSE);
    intakeLeft = new WPI_TalonSRX(Constants.INTAKE_LEFT_TALON);
    intakeRight = new WPI_TalonSRX(Constants.INTAKE_RIGHT_TALON);
  }

  //temporary methods for actuating intake, might change later depending on logic
  public void deployIntakeLeft() {
    intakeSolenoidLeft.set(DoubleSolenoid.Value.kForward);
    intakeLeft.set(Constants.INTAKE_SPEED);
  }

  public void deployIntakeRight() {
    intakeSolenoidRight.set(DoubleSolenoid.Value.kForward);
    intakeRight.set(Constants.INTAKE_SPEED);
  }

  public void retractIntakeLeft() {
    intakeSolenoidLeft.set(DoubleSolenoid.Value.kReverse);
    intakeLeft.set(0);
  }

  public void retractIntakeRight() {
    intakeSolenoidRight.set(DoubleSolenoid.Value.kReverse);
    intakeRight.set(0);
  }


@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}

