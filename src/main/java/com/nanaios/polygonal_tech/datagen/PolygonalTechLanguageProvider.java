package com.nanaios.polygonal_tech.datagen;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.lang.ILang;
import com.nanaios.polygonal_tech.lang.PolygonalTechToolTipLang;
import com.nanaios.polygonal_tech.registries.PolygonalTechBlockRegister;
import com.nanaios.polygonal_tech.registries.PolygonalTechFluidTypeRegister;
import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

public class PolygonalTechLanguageProvider extends LanguageProvider {
    public PolygonalTechLanguageProvider(PackOutput output) {
        super(output, PolygonalTech.MODID, "ja_jp");
    }

    @Override
    protected void addTranslations() {
        add(PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE_MK1.get(), "光分解機MK.1");
        add(PolygonalTechFluidTypeRegister.THIRD_FLOW_TYPE.get(), "3次流元");
        add(PolygonalTechToolTipLang.FLUID_TANK_EMPTY, "空");
        add(PolygonalTechToolTipLang.FLUID_TANK_AMOUNT, "%s / %s mB");
    }

    private void add(FluidType type, String translation) {
        String descriptionId = Util.makeDescriptionId("fluid_type", ForgeRegistries.FLUID_TYPES.get().getKey(type));
        add(descriptionId, translation);
    }

    private void add(ILang lang, String translation) {
        add(lang.getKey(), translation);
    }
}
