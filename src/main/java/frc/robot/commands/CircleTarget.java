// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;

/**
 * This command uses the Drivetrain and Vision subsystems to rotate the robot around a vision target.
 */
public class CircleTarget extends CommandBase {
  //Subsystems
  private Drivetrain drivetrain = RobotContainer.drivetrain;
  private Vision vision = RobotContainer.vision;
  private double yTarget, targetAngle, strafeSpeed, forwardSpeed, 
    maxForwardSpeed = 0.6,
    mForwardMultiplier = 0.05,
    mRotationMultiplier = 0.02;

  /** Creates a new Rotate. */
  public CircleTarget(double strafeSpeed, double yTarget) {
    this.strafeSpeed = strafeSpeed;
    this.yTarget = yTarget;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    addRequirements(vision);
  }
  public CircleTarget(double strafeSpeed, double yTarget, double targetAngle) {
    this.strafeSpeed = strafeSpeed;
    this.yTarget = yTarget;
    this.targetAngle = targetAngle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    addRequirements(vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Do nothing
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Sets the forward speed based on the Y position of the target
    if(yTarget < vision.getTargetY()){
      forwardSpeed = (Math.abs(yTarget - vision.getTargetY())) * mForwardMultiplier;
    }else if(yTarget > vision.getTargetY()){
      forwardSpeed = -(Math.abs(yTarget - vision.getTargetY())) * mForwardMultiplier;
    }else{
      forwardSpeed = 0;
    }
    //Checks to make sure the speed is less than the max speed
    if(forwardSpeed > maxForwardSpeed){
      forwardSpeed = maxForwardSpeed;
    } else if (forwardSpeed < -maxForwardSpeed){
      forwardSpeed = -maxForwardSpeed;
    }

    //Updates the speed of the Drivetrain motors
    drivetrain.drive(strafeSpeed, forwardSpeed, vision.getTargetX() * mRotationMultiplier);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
