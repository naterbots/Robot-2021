// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

public class ShooterRampUp extends CommandBase {
  private Shooter shooter;
  private long startTime, currentTime;
  private double rampTime, targetValue, currentValue;
  /** Creates a new ShooterRampUp. */
  public ShooterRampUp(double targetVoltage, Double seconds) {
    shooter = RobotContainer.shooter;
    this.targetValue = targetVoltage;
    this.rampTime = seconds;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = System.currentTimeMillis();
    currentValue = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = System.currentTimeMillis();
    if(currentValue < targetValue){
      currentValue = (targetValue / rampTime) * (currentTime - startTime);
      shooter.setMotorPercentage(currentValue);
    } else{
      currentValue = targetValue;
      shooter.setMotorPercentage(currentValue);
    }
    SmartDashboard.putNumber("Shooter Voltage", currentValue);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
