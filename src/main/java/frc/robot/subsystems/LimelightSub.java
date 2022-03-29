// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSub extends SubsystemBase {
  private double STEER_K = -0.04; // needs to be tuned
  private double DRIVE_K = -0.1; // needs to be tuned
  private double min_cmd = 0.07;
  private double steer_cmd;
  private double drive_cmd;
  //private double DRIVE_K = 0.26;
  //private double DESIRED_TARGET_AREA = 10.0; //percent of the screen
  //private double MAX_DRIVE = 0.6;

  private double distance_error;

  private double current_distance;

  private NetworkTable limelight;
  private double tv;
  private double ta;
  private double tx;
  private double ty;

  /** Creates a new Limelight. */
  public LimelightSub() {
    limelight = NetworkTableInstance.getDefault().getTable("limelight");
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
  }

  public double getSteer() {
    steer_cmd = (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0)) * STEER_K;
    // We do see the target, execute aiming code
    //steer_cmd = 0;
    if (tx > 1.0) {
      return steer_cmd = tx * STEER_K - min_cmd; //either + or -
    } else if (tx < -1.0) {
      return steer_cmd = tx * STEER_K + min_cmd; //either + or -
    }
    
    return steer_cmd; 
  }

  public double getDistanceAdjust() {
    distance_error = ty - 15; // figure out actual error
    drive_cmd = DRIVE_K * distance_error;
    return drive_cmd;
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

  public void setPipeline(Integer pipeline) {
    if(pipeline<0){
        pipeline = 0;
        throw new IllegalArgumentException("Pipeline can not be less than zero");
    }else if(pipeline>9){
        pipeline = 9;
        throw new IllegalArgumentException("Pipeline can not be greater than nine");
    }
    limelight.getEntry("pipeline").setValue(pipeline);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    current_distance = 96 + -4.12*ty + 0.0981*(Math.pow(ty, 2)) + -1.22*(Math.pow(10, -3))*(Math.pow(ty, 3));

    SmartDashboard.putNumber("Current Distance: ", getDistance());
    SmartDashboard.putBoolean("Is Target Valid", isTargetValid());
    SmartDashboard.putNumber("Horizonatal Error (tx): ", tx);
    SmartDashboard.putNumber("Vertical Error (ty): ", ty);
    SmartDashboard.putNumber("Area of Screen (ta): ", ta);
  }
}
