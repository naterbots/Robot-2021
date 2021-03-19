// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private WPI_TalonFX mShooterMotor;
  private WPI_TalonSRX mSlider;
  private AnalogInput mPotentiometer;
  private Zones selectedZone;
  private ZoneAnalogPosition[] zoneList;

  /** Creates a new Shooter. */
  public Shooter() {
    mShooterMotor = new WPI_TalonFX(Constants.MotorIDs.SHOOTER);
    mSlider = new WPI_TalonSRX(Constants.MotorIDs.SLIDER);
    mPotentiometer = new AnalogInput(3);
    // Zone List
    zoneList = new ZoneAnalogPosition[4];
    zoneList[0] = new ZoneAnalogPosition(Zones.GREEN, 2.90); // 2.85
    zoneList[1] = new ZoneAnalogPosition(Zones.YELLOW, 2.95);
    zoneList[2] = new ZoneAnalogPosition(Zones.BLUE, 3.67);
    zoneList[3] = new ZoneAnalogPosition(Zones.RED, 3.70);
    // Select Zone
    selectedZone = Zones.REINTRODUCTION_ZONE;
  }

  public void init() {
    // Sets the Neutral Mode of the motors
    mShooterMotor.setNeutralMode(NeutralMode.Coast);
    // Reset the encoders
    mShooterMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // Moves slider to current zone
    for (ZoneAnalogPosition target : zoneList) {
      if (target.zone.equals(selectedZone))
        moveSlideTo(target);
    }
    SmartDashboard.putNumber("Shooter RPM", getRPM());
    SmartDashboard.putString("Selected Zone", selectedZone.toString());
  }

  /**
   * Sets the voltage output of the motor where the output is between -1.0 and
   * 1.0, with 0.0 as stopped
   * 
   * @param speed
   */
  public void setMotorPercentage(double speed) {
    mShooterMotor.set(ControlMode.PercentOutput, speed);
  }

  // Getter Methods
  /**
   * Get the current RPM of the shoot motor
   * 
   * @return RPM
   */
  public double getRPM() {
    return 600 * mShooterMotor.getSelectedSensorVelocity() / Constants.ENCODER_TICK_PER_REV;
  }

  /**
   * Set the speed of the slider
   * 
   * @param speed
   * @return actual percentage
   */
  private double setSliderSpeed(double speed) {
    mSlider.set(speed);
    return mSlider.get();
  }

  /**
   * Moves slider to desired position for the selected zone
   * 
   * @param zone
   */
  private void moveSlideTo(ZoneAnalogPosition zone) {
    if (mPotentiometer.getVoltage() > zone.analogTarget + 0.02) {
      // If Flap is too far forward, then go backward
      setSliderSpeed(0.5);
    } else if (mPotentiometer.getVoltage() < zone.analogTarget - 0.02) {
      // If Flap is too far backward then go forward
      setSliderSpeed(-0.5);
    } else {
      // Otherwise Step
      setSliderSpeed(0);
    }
  }

  public Zones setFlapToGreen() {
    this.selectedZone = Zones.GREEN;
    return this.selectedZone;
  }

  public Zones setFlapToYellow() {
    this.selectedZone = Zones.YELLOW;
    return this.selectedZone;
  }

  public Zones setFlapToBlue() {
    this.selectedZone = Zones.BLUE;
    return this.selectedZone;
  }

  public Zones setFlapToRed() {
    this.selectedZone = Zones.RED;
    return this.selectedZone;
  }

  // This will be where the Zone stuff is
  /**
   * The color of zones for shooting during Autonomous
   */
  public enum Zones {
    GREEN, // 0 - 3
    YELLOW, // 3 - 5
    BLUE, // 5 - 7
    RED, // 7 - 9
    REINTRODUCTION_ZONE // 9 - End
  }

  private class ZoneAnalogPosition {
    private double analogTarget;
    Zones zone;

    public ZoneAnalogPosition(Zones zone, double analogTarget) {
      this.zone = zone;
      this.analogTarget = analogTarget;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Zones)) {
        return false;
      }
      return this.zone.compareTo((Zones) o) == 0;
    }

    @Override
    public String toString() {
      return this.zone + ":" + this.analogTarget;
    }
  }
}
