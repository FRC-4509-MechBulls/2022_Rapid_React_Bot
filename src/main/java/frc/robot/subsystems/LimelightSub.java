// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSub extends SubsystemBase {
  private double STEER_K = 0.03; // needs to be tuned
  //private double DRIVE_K = 0.26;
  //private double DESIRED_TARGET_AREA = 10.0; //percent of the screen
  //private double MAX_DRIVE = 0.7;

  private double current_distance;

  private NetworkTable limelight;
  double tv;
  double ta;
  double tx;
  double ty;

  /** Creates a new Limelight. */
  public LimelightSub() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  public double getSteer() {
    double steer_cmd = tx * STEER_K;
    return steer_cmd;
  }

  /* NEED TO UPDATE EQUATION */
  public double getDistance() {
    return current_distance;
  }

  public double getX() {
    return tx;
  }

  public double getArea() {
    return ta;
  }

  public double getY(){
    return ty;
  }

  public boolean isTargetValid() { //ONLY RETURNS FALSE if you type tv == 1.0 (must not be updating enough)
    if (tv == 1.0) {
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    current_distance = 37.0 / (Math.tan(20.474 + ty)); //NEED TO UPDATE EQUATION

    SmartDashboard.putNumber("Current Distance: ", current_distance);
    SmartDashboard.putBoolean("Is Target Valid", isTargetValid());
    SmartDashboard.putNumber("Horizonatal Error (tx): ", tx);
    SmartDashboard.putNumber("Vertical Error (ty): ", ty);
    SmartDashboard.putNumber("Area of Screen (ta): ", ta);
  }
}
