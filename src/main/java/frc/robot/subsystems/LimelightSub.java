// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSub extends SubsystemBase {
  private double STEER_K = 0.03;
  private double DRIVE_K = 0.26;
  private double DESIRED_TARGET_AREA = 10.0; //percent of the screen
  private double MAX_DRIVE = 0.7;

  private NetworkTable limelight;
  double tv;
  double ta;
  double tx;
  /** Creates a new Limelight. */
  public LimelightSub() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  public double getSteer() {
    double steer_cmd = (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)) * STEER_K;
    return steer_cmd;
  }

  public double getX() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public double getArea() {
    return ta;
  }

  public double getY(){
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  public boolean isTargetValid() { //ONLY RETURNS FALSE if you type tv == 1.0 (must not be updating enough)
    if (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1.0) {
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }
}
