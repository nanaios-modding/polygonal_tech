package com.nanaios.polygonal_tech.lib.registration.impl;

import com.nanaios.polygonal_tech.lib.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DeferredFluidRegister extends WrapperDeferredRegister<Fluid> {
    private static final Map<NamedToken, RegistryObject<Fluid>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<Fluid>, NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredFluidRegister(DeferredRegister<Fluid> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    @SuppressWarnings("unchecked")
    public <I extends Fluid> RegistryObject<I> register(NamedToken token,RegistryObject<FluidType> typeRegistry) {
        RegistryObject<Fluid>[] fluids = new RegistryObject[2];

        Supplier<Fluid> sourceSupplier = () -> fluids[0].get();
        Supplier<Fluid> flowingSupplier = () -> fluids[1].get();

        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(typeRegistry,sourceSupplier,flowingSupplier);

        fluids[0] = super.register(token,() -> new ForgeFlowingFluid.Source(properties));
        fluids[1] = super.register(token.name() + "_flowing",() -> new ForgeFlowingFluid.Flowing(properties));

        return (RegistryObject<I>) fluids[0];
    }
}
