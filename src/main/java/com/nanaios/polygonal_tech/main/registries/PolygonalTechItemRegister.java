package com.nanaios.polygonal_tech.main.registries;

import com.nanaios.polygonal_tech.lib.registration.impl.DeferredItemRegister;
import com.nanaios.polygonal_tech.lib.registration.impl.MultipleItemRegister;
import com.nanaios.polygonal_tech.main.util.NamedToken;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.RegistryObject;

public class PolygonalTechItemRegister {
    public static final MultipleItemRegister ITEMS = new MultipleItemRegister();
    public static final DeferredItemRegister BUCKETS = ITEMS.create();
    public static final DeferredItemRegister BLOCK_ITEMS = ITEMS.create();

    public static final RegistryObject<Item> THIRD_FLOW_BUCKET;

    static {
        THIRD_FLOW_BUCKET = registerBucketItem(PolygonalTechNamedTokens.THIRD_FLOW_BUCKET, PolygonalTechFluidRegister.THIRD_FLOW);
    }

    private static RegistryObject<Item> registerBucketItem(NamedToken token, RegistryObject<Fluid> fluid) {
        return BUCKETS.register(
                token,
                () -> new BucketItem(
                        fluid,
                        new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
                )
        );
    }
}
