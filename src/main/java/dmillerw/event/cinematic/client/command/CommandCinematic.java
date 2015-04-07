package dmillerw.event.cinematic.client.command;

import dmillerw.event.cinematic.Cinematic;
import dmillerw.event.cinematic.CinematicBuilder;
import dmillerw.event.cinematic.Point;
import dmillerw.event.cinematic.client.ClientTickHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

/**
 * @author dmillerw
 */
public class CommandCinematic extends CommandBase {

    private static final String NAME = "cinematic";
    private static final List<String> ALIASES = Arrays.asList("c");

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List getCommandAliases() {
        return ALIASES;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("mark")) {
                if (!check(sender, "You must create a new cinematic first")) {
                    return;
                }

                final Point point = Point.fromPlayer();
//                sender.addChatMessage(new ChatComponentText(point.toString()));

                ClientTickHandler.currentBuildingCinematic.addPoint(point);
            } else if (args[0].equals("build")) {
                if (!check(sender, "You must create a new cinematic first")) {
                    return;
                }

                final String name = ClientTickHandler.currentBuildingCinematic.name;
                Cinematic cinematic = ClientTickHandler.currentBuildingCinematic.build();
                ClientTickHandler.cinematicMap.put(name, cinematic);
                ClientTickHandler.currentBuildingCinematic = null;
            } else if (args[0].equals("stop")) {
                ClientTickHandler.stopCinematic();
            } else if (args[0].equals("loop")) {
                if (!check(sender, "You must create a new cinematic first")) {
                    return;
                }

                ClientTickHandler.currentBuildingCinematic.loop();
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("new")) {
                ClientTickHandler.currentBuildingCinematic = new CinematicBuilder();
                ClientTickHandler.currentBuildingCinematic.setName(args[1]);
            } else if (args[0].equals("play")) {
                final Cinematic cinematic = ClientTickHandler.cinematicMap.get(args[1]);
                if (cinematic == null) {
                    error(sender, "Could not find a cinematic with the name '" + args[1] + "'");
                    return;
                }

                ClientTickHandler.startCinematic(cinematic);
            } else if (args[0].equals("speed")) {
                if (!check(sender, "You must create a new cinematic first")) {
                    return;
                }

                ClientTickHandler.currentBuildingCinematic.setSpeed(Integer.parseInt(args[1]));
            }
        }
    }

    private boolean check(ICommandSender sender, String msg) {
        if (ClientTickHandler.currentBuildingCinematic == null) {
            error(sender, msg);
            return false;
        } else {
            return true;
        }
    }

    private void error(ICommandSender commandSender, String msg) {
        commandSender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED.getFormattingCode() + "[ERROR]: " + EnumChatFormatting.RESET.getFormattingCode() + msg));
    }
}
