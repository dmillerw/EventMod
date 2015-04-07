package dmillerw.event.lore.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.event.lore.client.SoundHandler;
import dmillerw.event.lore.data.lore.LoreRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class S01PlayLoreAudio implements IMessage, IMessageHandler<S01PlayLoreAudio, IMessage> {

    public String ident;

    public S01PlayLoreAudio() {

    }

    public S01PlayLoreAudio(String ident) {
        this.ident = ident;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            packetBuffer.writeStringToBuffer(ident);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            ident = packetBuffer.readStringFromBuffer(256);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(S01PlayLoreAudio message, MessageContext ctx) {
        SoundHandler.INSTANCE.play(LoreRegistry.getLore(message.ident).audioPath);
        return null;
    }
}
