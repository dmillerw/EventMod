package dmillerw.event.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.event.EventMod;
import dmillerw.event.network.packet.S01PlayLoreAudio;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EventMod.ID);

    public static void initialize() {
        INSTANCE.registerMessage(S01PlayLoreAudio.class, S01PlayLoreAudio.class, 0, Side.CLIENT);
    }
}
