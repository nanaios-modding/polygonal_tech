package com.nanaios.polygonal_tech.block_entity;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.CapabilityBuilder;
import com.nanaios.polygonal_tech.capability.fluid.LongFluidTank;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.capability.item.ItemSlot;
import com.nanaios.polygonal_tech.config.PolygonalTechMachineConfig;
import com.nanaios.polygonal_tech.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.util.sync.Synchronize;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class PhotolysisMachineMk1 extends BaseGuiMachine<PhotolysisMachineMk1> {
    @Synchronize(Synchronize.Type.ALWAYS)
    private ItemSlot inputSlot;
    @Synchronize(Synchronize.Type.ALWAYS)
    private LongFluidTank outputTank;
    @Synchronize(Synchronize.Type.ALWAYS)
    private long progress = 0;
    @Synchronize(Synchronize.Type.ALWAYS)
    private boolean canSeeSky = false;

    public PhotolysisMachineMk1(BlockPos pos, BlockState state) {
        super(PolygonalTechBlockEntityTypeRegister.PHOTOLYSIS_MACHINE_MK1, pos, state);
    }

    @Override
    public CapabilityBuilder<IItemSlot> initItemSlot() {
        CapabilityBuilder<IItemSlot> builder = new CapabilityBuilder<>();
        builder.add(inputSlot = new ItemSlot(true,true,stack -> true,40,40), Direction.NORTH,Direction.SOUTH);
        return builder;
    }

    @Override
    public CapabilityBuilder<ILongFluidTank> initFluidTank() {
        CapabilityBuilder<ILongFluidTank> builder = new CapabilityBuilder<>();
        builder.add(outputTank = new LongFluidTank(false,true,() -> Long.MAX_VALUE,fluidStack -> true), Direction.EAST,Direction.WEST);
        return builder;
    }

    public long getProgress() {
        return progress;
    }

    @Override
    public void save(CompoundTag tag) {
        super.save(tag);
        tag.putLong("progress", progress);
        tag.putBoolean("can_see_sky", canSeeSky);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        progress = tag.getLong("progress");
        canSeeSky = tag.getBoolean("can_see_sky");
    }

    @Override
    public boolean serverTick(Level level, BlockPos pos, BlockState state, PhotolysisMachineMk1 blockEntity) {
        boolean isChanged = super.serverTick(level, pos, state, blockEntity);

        // 処理が可能かどうかをチェック
        boolean hasItem = inputSlot.getStack() != ItemStack.EMPTY;
        canSeeSky = level.canSeeSky(pos.above()) && level.getBrightness(LightLayer.SKY, pos) >= PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL.get();

        if(hasItem && canSeeSky) {
            progress++;
            if(progress >= PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME.get()) {
                inputSlot.extractItem(1, false); // アイテムを1つ消費
                outputTank.fillLong(new LongFluidStack(Fluids.WATER, Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE);
                progress = 0; // 進行度をリセット
            }
        } else {
            progress = 0; // 処理できない場合は進行度をリセット
        }

        return isChanged;
    }
}
