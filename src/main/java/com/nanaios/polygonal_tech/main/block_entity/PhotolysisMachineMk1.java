package com.nanaios.polygonal_tech.main.block_entity;

import com.nanaios.polygonal_tech.main.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.main.capability.CapabilityBuilder;
import com.nanaios.polygonal_tech.main.capability.fluid.LongFluidTank;
import com.nanaios.polygonal_tech.main.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.main.capability.interfaces.ILongFluidTank;
import com.nanaios.polygonal_tech.main.capability.item.ItemSlot;
import com.nanaios.polygonal_tech.main.config.PolygonalTechMachineConfig;
import com.nanaios.polygonal_tech.main.fluids.base.LongFluidStack;
import com.nanaios.polygonal_tech.main.registries.PolygonalTechBlockEntityTypeRegister;
import com.nanaios.polygonal_tech.main.registries.PolygonalTechFluidRegister;
import com.nanaios.polygonal_tech.main.util.save.SaveToNBT;
import com.nanaios.polygonal_tech.main.util.sync.Synchronize;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PhotolysisMachineMk1 extends BaseGuiMachine<PhotolysisMachineMk1> {
    @SaveToNBT
    @Synchronize(Synchronize.Type.IN_GUI)
    public ItemSlot inputSlot;

    @SaveToNBT
    @Synchronize(Synchronize.Type.IN_GUI)
    public LongFluidTank outputTank;

    @SaveToNBT
    @Synchronize(Synchronize.Type.IN_GUI)
    public long progress = 0;

    @SaveToNBT
    @Synchronize(Synchronize.Type.IN_GUI)
    public boolean canSeeSky = false;

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
        builder.add(outputTank = new LongFluidTank(false,true,
                PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_OUTPUT_TANK_CAPACITY::get,
                fluidStack -> fluidStack.getFluid().isSame(PolygonalTechFluidRegister.THIRD_FLOW.get())),
                Direction.EAST,Direction.WEST);
        return builder;
    }

    public long getProgress() {
        return progress;
    }

    @Override
    public boolean serverTick(Level level, BlockPos pos, BlockState state, PhotolysisMachineMk1 blockEntity) {
        boolean isChanged = super.serverTick(level, pos, state, blockEntity);
        canSeeSky = level.canSeeSky(pos.above()) && level.getBrightness(LightLayer.SKY, pos) >= PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_LIGHT_LEVEL.get();

        // 処理が可能かどうかをチェック
        boolean hasItem = !inputSlot.getStack().isEmpty();
        if(!hasItem) {
            progress = 0; // アイテムがない場合は進行度をリセット
            return isChanged;
        }

        long space = outputTank.getLongCapacity() - outputTank.getFluidLongAmount(); // 出力タンクの空き容量を計算
        if(space < PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PRODUCED_AMOUNT.get()) {
            progress = 0; // 出力タンクに十分な空き容量がない場合は進行度をリセット
            return isChanged;
        }

        // 処理が可能な場合は進行度を増加
        // 逆に処理が不可能な場合は進行を停止
        if(canSeeSky) {
            progress++;
            if(progress >= PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PROCESS_TIME.get()) {
                inputSlot.extractItem(1, false); // アイテムを1つ消費
                outputTank.fillLong(new LongFluidStack(PolygonalTechFluidRegister.THIRD_FLOW.get(), PolygonalTechMachineConfig.PHOTOLYSIS_MACHINE_MK1_PRODUCED_AMOUNT.get()), IFluidHandler.FluidAction.EXECUTE);
                progress = 0; // 進行度をリセット
            }
        }

        return isChanged;
    }
}
