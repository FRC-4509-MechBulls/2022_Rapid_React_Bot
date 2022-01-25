// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SonarSub extends SubsystemBase {
  private final double IN_TO_CM_CONVERSION = 2.54;
  private boolean use_units; //are we using units or just returning voltage?
  private double min_voltage; //minimum voltage the ultrasonic sensor can return
  private double voltage_range; //range of velocities returned by sensor (max - min)
  private double min_distance; //minimum distance the ultrasonic sensor can return inches
  private double distance_range; //range of distances returned by this class in inches (max - min)
  AnalogInput channel;

  double voltsPerInch = 5.0 / 512.0;
  double voltage = channel.getVoltage();
  double distance;

  /** Creates a new SonarSub. */
  public SonarSub(int _channel) {
    channel = new AnalogInput(_channel);
    //default values
    use_units = true;
		min_voltage = .5;
		voltage_range = 5.0 - min_voltage;
		min_distance = 3.0;
		distance_range = 60.0 - min_distance;
  }

  public SonarSub(int _channel, boolean _use_units, double _min_voltage,
      double _max_voltage, double _min_distance, double _max_distance) {
    channel = new AnalogInput(_channel);
    //only use unit-specific variables if we're using units
    if (_use_units) {
      use_units = true;
      min_voltage = _min_voltage;
      voltage_range = _max_voltage - _min_voltage;
      min_distance = _min_distance;
      distance_range = _max_distance - _min_distance;
    }
  }

  public boolean isRequiredDistance() {
    if (((channel.getVoltage())/voltsPerInch) <= 15) {
      return true;
    }
    return false;
  }

  public double getDistance() {
    return distance = volts/voltsPerInch;
  }

  public double getVoltage() {
    return channel.getVoltage();
  }

  /* GetRangeInInches
  * Returns the range in inches
  * Returns -1.0 if units are not being used
  * Returns -2.0 if the voltage is below the minimum voltage
  */
  public double getRangeInInches() {
    double range;
    //if we're not using units, return -1, a range that will most likely never be returned
    if (!use_units) {
      return -1.0;
    }
    range = channel.getVoltage();
    if (range < min_voltage) {
      return -2.0;
    }
    //first, normalize the voltage
    range = (range - min_voltage) / voltage_range;
    //next, denormalize to the unit range
    range = (range * distance_range) + min_distance;
    return range;
  }

  /* GetRangeInCM
  * Returns the range in centimeters
  * Returns -1.0 if units are not being used
  * Returns -2.0 if the voltage is below the minimum voltage
  */

  public double getRangeInCM() {
    double range;
    //if we're not using units, return -1, a range that will most likely never be returned
    if (!use_units) {
      return -1.0;
    }
    range = channel.getVoltage();
    if (range < min_voltage) {
      return -2.0;
    }
    //first, normalize the voltage
    range = (range - min_voltage) / voltage_range;
    //next, denormalize to the unit range
    range = (range * distance_range) + min_distance;
    //finally, convert to centimeters
    range *= IN_TO_CM_CONVERSION;
    return range;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
