package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.registries.interfaces.IDisplayable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DeferredBlockRegister extends WrapperDeferredRegister<Block> implements IDisplayable {
    private final DeferredRegister<Item> blockItemDeferredRegister;

    public DeferredBlockRegister(DeferredRegister<Block> blockDeferredRegister, DeferredRegister<Item> blockItemDeferredRegister) {
        super(blockDeferredRegister);
        this.blockItemDeferredRegister = blockItemDeferredRegister;
    }

    @Override
    public <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> sup) {
        RegistryObject<I> blockRegistryObject = super.register(name, sup);

        blockItemDeferredRegister.register(
                name,
                () -> new BlockItem(blockRegistryObject.get(), new Item.Properties())
        );

        return blockRegistryObject;
    }

    @SuppressWarnings("unchecked")
    public <I extends Block> RegistryObject<I> registerMachine(String name, BlockSupplier<I> sup) {
        RegistryObject<I>[] pointer = new RegistryObject[1];
        RegistryObject<I> blockRegistryObject = super.register(name, () -> sup.create(pointer[0]));

        pointer[0] = blockRegistryObject;

        blockItemDeferredRegister.register(
                name,
                () -> new BlockItem(blockRegistryObject.get(), new Item.Properties())
        );

        return blockRegistryObject;
    }

    @FunctionalInterface
    public interface BlockSupplier<T extends Block> {
        T create(RegistryObject<T> block);
    }

    @Override
    public void display(CreativeModeTab.Output output) {
        for(RegistryObject<Item> item : blockItemDeferredRegister.getEntries()) {
            output.accept(item.get());
        }
    }
}
