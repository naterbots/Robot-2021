// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.xml.validation.Validator;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  private VictorSP mMotor;
  private DoubleSolenoid mExtender;
  /** Creates a new Intake. */
  public Intake() {
    mMotor = new VictorSP(Constants.MotorIDs.INTAKE);
    mExtender = new DoubleSolenoid(1, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Sets the intake motor to inwards
   */
  public double in() {
    mMotor.set(1);
    return mMotor.get();
  }

  /**
   * Sets the intake motor to outwards
   */
  public double out() {
    mMotor.set(-1);
    return mMotor.get();
  }

  /**
   * Stops the intake motor
   */
  public double stop() {
    mMotor.set(0);
    return mMotor.get();
  }

  /**
   * Extends the extender
   */
  public Value extend(){
    mExtender.set(Value.kForward);
    return mExtender.get();
  }

  /**
   * Retracts the extender
   */
  public Value retract(){
    mExtender.set(Value.kReverse);
    return mExtender.get();
  }

}
