package com.nanaios.polygonal_tech.registration.impl;

import com.nanaios.polygonal_tech.block.base.BaseGuiMachineBlock;
import com.nanaios.polygonal_tech.block.base.BasePipeBlock;
import com.nanaios.polygonal_tech.registration.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredBlockRegister extends WrapperDeferredRegister<Block> {
    private static final Map<NamedToken, RegistryObject<Block>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<Block>, NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    protected @Nullable DeferredItemRegister itemRegister = null;

    public DeferredBlockRegister(DeferredRegister<Block> blockDeferredRegister) {
        super(blockDeferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity>RegistryObject<Block> registerPipe(NamedToken token, Supplier<RegistryObject<BlockEntityType<T>>> blockEntityType, Function<RegistryObject<BlockEntityType<?>>, BasePipeBlock> blockEntityTypeRegistryObjectFunction) {
        RegistryObject<Block> registryObject = super.register(
                token,
                () -> blockEntityTypeRegistryObjectFunction.apply((RegistryObject<BlockEntityType<?>>)(Object)blockEntityType.get())
        );

        if (itemRegister != null) {
            itemRegister.register(token, () -> new BlockItem(registryObject.get(), new Item.Properties()));
        }

        return registryObject;
    }

    @SuppressWarnings("unchecked")
    public <T extends BlockEntity>RegistryObject<Block> registerGuiMachine(NamedToken token, Supplier<RegistryObject<BlockEntityType<T>>> blockEntityType) {
        RegistryObject<Block> registryObject = super.register(
                token,
                // BaseGuiMachineBlockはBlockEntityType<?>を受け取るため、RegistryObject<BlockEntityType<T>>をRegistryObject<BlockEntityType<?>>にキャストする必要がある
                // これは理論上安全である。なぜなら、BaseGuiMachineBlockはBlockEntityType<?>を受け取るため、BlockEntityType<T>もBlockEntityType<?>のサブタイプであるため、キャストしても問題ないからである。
                () -> new BaseGuiMachineBlock<>((RegistryObject<BlockEntityType<?>>) (Object)blockEntityType.get())
        );

        if (itemRegister != null) {
            itemRegister.register(token, () -> new BlockItem(registryObject.get(), new Item.Properties()));
        }

        return registryObject;
    }

    public DeferredBlockRegister setBlockItemRegister(DeferredItemRegister itemRegister) {
        this.itemRegister = itemRegister;
        return this;
    }
}
