package dmillerw.event.cinematic.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.event.cinematic.data.Cinematic;
import dmillerw.event.cinematic.data.Point;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.*;

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

            for (int i=0; i<displayed.points.size(); i++) {
                final String str = Integer.toString(i);
                final Point p = displayed.points.get(i);

                FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
                float f = 1.6F;
                float f1 = 0.016666668F * f;
                GL11.glPushMatrix();
                GL11.glTranslatef((float)p.x + 0.0F, (float)p.y + 0.35F, (float)p.z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                GL11.glScalef(-f1, -f1, f1);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                byte b0 = 0;

                GL11.glDisable(GL11.GL_TEXTURE_2D);
                tessellator.startDrawingQuads();
                int j = fontrenderer.getStringWidth(str) / 2;
                tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                tessellator.addVertex((double)(-j - 1), (double)(-1 + b0), 0.0D);
                tessellator.addVertex((double)(-j - 1), (double)(8 + b0), 0.0D);
                tessellator.addVertex((double)(j + 1), (double)(8 + b0), 0.0D);
                tessellator.addVertex((double)(j + 1), (double)(-1 + b0), 0.0D);
                tessellator.draw();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, 553648127);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, -1);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glPopMatrix();
            }
            
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null) {
            if (event.type != CHAT && event.type != TEXT) {
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
    public void onRenderNametag(RenderLivingEvent.Specials.Pre event) {
        final Cinematic cinematic = ClientTickHandler.getPlayingCinematic();
        if (cinematic != null) {
            event.setCanceled(true);
        }
    }
}
