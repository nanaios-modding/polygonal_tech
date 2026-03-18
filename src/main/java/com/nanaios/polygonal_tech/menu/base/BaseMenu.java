package com.nanaios.polygonal_tech.menu.base;

import com.nanaios.polygonal_tech.PolygonalTech;
import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.util.sync.SyncedValue;
import com.nanaios.polygonal_tech.util.sync.SynchronizeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BaseMenu<M extends BaseGuiMachine<M>> extends AbstractContainerMenu {
    protected final BlockPos pos;
    protected final Inventory inventory;
    protected final ContainerLevelAccess access;
    @SuppressWarnings("rawtypes")
    protected final List<SyncedValue> syncedFields = new ArrayList<>();
    protected int itemSlotCount;
    protected boolean[] markedForSync;

    public BaseMenu(MenuType<?> type, int id, Inventory inv, BlockPos pos) {
        super(type, id);

        this.inventory = inv;
        this.pos = pos;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);

        M machine = getMachine();
        if (machine == null) return;

        // 同期対象のフィールドを取得。これにより、@Synchronizeアノテーションが付けられたフィールドが自動的に同期されるようになる。
        for (Field field : SynchronizeMap.inGuiSynchronizedFields.getOrDefault(machine.getClass(), List.of())) {
            Class<?> fieldType = field.getType();
            // 同期するアイテムスロットを追加。これにより、機械のアイテムスロットが自動的にメニューに追加されるようになる。
            if (IItemSlot.class.isAssignableFrom(fieldType)) {
                try {
                    IItemSlot iitemSlot = (IItemSlot) field.get(machine);
                    addSlot(new ItemSlotHandler(iitemSlot, itemSlotCount));
                    itemSlotCount++;
                } catch (IllegalAccessException e) {
                    PolygonalTech.LOGGER.error("Failed to access item slot field for synchronization", e);
                }
            }
        }

        if (!syncedFields.isEmpty()) {
            markedForSync = new boolean[syncedFields.size()];
        }

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        if(inv.player instanceof ServerPlayer serverPlayer) {
            machine.guiViewer.add(serverPlayer);
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);

        M machine = getMachine();
        if(machine != null && player instanceof ServerPlayer serverPlayer) {
            machine.guiViewer.remove(serverPlayer);
        }
    }

    public int getItemSlotCount() {
        return itemSlotCount;
    }

    @SuppressWarnings("unchecked")
    public @Nullable M getMachine() {
        return (M) inventory.player.level().getBlockEntity(pos);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(access, player, inventory.player.level().getBlockState(pos).getBlock());
    }

    @Override
    @NotNull
    public ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < itemSlotCount) {

            // machine -> player
            if (!moveItemStackTo(stack, itemSlotCount, slots.size(), true)) {
                return ItemStack.EMPTY;
            }

        } else {

            // player -> machine
            if (!moveItemStackTo(stack, 0, itemSlotCount, false)) {
                return ItemStack.EMPTY;
            }

        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return copy;

    }

    protected int getInventoryYOffset() {
        return 84;
    }

    protected int getInventoryXOffset() {
        return 8;
    }

    private void addPlayerInventory(Inventory playerInv) {
        int xOffset = getInventoryXOffset();
        int yOffset = getInventoryYOffset();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {

                int index = col + row * 9 + 9;

                int x = xOffset + col * 18;
                int y = yOffset + row * 18;

                this.addSlot(new Slot(playerInv, index, x, y));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInv) {
        int xOffset = getInventoryXOffset();
        int yOffset = getInventoryYOffset() + 58;

        for (int col = 0; col < 9; col++) {

            int x = xOffset + col * 18;

            this.addSlot(new Slot(playerInv, col, x, yOffset));
        }
    }
}
