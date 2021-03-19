// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;


public class FieldOrientedDrive extends CommandBase {
  private Drivetrain drivetrain;
  private double xSpeed, ySpeed, zSpeed;
  /**
   * Creates a new FieldOrientedDrive.
   * @param xSpeed speed in the X direction
   * @param ySpeed speed in the Y direction
   */
  public FieldOrientedDrive(double xSpeed, double ySpeed) {
    drivetrain = RobotContainer.drivetrain;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    // Use addRequirements() here to declare subsystem dependencies.    
    addRequirements(drivetrain);
  }
  /**
   * Creates a new FieldOrientedDrive.
   * @param xSpeed speed in the X direction
   * @param ySpeed speed in the Y direction
   */
  public FieldOrientedDrive(double xSpeed, double ySpeed, double zSpeed) {
    drivetrain = RobotContainer.drivetrain;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    this.zSpeed = zSpeed;
    // Use addRequirements() here to declare subsystem dependencies.    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if((Double)zSpeed==null)zSpeed = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drive(xSpeed, ySpeed, zSpeed, drivetrain.getGyroAngle());
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
