package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class DeferredBlockEntityTypeRegister extends WrapperDeferredRegister<BlockEntityType<?>> {
    public DeferredBlockEntityTypeRegister(DeferredRegister<BlockEntityType<?>> deferredRegister) {
        super(deferredRegister);
    }
}