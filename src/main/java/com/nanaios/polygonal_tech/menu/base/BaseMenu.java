package com.nanaios.polygonal_tech.menu.base;

import com.nanaios.polygonal_tech.block_entity.base.BaseGuiMachine;
import com.nanaios.polygonal_tech.capability.base.BaseProvider;
import com.nanaios.polygonal_tech.capability.interfaces.IItemSlot;
import com.nanaios.polygonal_tech.capability.provider.ItemSlotProvider;
import com.nanaios.polygonal_tech.container.base.BaseContainer;
import com.nanaios.polygonal_tech.network.PolygonalTechNetwork;
import com.nanaios.polygonal_tech.network.packet.ContainerNBTSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

public class BaseMenu<M extends BaseGuiMachine<M>> extends AbstractContainerMenu {
    protected final BlockPos pos;
    protected final Inventory inventory;
    protected final ContainerLevelAccess access;
    protected int machineSlots = 0;

    public BaseMenu(MenuType<?> type, int id, Inventory inv, BlockPos pos) {
        super(type, id);

        this.inventory = inv;
        this.pos = pos;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);

        broadcastChanges();

        M machine = getMachine();
        if (machine == null) return;

        int i = 0;
        ItemSlotProvider slotProvider = machine.itemSlotProvider;
        machineSlots = slotProvider.getContainers().size();
        for (BaseContainer<IItemSlot> itemSlot : slotProvider.getContainers()) {
            IItemSlot base = itemSlot.getBase();
            addSlot(new SlotItemHandler(base, i, base.getX(), base.getY()));
            i++;
        }

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();

        // クライアント側での処理は不要
        if(inventory.player.level().isClientSide) return;

        M machine = getMachine();
        if (machine == null) return;

        // クライアントにNBTを送信
        for(int i = 0;i < machine.getProviderCount();i++) {
            BaseProvider<?,?> provider = machine.getProvider(i);
            int containerIndex = 0;
            for(BaseContainer<?> container : provider.getContainers()) {
                if(container.isMarkUpdate()) {
                    PolygonalTechNetwork.CHANNEL.send(
                            PacketDistributor.PLAYER.with(() -> (ServerPlayer) inventory.player),
                            new ContainerNBTSyncPacket(i,containerIndex, pos, container.serializeNBT())
                    );
                    container.setMarkUpdate(false);
                }
                containerIndex++;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public @Nullable M getMachine() {
        return (M) inventory.player.level().getBlockEntity(pos);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, inventory.player.level().getBlockState(pos).getBlock());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < machineSlots) {

            // machine -> player
            if (!moveItemStackTo(stack, machineSlots, slots.size(), true)) {
                return ItemStack.EMPTY;
            }

        } else {

            // player -> machine
            if (!moveItemStackTo(stack, 0, machineSlots, false)) {
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
