package dev.tucanu.pvz.gui;

import dev.tucanu.pvz.util.ModAttachments;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HudOverlay
{
    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || minecraft.options.hideGui) {
            return;
        }

        int currentSun = minecraft.player.getData(ModAttachments.SUN_STORAGE);
        String text = "Sun: " + currentSun;

        int textWidth = minecraft.font.width(text);
        int x = 10;
        int y = 10;

        // Draw background and text
        guiGraphics.fill(x - 3, y - 3, x + textWidth + 3, y + 10, 0x90000000);
        guiGraphics.fill(x - 5, y - 3, x - 3, y + 10, 0xFFFFFF00);
        guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF, true);
    }
}
