// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {
  DoubleSolenoid intakeSolenoid1;
  DoubleSolenoid intakeSolenoid2;
  WPI_TalonFX intake;
  /** Creates a new IntakeSub. */
  public IntakeSub() {
    intake = new WPI_TalonFX(Constants.INTAKE);
    intakeSolenoid1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_1_FORWARD, Constants.INTAKE_1_REVERSE);
    intakeSolenoid2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_2_FORWARD, Constants.INTAKE_2_REVERSE);
  }

  //temporary methods for actuating intake, might change later depending on logic
  public void deployIntake1() {
    intakeSolenoid1.set(DoubleSolenoid.Value.kForward);
  }

  public void deployIntake2() {
    intakeSolenoid2.set(DoubleSolenoid.Value.kForward);
  }


  public void retractIntake1() {
    intakeSolenoid1.set(DoubleSolenoid.Value.kReverse);
  }

  public void retractIntake2() {
    intakeSolenoid2.set(DoubleSolenoid.Value.kReverse);
  }
  
  public void deployIntakeTalon(double speed) {
    intake.set(speed);
  }

  public void retractIntakeTalon() {
    intake.set(0);
  } 

@Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


}

