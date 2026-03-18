package com.nanaios.polygonal_tech.client.gui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public final class FluidRenderUtil {
    private static final int TILE_SIZE = 16;

    private FluidRenderUtil() {
    }

    public static void renderFluidInTank(GuiGraphics graphics, ILongFluidTank tank, int x, int y, int width, int height) {
        if (tank == null || width <= 0 || height <= 0) {
            return;
        }

        LongFluidStack fluidStack = tank.getFluid();
        long amount = fluidStack.getLongAmount();
        long capacity = tank.getLongCapacity();

        if (fluidStack.isEmpty() || amount <= 0 || capacity <= 0) {
            return;
        }

        long scaledHeight = (long) Math.ceil((double) amount / (double) capacity * (double) height);
        long clampedHeight = Math.max(1L, Math.min(height, scaledHeight));
        int fillHeight = MathUtil.longToInt(clampedHeight);

        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
        if (stillTexture == null) {
            return;
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack);

        float alpha = (float) (tintColor >> 24 & 255) / 255.0F;
        if (alpha <= 0.0F) {
            alpha = 1.0F;
        }
        float red = (float) (tintColor >> 16 & 255) / 255.0F;
        float green = (float) (tintColor >> 8 & 255) / 255.0F;
        float blue = (float) (tintColor & 255) / 255.0F;

        int fluidTopY = y + height - fillHeight;

        RenderSystem.enableBlend();
        graphics.setColor(red, green, blue, alpha);
        graphics.enableScissor(x, fluidTopY, x + width, y + height);

        for (int xOffset = 0; xOffset < width; xOffset += TILE_SIZE) {
            int drawWidth = Math.min(TILE_SIZE, width - xOffset);
            for (int yOffset = 0; yOffset < fillHeight; yOffset += TILE_SIZE) {
                int drawY = y + height - yOffset - TILE_SIZE;
                graphics.blit(x + xOffset, drawY, 0, drawWidth, TILE_SIZE, sprite);
            }
        }

        graphics.disableScissor();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
