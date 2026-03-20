package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* at this point, anything is a subsystem... */
public class LightSubsystem extends SubsystemBase {
    private final Spark m_ledStrip = new Spark(0);
    
    public LightSubsystem() {
        setDefaultCommand(doNothing());
    }

    /**
     * Turns the LED strip off.
     * @return The relevant command.
     */
    public Command doNothing() {
        return this.run(() -> m_ledStrip.set(0));
    }

    /**
     * Sets the LED strip to strobe red. Note that this will technically strobe whatever color 1 is set to.
     * @return The relevant command.
     */
    public Command strobeRed() {
        return this.run(() -> m_ledStrip.set(0.15));
    }

    /**
     * Sets the LED strip to be solid red.
     * @return The relevant command.
     */
    public Command solidRed() {
        return this.run(() -> m_ledStrip.set(0.61));
    }

    /**
     * Sets the LED strip to be solid green.
     * @return The relevant command.
     */
    public Command solidGreen() {
        return this.run(() -> m_ledStrip.set(0.77));
    }
}
