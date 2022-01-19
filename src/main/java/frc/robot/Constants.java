// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Falcons
    public static final int FRONT_LEFT_TALON = 1;
    public static final int FRONT_RIGHT_TALON = 2;
    public static final int BACK_LEFT_TALON = 3;
    public static final int BACK_RIGHT_TALON = 4;
    
    public static final int SHOOTER_TALON_1 = 5;
    public static final int SHOOTER_TALON_2 = 6;
    public static final int SHOOTER_TALON_3 = 7;
    public static final int SHOOTER_TALON_4 = 8;

    public static final int HOOD_TALON = 0;

    //Speeds
    public static final double DRIVETRAIN_SPEED = 0;
    public static final double SHOOTER_SPEED_1 = 0.7;
    public static final double SHOOTER_SPEED_2 = 0.7;
    public static final double SHOOTER_SPEED_TOP = 1.0;

    //Controller Inputs
    public static final int RIGHT_TRIGGER = 3;
    public static final int LEFT_TRIGGER = 2;
    public static final int XBOX_LEFT_X_AXIS = 0;

    public static final int DRIVER_CONTROLLER = 0;
    public static final int SHOOTER_CONTROLLER = 1;

    //Solenoid Channels
    public static final int SHIFTER_1_FORWARD_CHANNEL = 0;
    public static final int SHIFTER_2_REVERSE_CHANNEL = 1;
    
}
