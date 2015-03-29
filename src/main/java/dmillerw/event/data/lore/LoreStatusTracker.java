package dmillerw.event.data.lore;

import com.google.common.collect.Sets;
import dmillerw.event.EventMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

import java.util.Set;

/**
 * @author dmillerw
 */
public class LoreStatusTracker implements IExtendedEntityProperties {

    public static void attachStatusTracker(EntityPlayer entityPlayer) {
        entityPlayer.registerExtendedProperties(EventMod.ID + ":status", new LoreStatusTracker());
    }

    public static LoreStatusTracker getStatusTracker(EntityPlayer entityPlayer) {
        return (LoreStatusTracker) entityPlayer.getExtendedProperties(EventMod.ID + ":status");
    }

    public Set<String> receivedLore = Sets.newHashSet();

    @Override
    public void init(Entity entity, World world) {

    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagList nbtTagList = new NBTTagList();
        for (String str : receivedLore) {
            nbtTagList.appendTag(new NBTTagString(str));
        }

        compound.setTag("receivedLore", nbtTagList);
        compound.setInteger("length", receivedLore.size());
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        receivedLore.clear();

        NBTTagList nbtTagList = compound.getTagList("receivedLore", Constants.NBT.TAG_STRING);
        for (int i=0; i<compound.getInteger("length"); i++) {
            receivedLore.add(nbtTagList.getStringTagAt(i));
        }
    }

    public boolean hasBeenRead(String ident) {
        return receivedLore.contains(ident);
    }

    public void markAsRead(String ident) {
        receivedLore.add(ident);
    }
}
