package org.torc.mainrobot.program;

import org.torc.mainrobot.robot.commands.auton.DriveStraight;
import org.torc.mainrobot.robot.commands.auton.TestAutonCommand;
import org.torc.mainrobot.robot.subsystems.DriveTrain.DTSide;
import org.torc.mainrobot.tools.CommandList;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonMode {
	
	public static DigitalInput testInp = new DigitalInput(9);
	
	public static void Init() {
		//RobotMap.DriveSubsystem.zeroGyro();
		
		CommandList testCom = new CommandList();
		testCom.addCommand(new TestAutonCommand("Going to start driving in a few seconds!!"));
		testCom.addCommand(new DriveStraight(RobotMap.DriveSubsystem, 100, 0.5));
		testCom.start();
	}
	
	public static void Periodic() {
		
		SmartDashboard.putBoolean("InputEye", testInp.get());
		
		SmartDashboard.putNumber("RightEnc", RobotMap.DriveSubsystem.getEncoder(DTSide.right));
		SmartDashboard.putNumber("LeftEnc", RobotMap.DriveSubsystem.getEncoder(DTSide.left));
		
		//SmartDashboard.putNumber("FusionAngle", RobotMap.DriveSubsystem.getGyroHeader());

	}
}