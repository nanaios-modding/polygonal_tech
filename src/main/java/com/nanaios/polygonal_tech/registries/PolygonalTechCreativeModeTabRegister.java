package com.nanaios.polygonal_tech.registries;

import com.nanaios.polygonal_tech.PolygonalTechLang;
import com.nanaios.polygonal_tech.registration.impl.DeferredCreativeModeTabRegister;
import com.nanaios.polygonal_tech.registration.impl.MultipleCreativeModeTabRegister;
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
                        .icon(() -> new ItemStack(PolygonalTechItemRegister.ELEMENT_3.get()))
                        .displayItems((parameters, output) -> {
                            PolygonalTechItemRegister.ELEMENTS.display(output);
                            PolygonalTechItemRegister.BLOCK_ITEMS.display(output);
                        })
                        .build());
    }
}
