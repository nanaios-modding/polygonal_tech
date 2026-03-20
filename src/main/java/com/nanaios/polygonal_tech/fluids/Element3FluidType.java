package com.nanaios.polygonal_tech.fluids;

import com.nanaios.polygonal_tech.PolygonalTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class Element3FluidType extends FluidType {
    private static final ResourceLocation STILL_TEXTURE = PolygonalTech.rl("fluid/element3");
    private static final ResourceLocation FLOWING_TEXTURE = PolygonalTech.rl("fluid/element3");
    private static final int TINT_COLOR = 0xFFFFFFFF;

    public Element3FluidType() {
        super(Properties.create());
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Override
            public int getTintColor() {
                return TINT_COLOR;
            }
        });
    }
}

