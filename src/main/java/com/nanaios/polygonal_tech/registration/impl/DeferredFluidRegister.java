package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.registries.PolygonalTechFluidTypeRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class DeferredFluidRegister extends WrapperDeferredRegister<Fluid> {
    private static final Map<NamedToken, RegistryObject<Fluid>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<Fluid>, NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredFluidRegister(DeferredRegister<Fluid> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    @SuppressWarnings("unchecked")
    public <I extends Fluid> RegistryObject<I> register(NamedToken token) {
        RegistryObject<FluidType> typeRegistry = PolygonalTechFluidTypeRegister.BASE_FLUID_TYPES.getRegistry(token);
        if (typeRegistry == null) {
            throw new IllegalStateException("No FluidType registered for token: " + token);
        }

        RegistryObject<Fluid> sourceFluid = null;
        RegistryObject<Fluid> flowingFluid = null;

        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(typeRegistry,sourceFluid,flowingFluid);

        flowingFluid = super.register(token.name() + "_flowing",() -> new ForgeFlowingFluid.Flowing(properties));
        sourceFluid = super.register(token,() -> new ForgeFlowingFluid.Source(properties));

        return (RegistryObject<I>) sourceFluid;
    }
}
