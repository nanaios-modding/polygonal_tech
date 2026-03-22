package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = PolygonalTech.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DeferredBlockEntityTypeRegister extends WrapperDeferredRegister<BlockEntityType<?>> {
    private static final Map<NamedToken, RegistryObject<BlockEntityType<?>>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<BlockEntityType<?>>,NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    public DeferredBlockEntityTypeRegister(DeferredRegister<BlockEntityType<?>> deferredRegister) {
        super(deferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            NamedToken name,
            BlockEntityType.BlockEntitySupplier<T> supplier,
            Supplier<Block> blocks
    ) {
        return super.register(
                name,
                () -> BlockEntityType.Builder.of(supplier, blocks.get()).build(null)
        );
    }

    public static <T extends BlockEntity>NamedToken getToken(RegistryObject<BlockEntityType<T>> registryObject) {
        return REVERSE_REGISTRY_OBJECT_MAP.get(registryObject);
    }
}