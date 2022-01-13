// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {
  // shooter1 is spun by pressing the A button
  WPI_TalonFX shooter1;

  // shooter2, shooter3, and shooter4 are spun by pressing the B button
  WPI_TalonFX shooter2;
  WPI_TalonFX shooter3;
  WPI_TalonFX shooter4;

  /** Creates a new ExampleSubsystem. */
  public ShooterSub() {
    shooter1 = new WPI_TalonFX(Constants.SHOOTER_TALON_1);
    shooter1.setInverted(false);

    shooter2 = new WPI_TalonFX(Constants.SHOOTER_TALON_2);
    shooter2.setInverted(false);
    shooter3 = new WPI_TalonFX(Constants.SHOOTER_TALON_3);
    shooter3.setInverted(true);
    shooter4 = new WPI_TalonFX(Constants.SHOOTER_TALON_4);
    shooter4.setInverted(true);

    // makes sure shooter2, shooter3, and shooter4 all spin together, 
    // however shooter2 spins in the opposite direction
    shooter3.follow(shooter2);
    //shooter4.follow(shooter2);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // spins shooter1
  public void shootGroup1(double speed) {
    shooter1.set(TalonFXControlMode.PercentOutput, speed);
  }

  // spins shooter2, shooter3, and shooter4
  public void shootGroup2(double speed1, double speed2) {
    shooter2.set(TalonFXControlMode.PercentOutput, speed1);
    shooter3.set(TalonFXControlMode.PercentOutput, speed2);
  }

  // stops shooter1
  public void stop1() {
    shooter1.set(TalonFXControlMode.PercentOutput, 0);
  }

  // stops shooter2, shooter3, and shooter4
  public void stop2() {
    shooter2.set(TalonFXControlMode.PercentOutput, 0);
    shooter4.set(TalonFXControlMode.PercentOutput, 0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

