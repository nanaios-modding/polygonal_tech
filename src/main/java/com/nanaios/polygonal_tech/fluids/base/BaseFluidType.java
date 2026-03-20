package com.nanaios.polygonal_tech.fluids.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

public class BaseFluidType extends FluidType {
    public final int tintColor;
    public final ResourceLocation stillTexture;
    public final ResourceLocation flowingTexture;

    public BaseFluidType(ResourceLocation stillTexture) {
        this(stillTexture, stillTexture, 0xFFFFFFFF);

    }

    public BaseFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int tintColor) {
        super(Properties.create());
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.tintColor = tintColor;
    }

    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return stillTexture;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return flowingTexture;
                }

                @Override
                public int getTintColor() {
                    return tintColor;
                }
            });
    }
}
