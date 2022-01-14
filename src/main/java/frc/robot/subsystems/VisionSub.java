// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSub extends SubsystemBase {
  PhotonCamera camera4509;
  /** Creates a new VisionSub. */
  public VisionSub() {
    camera4509 = new PhotonCamera("photonvision4509");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
