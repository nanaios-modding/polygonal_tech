package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolyTechBlockRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class DeferredBlockEntityTypeRegister extends WrapperDeferredRegister<BlockEntityType<?>> {
    public static final HashMap<Integer, RegistryObject<BlockEntityType<?>>> blockEntityTypes = new HashMap<>();

    public DeferredBlockEntityTypeRegister(DeferredRegister<BlockEntityType<?>> deferredRegister) {
        super(deferredRegister);
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            String name,
            BlockEntitySupplier<? extends T> supplier,
            Supplier<Block> blocks
    ) {
        // ビルドして登録する
        RegistryObject<BlockEntityType<?>> registry = super.register(name, () ->
                BlockEntityType.Builder.of(
                        (pos, state) -> supplier.create(blockEntityTypes.get(blocks.hashCode()).get(), pos, state),
                        blocks.get()
                ).build(null));
        blockEntityTypes.put(blocks.hashCode(), registry);

        return ((RegistryObject<BlockEntityType<T>>) (Object) registry);
    }

    @FunctionalInterface
    public interface BlockEntitySupplier<T extends BlockEntity> {
        T create(BlockEntityType<?> type, BlockPos p_155268_, BlockState p_155269_);
    }
}