package com.nanaios.polygonal_tech.registries.base;

import com.nanaios.polygonal_tech.registries.PolyTechBlockRegister;
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
    public static final HashMap<BlockEntitySupplier<?>,RegistryObject<BlockEntityType<?>>> blockEntityTypes = new HashMap<>();

    public DeferredBlockEntityTypeRegister(DeferredRegister<BlockEntityType<?>> deferredRegister) {
        super(deferredRegister);
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            String name,
            BlockEntitySupplier<? extends T> supplier,
            Supplier<Block> blocks
    ) {
        BlockEntityType.Builder<T> builder = BlockEntityType.Builder.of(
                (pos, state) -> supplier.create(blockEntityTypes.get(supplier).get(), pos, state),
                PolyTechBlockRegister.TEST_FACTORY.get(),
                blocks.get()
        );

        // ビルドして登録する
        RegistryObject<BlockEntityType<?>> registry = super.register(name, () -> builder.build(null));
        blockEntityTypes.put(supplier, registry);

        return ((RegistryObject<BlockEntityType<T>>)(Object) registry);
    }

    @FunctionalInterface
    public interface BlockEntitySupplier<T extends BlockEntity> {
        T create(BlockEntityType<?> type,BlockPos p_155268_, BlockState p_155269_);
    }
}