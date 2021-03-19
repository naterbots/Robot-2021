// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  private WPI_TalonFX mShooterMotor;
  private WPI_TalonSRX mSlider;
  /** Creates a new Shooter. */
  public Shooter() {
    mShooterMotor = new WPI_TalonFX(Constants.MotorIDs.SHOOTER);
    mSlider  = new WPI_TalonSRX(Constants.MotorIDs.SLIDER);
  }

  public void init(){
    // Sets the Neutral Mode of the motors
    mShooterMotor.setNeutralMode(NeutralMode.Coast);
    //Reset the encoders
    mShooterMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter RPM", getRPM());
  }

  /**
   * Sets the voltage output of the motor where the output is between -1.0 and 1.0, with 0.0 as stopped
   * @param speed
   */
  public void setMotorPercentage(double speed){
    mShooterMotor.set(ControlMode.PercentOutput, speed);
  }

  // Getter Methods
  /**
   * Get the current RPM of the shoot motor
   * @return RPM
   */
  public double getRPM(){
    return 600 * mShooterMotor.getSelectedSensorVelocity()/Constants.ENCODER_TICK_PER_REV;
  }
}
