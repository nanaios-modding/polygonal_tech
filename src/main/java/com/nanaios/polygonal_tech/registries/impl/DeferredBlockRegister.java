package com.nanaios.polygonal_tech.registries.impl;

import com.nanaios.polygonal_tech.block.base.BaseGuiMachineBlock;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.base.WrapperDeferredRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DeferredBlockRegister extends WrapperDeferredRegister<Block> {
    private static final Map<NamedToken, RegistryObject<Block>> REGISTRY_OBJECT_MAP = new HashMap<>();
    private static final Map<RegistryObject<Block>, NamedToken> REVERSE_REGISTRY_OBJECT_MAP = new HashMap<>();

    protected @Nullable DeferredItemRegister itemRegister = null;

    public DeferredBlockRegister(DeferredRegister<Block> blockDeferredRegister) {
        super(blockDeferredRegister, REGISTRY_OBJECT_MAP, REVERSE_REGISTRY_OBJECT_MAP);
    }

    public RegistryObject<Block> registerGuiMachine(NamedToken token) {
        RegistryObject<Block> registryObject = super.register(
                token,
                () -> new BaseGuiMachineBlock<>(PolygonalTechBlockEntityTypeRegister.MACHINE_BLOCK_ENTITIES.getRegistry(token))
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
