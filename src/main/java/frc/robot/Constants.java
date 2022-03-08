// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //DriveTrain
    // LEFT and RIGHT are robot's view/perspective
    public static final int LEFT_FRONT_TALON = 4;
    public static final int LEFT_BACK_TALON = 3;
    public static final int RIGHT_FRONT_TALON = 2;
    public static final int RIGHT_BACK_TALON = 1;
    
    //Shooter
    public static final int SHOOTER_FALCON_TOP = 20;
    public static final int LEFT_SHOOTER_FALCON = 21;
    public static final int RIGHT_SHOOTER_FALCON = 22;    
    public static final int KICKER_FALCON = 23;
    
    public static final int HOOD_TALON = 0; //fx // servo control?

    //Indexer
    public static final int INDEX_BELT_LEFT= 31; //figure out later  srx
    public static final int INDEX_BELT_RIGHT= 32; //figure out later   srx
    public static final int INDEX_KICK_TALON = 23;   //  srx
    

    //Speeds
    public static final double DRIVETRAIN_SPEED = 0.5;
    public static final double SHOOTER_SPEED = 0.4;
    public static final double SHOOTER_SPEED_TOP = 1.0;
    public static final double INDEXER_SPEED = 0.4;
    public static final double INTAKE_SPEED = -0.6;
    public static final double CLIMB_SPEED = 0;
    public static final double KICK_SPEED = 0.5;

    //Controller Inputs
    public static final int DRIVER_CONTROLLER = 0;
    public static final int SHOOTER_CONTROLLER = 1;
    public static final int RIGHT_TRIGGER = 3;
    public static final int LEFT_TRIGGER = 2;
    public static final int XBOX_LEFT_X_AXIS = 0;
    
    //Intake
    public static final int INTAKE_TALON = 40;
    //public static final int INTAKE_RIGHT_TALON = 12;
    public static final int INTAKE_2_REVERSE = 0; // reverse pneumatics channel
    public static final int INTAKE_2_FORWARD = 1; // forward pneumatics channel
    public static final int INTAKE_1_REVERSE = 2; // reverse pneumatics channel
    public static final int INTAKE_1_FORWARD = 3; // forward pneumatics channel
   
    //Sonar
    public static final int SONAR_CHANNEL = 0;

    //Solenoid Channels
    public static final int SHIFTER_1_FORWARD_CHANNEL = 0;
    public static final int SHIFTER_2_REVERSE_CHANNEL = 1;

    public static final int HOOK_FORWARD = 2;
    public static final int HOOK_REVERSE = 3;

    public static final int ENABLE_FORWARD = 4;
    public static final int ENABLE_REVERSE = 5;

    public static final int ACTUATION_FORWARD = 6;
    public static final int ACTUATION_REVERSE = 7;

    //Climb Motors
    public static final int CLIMB_FALCON = 0;
	public static final int CLIMB_FALCON_INVERTED = 0;
    
    public static final int kTimeoutMs = 30;
    public static final int kPIDLoopIdx = 0;

    //kP kI kD kF Iz PeakOut -- FOR PID LOOP
    public final static Gains kGains_Velocit_shooterWheel = new Gains(0.1,  0.001,  2,  767.25/17207,  300,  1.00);
    public final static Gains kGains_Velocit_topWheel = new Gains(0.1,  0.001,  2,  767.25/17207,  300,  1.00); // needs to be tuned unless it's the same lol
    public final static Gains kGains_Posit_climb = new Gains(0.15,  0.0,  1,  0.0,  0,  1.00); //needs to be tuned

    //Digital Inputs
    public static final int BB_1_CHANNEL = 0;
    public static final int BB_2_CHANNEL = 2;
    public static final int BB_3_CHANNEL = 4;
    public static final int LIMIT_SWITCH_DI = 6;

    //PWM
    public static final int SERVO_1_CHANNEL = 0;
    public static final int SERVO_2_CHANNEL = 9;
    
}

