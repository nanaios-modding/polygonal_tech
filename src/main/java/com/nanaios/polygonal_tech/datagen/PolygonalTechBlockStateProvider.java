package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.main.PolygonalTech;
import com.nanaios.polygonal_tech.main.registries.PolygonalTechBlockRegister;
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
            pipeBlock(block);
        }
    }

    private void pipeBlock(RegistryObject<Block> block) {
        ResourceLocation location = block.getId();

        ResourceLocation armLocation = ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath() + "_arm");

        ModelFile armModel = models().getExistingFile(armLocation);
        ModelFile coreModel = models().getExistingFile(location);


        // マルチパートbuilder
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());

        // コアは常に表示
        builder.part().modelFile(coreModel).addModel().end();

        // 各方向のアーム
        addArm(builder, armModel, BlockStateProperties.NORTH, 0, 0);
        addArm(builder, armModel, BlockStateProperties.SOUTH, 0, 180);
        addArm(builder, armModel, BlockStateProperties.EAST,  0, 90);
        addArm(builder, armModel, BlockStateProperties.WEST,  0, 270);
        addArm(builder, armModel, BlockStateProperties.UP,    270, 0);
        addArm(builder, armModel, BlockStateProperties.DOWN,  90,  0);

        simpleBlockItem(
                block.get(),
                coreModel
        );
    }

    private void addArm(MultiPartBlockStateBuilder builder, ModelFile arm,
                        BooleanProperty prop, int xRot, int yRot) {
        builder.part()
                .modelFile(arm).rotationX(xRot).rotationY(yRot).addModel()
                .condition(prop, true)
                .end();
    }
}
