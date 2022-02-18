// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ServoSub extends SubsystemBase {
  private double m_speed;
  private double m_length;

  private double setPos;
  private double curPos;

  private double lastTime = 0;

  private Servo servo;


  /** Creates a new ServoSub. */
  public ServoSub() {
    servo = new Servo(Constants.SERVO_CHANNEL);
  }


  public void setPosition(double setPoint) {
    setPos = MathUtil.clamp(setPoint, 0, m_length);
    setSpeed((setPos/m_length*2)-1);
  }

  public void updateCurPos() {
    double dt = Timer.getFPGATimestamp() - lastTime;
    if (curPos > setPos + m_speed *dt) {
      curPos -= m_speed * dt;
    } else if (curPos < setPos - m_speed * dt) {
      curPos += m_speed * dt;
    } else {
      curPos = setPos;
    }
  }

  /**
   * Current position of the servo, must be calling {@link #updateCurPos() updateCurPos()} periodically
   * 
   * @return Servo Position [mm]
   */
  public double getPosition() {
    return curPos;
  }

  /**
   * Checks if the servo is at its target position, must be calling {@link #updateCurPos() updateCurPos()} periodically
   * @return true when servo is at its target
   */
  public boolean isFinished() {
    return curPos == setPos;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    updateCurPos();
  }
}
