package org.torc.mainrobot.teleopcontrols;

import org.torc.mainrobot.program.RobotMap;
import org.torc.mainrobot.program.ButtonMap.GetType;
import org.torc.mainrobot.program.ButtonMap.RCAxis;
import org.torc.mainrobot.program.ButtonMap.RCButtons;
import org.torc.mainrobot.robot.ControlledStateMachine;
import org.torc.mainrobot.robot.subsystems.Elevator.ElevatorPositions;
import org.torc.mainrobot.robot.subsystems.UltraGrabber.GrabberPositions;

public class DriveTrain_Teleop extends ControlledStateMachine {
	
	private int lowGearTime = 0;
	
	private final int lowGearWait = 100 / 20;
	
	private boolean lowShiftLastVal = false;
	
	@Override
	public void execute() {
		// ez halo drive. ;)
		RobotMap.DriveSubsystem.haloDrive(-RobotMap.driverControl.getAxis(RCAxis.leftY), RobotMap.driverControl.getAxis(RCAxis.rightX), true);
		
		/*
		// Toggle shifters high/low
		if (RobotMap.driverControl.getButton(RCButtons.toggleShifters, GetType.pressed)) {
			RobotMap.DriveSubsystem.setShifters(!RobotMap.DriveSubsystem.getShifters());
		}
		*/
		
		// Open hook and raise grabber
		if (RobotMap.driverControl.getButton(RCButtons.hookRelease, GetType.pressed)) {
			RobotMap.GrabberSubsystem.findGrabberPosition(GrabberPositions.up);
			RobotMap.ClimbingHook.openHook();
		}
		
		// Go to climb position
		if (RobotMap.driverControl.getButton(RCButtons.elevClimb, GetType.pressed)) {
			RobotMap.ElevSubsystem.positionFind(ElevatorPositions.climb);
			//RobotMap.ElevSubsystem.positionFind(ElevatorPositions.high);
		}
		
		boolean shifterLowVal = RobotMap.driverControl.getButton(RCButtons.shiftersLow, GetType.normal);
		
		// lowGearHold
		if (shifterLowVal) {
			if (lowGearTime != -1) {
				lowGearTime++;
			}
		}
		else {
			lowGearTime = 0;
		}
		
		if (!shifterLowVal && lowShiftLastVal) {
			RobotMap.DriveSubsystem.setShifters(false);
		}
		
		if (lowGearTime >= lowGearWait) {
			lowGearTime = -1;
			RobotMap.DriveSubsystem.setShifters(true);
		}
		
		lowShiftLastVal = shifterLowVal;
	}
	
}
