// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Conveyor extends SubsystemBase {
  private VictorSP mBeltMotor;
  private WPI_TalonSRX mAgitator;
  private AnalogInput topLightSensor;
  private AnalogInput bottomLightSensor;
  /** Creates a new Conveyor. */
  public Conveyor() {
    mBeltMotor = new VictorSP(Constants.MotorIDs.BELT);
    mAgitator = new WPI_TalonSRX(Constants.MotorIDs.AGITATOR);
    topLightSensor = new AnalogInput(Constants.TOP_SENSOR);
    bottomLightSensor = new AnalogInput(Constants.BOTTOM_SENSOR);
  }
  /**
   * Initialize the subsystem and it's components to their starting values
   */
  public void init(){
    mAgitator.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Top Sensor", getTopSensor());
    SmartDashboard.putBoolean("Bottom Sensor", getBottomSensor());
  }

  /**
   * Sets the voltage output of conveyor and agitator from the passed parameter
   * 
   * @param Speed range -1 to 1
   */
  public void moveConveyor (double speed) {
    setConveyor(speed);
    if(speed > 0) {
      setAgitator(-0.5);
    } else if(speed < 0) {
      setAgitator(0.5);
    } else {
      stop();
    }

  }
  /**
   * Stops all motors in the Conveyor subsystem
   */
  public void stop(){
    setConveyor(0);
    setAgitator(0);
  }

  /**
   * Sets the speed of the agitator
   * @param speed
   * @return the actual speed of the motor
   */
  private double setAgitator(double speed) {
    mAgitator.set(speed);
    return mAgitator.get();
  }

  /**
   * Sets the speed of the belt motor
   * @param speed
   * @return the actual speed of the motor
   */
  private double setConveyor(double speed) {
    mBeltMotor.set(speed);
    return mBeltMotor.get();
  }

  // Getter Methods
  /**
   * Gets whether the top sensor is triggured
   * @return
   */
  public boolean getTopSensor(){
    return topLightSensor.getVoltage() < 0.15;
  }
  /**
   * Gets whether the bottom sensor is triggered
   * @return
   */
  public boolean getBottomSensor(){
    return bottomLightSensor.getVoltage() > 0.6;
  }
}
