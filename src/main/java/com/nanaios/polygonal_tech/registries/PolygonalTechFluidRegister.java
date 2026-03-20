package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.fluids.Element3FluidType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechFluidRegister {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PolygonalTech.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, PolygonalTech.MODID);

    public static final RegistryObject<FluidType> ELEMENT3_TYPE = FLUID_TYPES.register("element3", Element3FluidType::new);
    public static final RegistryObject<FlowingFluid> ELEMENT3 = FLUIDS.register("element3", () -> new ForgeFlowingFluid.Source(getElement3Properties()));
    public static final RegistryObject<FlowingFluid> FLOWING_ELEMENT3 = FLUIDS.register("flowing_element3", () -> new ForgeFlowingFluid.Flowing(getElement3Properties()));

    private static ForgeFlowingFluid.Properties element3Properties;

    private static ForgeFlowingFluid.Properties getElement3Properties() {
        if (element3Properties == null) {
            element3Properties = new ForgeFlowingFluid.Properties(ELEMENT3_TYPE, ELEMENT3, FLOWING_ELEMENT3)
                    .slopeFindDistance(2)
                    .levelDecreasePerBlock(2)
                    .tickRate(20);
        }
        return element3Properties;
    }
}

