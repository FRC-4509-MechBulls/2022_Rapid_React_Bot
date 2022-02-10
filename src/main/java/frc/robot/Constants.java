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
    public static final int LEFT_FRONT_TALON = 1;
    public static final int LEFT_BACK_TALON = 2;
    public static final int RIGHT_FRONT_TALON = 3;
    public static final int RIGHT_BACK_TALON = 4;
    
    //Shooter
    public static final int SHOOTER_FALCON = 5;
    public static final int SHOOTER_FALCON_INVERTED = 6;
    //public static final int SHOOTER_TALON = 7;
    public static final int SHOOTER_FALCON_TOP = 0;
    
    public static final int HOOD_TALON = 0;

    //Indexer
    public static final int INDEX_BELT_1_TALON = 0; //figure out later
    public static final int INDEX_BELT_2_TALON = 0; //figure out later
    public static final int INDEX_KICK_1_TALON = 0; //figure out later
    public static final int INDEX_KICK_2_TALON = 0; //figure out later

    //Speeds
    public static final double DRIVETRAIN_SPEED = 0.5;
    public static final double SHOOTER_SPEED_1 = 0.7;
    public static final double SHOOTER_SPEED_2 = 0.7;
    public static final double SHOOTER_SPEED_TOP = 1.0;
    public static final double INDEXER_SPEED = 0;
    public static final double INTAKE_SPEED = 0.5;
    public static final double CLIMB_SPEED = 0;

    //Controller Inputs
    public static final int DRIVER_CONTROLLER = 0;
    public static final int SHOOTER_CONTROLLER = 1;
    public static final int RIGHT_TRIGGER = 3;
    public static final int LEFT_TRIGGER = 2;
    public static final int XBOX_LEFT_X_AXIS = 0;
    
    //Intake
    public static final int INTAKE_LEFT_TALON = 11;
    public static final int INTAKE_RIGHT_TALON = 12;
    public static final int INTAKE_2_REVERSE = 0; // reverse pneumatics channel
    public static final int INTAKE_2_FORWARD = 1; // forward pneumatics channel
    public static final int INTAKE_1_REVERSE = 2; // reverse pneumatics channel
    public static final int INTAKE_1_FORWARD = 3; // forward pneumatics channel
   
    //Sonar
    public static final int SONAR_CHANNEL = 0;

    //Solenoid Channels
    public static final int SHIFTER_1_FORWARD_CHANNEL = 0;
    public static final int SHIFTER_2_REVERSE_CHANNEL = 1;
    public static final int HOOK_FORWARD = 0;
    public static final int HOOK_REVERSE = 0;

    //Climb Motors
    public static final int CLIMB_FALCON = 0;
	public static final int CLIMB_FALCON_INVERTED = 0;
    
    public static final int kTimeoutMs = 30;
    public static final int kPIDLoopIdx = 0;

    //kP kI kD kF Iz PeakOut -- FOR PID LOOP
    public final static Gains kGains_Velocit_shooterWheel = new Gains(0.1,  0.001,  2,  767.25/17207,  300,  1.00);
    public final static Gains kGains_Velocit_topWheel = new Gains(0.1,  0.001,  2,  767.25/17207,  300,  1.00); // needs to be tuned unless it's the same lol
    
    public static final int BB_BOTTOM_1_CHANNEL = 0;
    public static final int BB_TOP_1_CHANNEL = 1;
    public static final int BB_BOTTOM_2_CHANNEL = 2;
    public static final int BB_TOP_2_CHANNEL = 3;
    public static final int BB_BOTTOM_3_CHANNEL = 4;
    public static final int BB_TOP_3_CHANNEL = 5;
    
}

