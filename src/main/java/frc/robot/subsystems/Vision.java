package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
    private final NetworkTable m_limelight = NetworkTableInstance.getDefault().getTable("limelight");

    private final NetworkTableEntry m_botpose = m_limelight.getEntry("botpose");

    public Pose2d getBotPose() {
        double[] botPoseData = m_botpose.getDoubleArray(new double[6]);
        return new Pose2d(botPoseData[0], botPoseData[1], Rotation2d.fromDegrees(botPoseData[5]));
    }
}
