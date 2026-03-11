package com.nanaios.polygonal_tech.block_entity.base;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.menu.base.BaseMenu;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.registries.PolygonalTechMenuTypeRegister;
import com.nanaios.polygonal_tech.util.NamedToken;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public abstract class BaseGuiMachine<M extends BaseGuiMachine<M>> extends BaseMachine<M> implements MenuProvider {
    protected final NamedToken token;

    public BaseGuiMachine(RegistryObject<BlockEntityType<M>> type, BlockPos pos, BlockState state) {
        super(type.get(), pos, state);

        // RegistryObject<BlockEntityType<M>>はRegistryObject<BlockEntityType<?>>のサブクラスであるため、キャスト可能
        token = PolygonalTechBlockEntityTypeRegister.MACHINE_BLOCK_ENTITIES.getToken(castType(type));
    }


    @SuppressWarnings("unchecked")
    public static <M extends BaseGuiMachine<M>> RegistryObject<BlockEntityType<?>> castType(RegistryObject<BlockEntityType<M>> type) {
        return ((RegistryObject<BlockEntityType<?>>) (Object) type);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.%1$s.%2$s".formatted(PolygonalTech.MODID,token.name()));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BaseMenu<>(PolygonalTechMenuTypeRegister.MACHINE_GUI.getRegistry(token).get(), id, inv, this.worldPosition);
    }
}
