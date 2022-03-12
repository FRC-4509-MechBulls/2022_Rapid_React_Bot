// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

/** Add your docs here. */
public class LinearServo extends Servo
{
    double speed;
    double length;

    double setPos;
    double curPos;

      //servo parameters
    private static final double MAX_SERVO_PWM = 2.0; //ms
    private static final double MIN_SERVO_PWM = 1.0; //ms
    private static final double CENTER_SERVO_PWM = 1.5; //ms
    private static final double SERVO_DEADBAND = 0.2; //ms 

    public LinearServo(int channel, int length, int speed)
    {
        super(channel);
        setBounds(MAX_SERVO_PWM, MAX_SERVO_PWM-SERVO_DEADBAND, CENTER_SERVO_PWM, MIN_SERVO_PWM+SERVO_DEADBAND, MIN_SERVO_PWM);
        this.length = length;
        this.speed = speed;

    }

    public void setPosition(double setpoint)
    {
        setPos = MathUtil.clamp(setpoint, 0, length);
        setSpeed((setPos/length*2)-1);

    }

    double lastTime = 0;

    public void updateCurPos()
    {
        double dt = Timer.getFPGATimestamp()-lastTime;
        if(curPos > setPos + speed *dt)
            curPos -= speed*dt;
        else if(curPos < setPos-speed*dt)
            curPos += speed*dt;
        else 
            curPos = setPos;
    }

    public double getPosition()
    {
        return curPos;
    }

    public boolean isFinished()
    {
        return curPos == setPos;
    }
}
