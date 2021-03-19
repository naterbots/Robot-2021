// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Joystick IDs
    public static final int OP_STICK_ID = 0;
    public static final int COOP_STICK_ID = 1;

    // ***** Motor IDs ***** //
    public final class MotorIDs{
        // Drivetrain
        public static final int DRIVE_FRONTLEFT = 2;                // TalonFX - CAN ID
        public static final int DRIVE_FRONTRIGHT = 1;               // TalonFX - CAN ID
        public static final int DRIVE_REARLEFT = 3;                 // TalonFX - CAN ID
        public static final int DRIVE_REARRIGHT = 0;                // TalonFX - CAN ID
        // Shooter
        public static final int SHOOTER = 4;             // TalonFX - CAN ID
        public static final int SLIDER = 8;      // TalonSRX - CAN ID 
        // Intake
        public static final int INTAKE = 5;     // VictorSP - RIO PWM Port
        // Conveyor
        public static final int BELT = 6;       // VictorSP - RIO PWM Port
        public static final int AGITATOR = 7;   // TalonSRX - CAN ID
    }
    
    // ***** Subsystem Costants ***** //
    public  static final int ENCODER_TICK_PER_REV= 2048;
    // Drivetrain
    public static final double DRIVE_MIN_VOLT = 0.4;
    public static final double WHEEL_DIAMETER = 6.0;
      
    // Conveyor
    public static final int TOP_SENSOR = 0;           // Analog Input - RIO Analog Port
    public static final int MIDDLE_SENSOR = 1;        // Analog Input - RIO Analog Port
    public static final int BOTTOM_SENSOR = 2;        // Analog Input - RIO Analog Port
}
