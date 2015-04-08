package dmillerw.event.cinematic.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.event.cinematic.data.Cinematic;
import dmillerw.event.cinematic.data.Point;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class ClientEventHandler {

    public static void translateToWorldCoords(Entity entity, float frame) {
        double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
        double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
        double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
        GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        final Cinematic displayed = ClientTickHandler.getDisplayedCinematic();

        if (displayed != null) {
            GL11.glPushMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            translateToWorldCoords(Minecraft.getMinecraft().renderViewEntity, event.partialTicks);

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(GL11.GL_LINE_STRIP);
            tessellator.setColorOpaque(255, 255, 255);

            for (int i=0; i<displayed.points.size(); i++) {
                final Point p = displayed.points.get(i);
                tessellator.addVertex(p.x, p.y, p.z);
            }

            tessellator.draw();

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null) {
            if (event.type != RenderGameOverlayEvent.ElementType.CHAT) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null && cinematic.hidePlayer) {
            if (event.entityPlayer.getGameProfile().getId() == Minecraft.getMinecraft().thePlayer.getGameProfile().getId()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayerTag(RenderPlayerEvent.Specials event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null) {
            event.setCanceled(true);
        }
    }
}
