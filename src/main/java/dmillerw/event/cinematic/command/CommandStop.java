package dmillerw.event.cinematic.command;

import dmillerw.event.cinematic.client.ClientTickHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 * @author dmillerw
 */
public class CommandStop extends CommandBase {

    @Override
    public String getCommandName() {
        return "stop";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        ClientTickHandler.stopCinematic();
    }
}
