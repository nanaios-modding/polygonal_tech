package com.nanaios.polygonal_tech.main.registries;

import com.nanaios.polygonal_tech.main.lang.PolygonalTechLang;
import com.nanaios.polygonal_tech.lib.registration.impl.DeferredCreativeModeTabRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.MultipleCreativeModeTabRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class PolygonalTechCreativeModeTabRegister {
    public static final MultipleCreativeModeTabRegister CREATIVE_TABS = new MultipleCreativeModeTabRegister();
    public static final DeferredCreativeModeTabRegister MAIN_TAB = CREATIVE_TABS.create();

    static {
        MAIN_TAB.register(
                PolygonalTechNamedTokens.MAIN_TAB,
                () -> CreativeModeTab.builder()
                        .title(PolygonalTechLang.MAIN_TAB.get())
                        .icon(() -> new ItemStack(PolygonalTechBlockRegister.PHOTOLYSIS_MACHINE_MK1.get()))
                        .displayItems((parameters, output) -> {
                            PolygonalTechItemRegister.BLOCK_ITEMS.display(output);
                            PolygonalTechItemRegister.BUCKETS.display(output);
                        })
                        .build());
    }
}
