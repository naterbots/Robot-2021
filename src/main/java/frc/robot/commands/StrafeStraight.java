// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class StrafeStraight extends CommandBase {
  private Drivetrain drivetrain;
  private double ySpeed, gyroTarget, zMultiplier = 0.02;

  /** 
   * Creates a new StrafeStraight.
   * Strafes while correcting for rotation using the current gyro angle
   * @param ySpeed strafe speed
   */
  public StrafeStraight(double ySpeed) {
    this.drivetrain = RobotContainer.drivetrain;
    this.ySpeed = ySpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  /** 
   * Creates a new StrafeStraight.
   * Strafes while correcting for rotation using the current gyro angle
   * @param ySpeed strafe speed
   * @param gyroTarget the gyro angle to target
   */
  public StrafeStraight(double ySpeed, double gyroTarget){
    this.drivetrain = RobotContainer.drivetrain;
    this.ySpeed = ySpeed;
    this.gyroTarget = gyroTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // If the gyro target wasn't specified, use the current angle instead.
    if ((Double)gyroTarget == null){
      gyroTarget = drivetrain.getGyroAngle();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(ySpeed, 0, (gyroTarget - drivetrain.getGyroAngle()) * zMultiplier);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
