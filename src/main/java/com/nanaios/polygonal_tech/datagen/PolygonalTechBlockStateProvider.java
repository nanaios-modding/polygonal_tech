package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechBlockStateProvider extends BlockStateProvider {
    public PolygonalTechBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PolygonalTech.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(RegistryObject<Block> block : PolygonalTechBlockRegister.MACHINE_BLOCKS.getEntries()) {
            ResourceLocation location = block.getId();

            // 機械ブロックは回転させる必要があるため、horizontalBlockを使用してモデルを生成する。
            horizontalBlock(
                    block.get(),
                    models().getExistingFile(location)
            );

            // アイテムモデルも同様に生成する。
            simpleBlockItem(
                    block.get(),
                    models().getExistingFile(location)
            );
        }

        for (RegistryObject<Block> block : PolygonalTechBlockRegister.PIPE_BLOCKS.getEntries()) {
            pipeBlock(block.get());
        }
    }

    private void pipeBlock(Block block) {
        ModelFile armModel = models().getExistingFile(PolygonalTech.rl("block/base_pipe_arm"));
        ModelFile coreModel = models().getExistingFile(PolygonalTech.rl("block/base_pipe"));


        // マルチパートbuilder
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        // コアは常に表示
        builder.part().modelFile(coreModel).addModel().end();

        // 各方向のアーム
        addArm(builder, armModel, BlockStateProperties.NORTH, 0, 0);
        addArm(builder, armModel, BlockStateProperties.SOUTH, 0, 180);
        addArm(builder, armModel, BlockStateProperties.EAST,  0, 90);
        addArm(builder, armModel, BlockStateProperties.WEST,  0, 270);
        addArm(builder, armModel, BlockStateProperties.UP,    270, 0);
        addArm(builder, armModel, BlockStateProperties.DOWN,  90,  0);
    }

    private void addArm(MultiPartBlockStateBuilder builder, ModelFile arm,
                        BooleanProperty prop, int xRot, int yRot) {
        builder.part()
                .modelFile(arm).rotationX(xRot).rotationY(yRot).addModel()
                .condition(prop, true)
                .end();
    }
}
