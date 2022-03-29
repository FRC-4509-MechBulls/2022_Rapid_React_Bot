// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {
  private Solenoid intakeSolenoid;
  private WPI_TalonSRX intake;
  private String IntakeStatus;
  //private WPI_TalonSRX intakeRight;
  /** Creates a new IntakeSub. */
  public IntakeSub() {
    //only one solenoid, control both sides
    intakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.INTAKE_CHANNEL);
    intake = new WPI_TalonSRX(Constants.INTAKE_TALON);
    IntakeStatus = "None";
  }

  //temporary methods for actuating intake, might change later depending on logic
  public void deployIntake() {
    intakeSolenoid.set(true);
    intake.set(Constants.INTAKE_SPEED);
    IntakeStatus = "Enabled";
    
  }

  public void retractIntake() {
    intakeSolenoid.set(false);
    intake.set(0);
    IntakeStatus = "Disabled";
  }



@Override
  public void periodic() {
    SmartDashboard.putString("Intake Status", IntakeStatus);
    // This method will be called once per scheduler run
  }


}

