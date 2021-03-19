// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private WPI_TalonFX mFrontLeft;
  private WPI_TalonFX mFrontRight;
  private WPI_TalonFX mRearLeft;
  private WPI_TalonFX mRearRight;
  private MecanumDrive mDrive;
  private AHRS mGyro;
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    mFrontLeft = new WPI_TalonFX(Constants.MotorIDs.DRIVE_FRONTLEFT);    
    mFrontRight = new WPI_TalonFX(Constants.MotorIDs.DRIVE_FRONTRIGHT);
    mRearLeft = new WPI_TalonFX(Constants.MotorIDs.DRIVE_REARLEFT);
    mRearRight = new WPI_TalonFX(Constants.MotorIDs.DRIVE_REARRIGHT);
    mDrive = new MecanumDrive(mFrontLeft, mRearLeft, mFrontRight, mRearRight);
    mGyro = new AHRS();
  }
  /**
   * Initialize the drivetrain class and it's components to their starting values
   */
  public void init(){
    // Sets the Neutral Mode of the motors
    mFrontRight.setNeutralMode(NeutralMode.Brake);
    mRearRight.setNeutralMode(NeutralMode.Brake);
    mFrontLeft.setNeutralMode(NeutralMode.Brake);
    mRearLeft.setNeutralMode(NeutralMode.Brake);
    // Sets whether or not the motors are inverted
    mFrontRight.setInverted(true);
    mRearRight.setInverted(true);
    mFrontLeft.setInverted(true);
    mRearLeft.setInverted(true);
    //Reset the encoders
    zeroEncoders();
    //Zero the Gyro
    mGyro.resetDisplacement();
    mGyro.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Gyro Angle", getGyroAngle());
    SmartDashboard.putNumber("Encoder Avg", getEncoderAverage());
  }

  /**
   * Drive method for Mecanum platform.
   * Angles are measured clockwise from the positive X axis. The robot's speed is independent from its angle or rotation rate.
   * 
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Right is positive.
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Forward is positive.
   * @param zSpeed The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   * @return the average percent of the motors
   */
  public double drive(double xSpeed, double ySpeed, double zSpeed){
    mDrive.driveCartesian(ySpeed, xSpeed, zSpeed);
    return (mFrontLeft.get()+mFrontRight.get()+mRearLeft.get()+mRearRight.get())/4;
  }

  /**
   * Drives without strafe
   * @param pXSpeed forward speed
   * @param pZSpeed rotational speed
   * @return the average percent of the motors
   */
  public double driveWithoutStrafe(double xSpeed, double zSpeed){
    return drive(xSpeed,0,zSpeed);
  }

  /**
   * Stops all motors
   * @return the average percent of the motors
   */
  public double stop(){
    mDrive.stopMotor();
    return (mFrontLeft.get()+mFrontRight.get()+mRearLeft.get()+mRearRight.get())/4;
  }

  /**
   * Sets the value of the Drivetrain encoders to 0
   */
  public void zeroEncoders(){
    mRearRight.setSelectedSensorPosition(0);
    mRearLeft.setSelectedSensorPosition(0);
    mFrontRight.setSelectedSensorPosition(0);
    mFrontLeft.setSelectedSensorPosition(0);
  }

  //Getter Methods
  /**
   * Gets gyro angle from the NavX
   * @return The gyro angle
   */
  public double getGyroAngle() {
    return mGyro.getAngle();
  }

  /**
   * Gets the average value from the 4 drivetrain motor encoders
   * @return average value
   */
  public int getEncoderAverage(){
    return (int) Math.round(Math.abs(mRearRight.getSelectedSensorPosition()) + 
      Math.abs(mRearLeft.getSelectedSensorPosition()) + 
      Math.abs(mFrontRight.getSelectedSensorPosition()) + 
      Math.abs(mFrontLeft.getSelectedSensorPosition()) / 4);

  }
}
