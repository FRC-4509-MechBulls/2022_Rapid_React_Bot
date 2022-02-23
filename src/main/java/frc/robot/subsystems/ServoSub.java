// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ServoSub extends SubsystemBase {
  private static double startAngle;
  private static double endAngle;
  private static double currentAngle;
  private static double startTime;

  public static final double MIN_SERVO_ANGLE = 0.0; //degrees
  public final double MAX_SERVO_ANGLE = 360.0; //degrees
  private static final double TIME_TO_SERVO_FULL_EXTENSION = 3.48; // avg time to move form retract to extend
  private static final double PERCENT_PER_SEC = 1.00 / TIME_TO_SERVO_FULL_EXTENSION;
  private final double DEGREES_PER_SEC = (MAX_SERVO_ANGLE - MIN_SERVO_ANGLE) * PERCENT_PER_SEC;

  private static final double HOOD_MAX_POS = 1.0; //percent servo travel to max hood position
  private static final double HOOD_MIN_POS = 0.0; //percent servo travel to min hood position

  //servo parameters
  private static final double MAX_SERVO_PWM = 2.0; //ms
  private static final double MIN_SERVO_PWM = 1.0; //ms
  private static final double SERVO_RANGE = MAX_SERVO_PWM - MIN_SERVO_PWM;
  private static final double CENTER_SERVO_PWM = 1.5; //ms
  private static final double SERVO_DEADBAND = 0.0; //ms - no deadband

  //pwm values in ms for max and min angles of shooter hood
  private static final double HOOD_MAX_PWM = MIN_SERVO_PWM + (SERVO_RANGE * HOOD_MAX_POS);
  private static final double HOOD_MIN_PWM = MIN_SERVO_PWM + (SERVO_RANGE * HOOD_MIN_POS);


  private double lastTime = 0;

  private Servo servo;

  /** Creates a new Servo. */
  public ServoSub() {
    servo = new Servo(Constants.SERVO_CHANNEL);
    servo.setBounds(HOOD_MAX_PWM, CENTER_SERVO_PWM + SERVO_DEADBAND, 
    			CENTER_SERVO_PWM, CENTER_SERVO_PWM - SERVO_DEADBAND, HOOD_MIN_PWM);
  }

  /**
   * Takes a given angle and rotates servo motor to that angle
   * @param degrees the angle limited by min and max values
   */
  public void setAngle(double degrees) {
    if (degrees <= MIN_SERVO_ANGLE) {
      degrees = MIN_SERVO_ANGLE;
    }
    if (degrees >= MAX_SERVO_ANGLE) {
      degrees = MAX_SERVO_ANGLE;
    }
    startAngle = servo.getAngle();
    startTime = Timer.getFPGATimestamp();

    servo.setAngle(degrees);
  }

  public double getServoAngle() {
    return servo.getAngle();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double angle = servo.getAngle();
    SmartDashboard.putNumber("Servo Angle: ", angle);
  }
}
