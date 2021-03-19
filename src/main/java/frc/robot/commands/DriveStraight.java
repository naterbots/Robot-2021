// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

/**
 * This command uses the Drivetrain subsystem to Drive forward or backward,
 * while correcting for rotation using the gyro.
 */
public class DriveStraight extends CommandBase {
  private Drivetrain drivetrain;
  private double xSpeed, zMultiplier = 0.04, gyroTarget;

  /**
   * Creates a new DriveStraight. Drives the robot straight, using the current
   * gyro angle to correct for rotation
   * 
   * @param pXSpeed Forward Speed
   */
  public DriveStraight(double xSpeed) {
    this.drivetrain = RobotContainer.drivetrain;
    this.xSpeed = xSpeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  /**
   * Drives the robot straight, using a specified gyro angle to correct for
   * rotation
   * 
   * @param pXSpeed     Forward Speed
   * @param pGyroTarget The gyro angle to Target
   */
  public DriveStraight(double xSpeed, double gyroTarget) {
    this.drivetrain = RobotContainer.drivetrain;
    this.xSpeed = xSpeed;
    this.gyroTarget = gyroTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // If the gyro target wasn't specified, use the current angle instead.
    if ((Double) gyroTarget == null) {
      gyroTarget = drivetrain.getGyroAngle();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Sends the voltage to the Drivetrain motors
    double currentAngle = drivetrain.getGyroAngle();
    drivetrain.driveWithoutStrafe(xSpeed, -(currentAngle - gyroTarget) * zMultiplier);
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
