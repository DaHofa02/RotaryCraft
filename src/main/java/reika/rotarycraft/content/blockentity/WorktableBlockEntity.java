/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.content.blockentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import reika.rotarycraft.content.item.EngineUpgradeItem;
import reika.rotarycraft.content.recipe.WorktableRecipe;
import reika.rotarycraft.lib.data.InventoryHelper;
import reika.rotarycraft.registry.RotaryBlockEntities;
import reika.rotarycraft.registry.RotaryComponents;
import reika.rotarycraft.registry.RotaryItems;
import reika.rotarycraft.registry.RotaryRecipeTypes;
import reika.rotarycraft.registry.RotarySounds;

/**
 * Original: Reika.RotaryCraft.TileEntities.Production.TileEntityWorktable. Slots 0-8 are the grid, 9-17 the outputs (13 is the main
 * output), 18 the crafting pattern (pattern item not ported yet). Crafting happens from the GUI (taking from the main output) or on a
 * rising redstone edge; with an empty grid except the centre, a redstone pulse takes the centre item apart again (uncrafting). The
 * integrated redstone upgrade pulses by itself every 6 ticks. Chargeable tools are charged from wound coils. Not ported yet (their
 * items do not exist): jetpack and bedrock armour upgrades.
 */
public class WorktableBlockEntity extends RotaryCraftBlockEntity implements UpgradeableMachine, net.minecraft.world.Clearable {

	/** Vanilla Clearable: commands such as /setblock and /fill empty the inventory before replacing the block, so nothing drops. */
	@Override
	public void clearContent() {
		for (int i = 0; i < inv.getSlots(); i++)
			inv.setStackInSlot(i, ItemStack.EMPTY);
		for (int i = 0; i < buffer.getSlots(); i++)
			buffer.setStackInSlot(i, ItemStack.EMPTY);
	}


	public static final int OUTPUT = 13;

	private boolean hasUpgrade;
	private boolean lastRedstone;

	private final ItemStackHandler inv = new ItemStackHandler(19) {
		@Override
		protected void onContentsChanged(int slot) {
			WorktableBlockEntity.this.setChanged();
		}
	};

	/**
	 * Own addition, ME pattern mode: for autocrafters that put a recipe's ingredients in without caring about slots (an AE2 pattern provider
	 * with a processing pattern merges equal items into one stack). Automation then fills this buffer instead of the grid; when the buffer
	 * holds exactly one recipe's ingredients, they are laid out in the grid and crafted, and the result goes back to the side they came from.
	 */
	private boolean patternMode;
	private final ItemStackHandler buffer = new ItemStackHandler(9) {
		@Override
		protected void onContentsChanged(int slot) {
			WorktableBlockEntity.this.setChanged();
		}
	};
	/** The game tick of the last item put into the buffer: one pattern's ingredients arrive in the same tick, later ones wait. */
	private long bufferTick = -1;
	@Nullable
	private Direction bufferSide;

	public WorktableBlockEntity(BlockPos pos, BlockState state) {
		super(RotaryBlockEntities.WORKTABLE.get(), pos, state);
	}

	@Override
	public String getName() {
		return "Worktable";
	}

	public ItemStackHandler getInventory() {
		return inv;
	}

	public static CraftingInput matrix(IItemHandler inv) {
		List<ItemStack> li = new ArrayList<>(9);
		for (int i = 0; i < 9; i++)
			li.add(inv.getStackInSlot(i));
		return CraftingInput.of(3, 3, li);
	}

	/** Original WorktableRecipes.findMatchingRecipe. */
	@Nullable
	public static WorktableRecipe findMatchingRecipe(Level world, IItemHandler inv) {
		CraftingInput ic = matrix(inv);
		if (ic.isEmpty())
			return null;
		Optional<RecipeHolder<WorktableRecipe>> o = world.getRecipeManager().getRecipeFor(RotaryRecipeTypes.WORKTABLE.get(), ic, world);
		return o.map(RecipeHolder::value).orElse(null);
	}

	private boolean isMatrixEmpty() {
		for (int i = 0; i < 9; i++)
			if (!inv.getStackInSlot(i).isEmpty())
				return false;
		return true;
	}

	@Override
	protected void updateEntity(Level world, BlockPos pos, BlockState state) {
		if (world.isClientSide)
			return;
		boolean signal = world.hasNeighborSignal(pos);
		if (signal && !lastRedstone)
			this.onPositiveRedstoneEdge();
		lastRedstone = signal;
		if (patternMode && world.getGameTime() > bufferTick)
			this.craftFromBuffer(world, pos);
		if (this.isMatrixEmpty())
			return;
		if (hasUpgrade && this.getTicksExisted()%6 == 0 && !signal) {
			this.onPositiveRedstoneEdge();
			world.playSound(null, pos, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.BLOCKS, 0.5F, 0.675F);
		}
		if (this.isReadyToCraft() && this.getTicksExisted()%4 == 0)
			this.chargeTools();
	}

	/**
	 * Original chargeTools: with exactly a wound coil and a chargeable tool in the worktable (17 of 19 slots empty), the tool takes the
	 * coil's charge and the coil keeps what the tool returns (the tools swap the two); the tool goes to slot 9, the coil to slot 10.
	 * (The original also recharged RotaryCraft's own charged tools and armour, which are not ported yet.)
	 */
	private void chargeTools() {
		int coilslot = -1, toolslot = -1, empty = 0;
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack is = inv.getStackInSlot(i);
			if (is.isEmpty())
				empty++;
			else if (coilslot == -1 && is.is(RotaryItems.SPRING.get()))
				coilslot = i;
			else if (toolslot == -1 && is.getItem() instanceof reika.rotarycraft.api.interfaces.ChargeableTool)
				toolslot = i;
		}
		if (coilslot == -1) {
			for (int i = 0; i < inv.getSlots(); i++)
				if (inv.getStackInSlot(i).is(RotaryItems.STRONGCOIL.get()))
					coilslot = i;
		}
		if (toolslot == -1 || coilslot == -1 || empty != 17)
			return;
		ItemStack coil = inv.getStackInSlot(coilslot);
		ItemStack tool = inv.getStackInSlot(toolslot).copy();
		int newcoil = ((reika.rotarycraft.api.interfaces.ChargeableTool)tool.getItem()).setCharged(tool, reika.rotarycraft.content.item.CoilItem.getCharge(coil),
				coil.is(RotaryItems.STRONGCOIL.get()));
		ItemStack coilOut = reika.rotarycraft.content.item.CoilItem.withCharge(coil.copy(), newcoil);
		inv.setStackInSlot(toolslot, ItemStack.EMPTY);
		inv.setStackInSlot(coilslot, ItemStack.EMPTY);
		inv.setStackInSlot(9, tool);
		inv.setStackInSlot(10, coilOut);
	}

	private void onPositiveRedstoneEdge() {
		if (!this.craft()) {
			if (this.canUncraft())
				this.uncraft();
		}
	}

	private boolean craft() {
		WorktableRecipe wr = findMatchingRecipe(level, inv);
		if (wr != null) {
			return this.handleCrafting(wr, this.getPlacer(), false);
		}
		return false;
	}

	public boolean isPatternMode() {
		return patternMode;
	}

	/** Switching back to grid mode moves anything left in the buffer into the grid (if free) so it does not get lost. */
	public void togglePatternMode() {
		patternMode = !patternMode;
		if (!patternMode && this.isMatrixEmpty()) {
			for (int i = 0; i < 9; i++)
				inv.setStackInSlot(i, buffer.getStackInSlot(i));
			for (int i = 0; i < 9; i++)
				buffer.setStackInSlot(i, ItemStack.EMPTY);
		}
		if (level != null)
			level.invalidateCapabilities(worldPosition); //pipes and ME buses cache the item handler of the old mode
		this.syncAllData();
	}

	public ItemStackHandler getBuffer() {
		return buffer;
	}

	private boolean isBufferEmpty() {
		for (int i = 0; i < 9; i++)
			if (!buffer.getStackInSlot(i).isEmpty())
				return false;
		return true;
	}

	/**
	 * The buffer's items laid out in a 3x3 grid for the one recipe whose ingredients they are exactly (no item left over or missing), or
	 * null. Recycling recipes do not count; if several recipes take the same items, the first by recipe id wins.
	 */
	@Nullable
	public static ItemStack[] layOutForRecipe(Level world, IItemHandler buf, List<WorktableRecipe> out) {
		List<ItemStack> units = new ArrayList<>();
		for (int i = 0; i < buf.getSlots(); i++) {
			ItemStack is = buf.getStackInSlot(i);
			for (int n = 0; n < is.getCount(); n++) {
				if (units.size() >= 9)
					return null;
				units.add(is.copyWithCount(1));
			}
		}
		if (units.isEmpty())
			return null;
		List<RecipeHolder<WorktableRecipe>> all = new ArrayList<>(world.getRecipeManager().getAllRecipesFor(RotaryRecipeTypes.WORKTABLE.get()));
		all.sort(java.util.Comparator.comparing(h -> h.id().toString()));
		for (RecipeHolder<WorktableRecipe> h : all) {
			WorktableRecipe wr = h.value();
			if (wr.isRecycling())
				continue;
			List<Ingredient> layout = wr.getGridLayout();
			int needed = 0;
			for (Ingredient ing : layout)
				if (!ing.isEmpty())
					needed++;
			if (needed != units.size())
				continue;
			ItemStack[] grid = new ItemStack[9];
			java.util.Arrays.fill(grid, ItemStack.EMPTY);
			if (assign(layout, 0, units, new boolean[units.size()], grid) && wr.matches(CraftingInput.of(3, 3, List.of(grid)), world)) {
				out.add(wr);
				return grid;
			}
		}
		return null;
	}

	/** Backtracking: gives every grid position with an ingredient its own unit that the ingredient accepts. */
	private static boolean assign(List<Ingredient> layout, int pos, List<ItemStack> units, boolean[] used, ItemStack[] grid) {
		if (pos >= 9)
			return true;
		Ingredient ing = pos < layout.size() ? layout.get(pos) : Ingredient.EMPTY;
		if (ing.isEmpty())
			return assign(layout, pos+1, units, used, grid);
		for (int u = 0; u < units.size(); u++) {
			if (!used[u] && ing.test(units.get(u))) {
				used[u] = true;
				grid[pos] = units.get(u);
				if (assign(layout, pos+1, units, used, grid))
					return true;
				used[u] = false;
				grid[pos] = ItemStack.EMPTY;
			}
		}
		return false;
	}

	/** Pattern mode, once a tick has passed since the last insertion: lay out, craft, send the result back. */
	private void craftFromBuffer(Level world, BlockPos pos) {
		if (this.isBufferEmpty() || !this.isMatrixEmpty() || !this.isReadyToCraft())
			return;
		List<WorktableRecipe> found = new ArrayList<>(1);
		ItemStack[] grid = layOutForRecipe(world, buffer, found);
		if (grid == null) { //not a recipe: hand the items over to the grid, where the player can see and take them
			for (int i = 0; i < 9; i++) {
				inv.setStackInSlot(i, buffer.getStackInSlot(i));
				buffer.setStackInSlot(i, ItemStack.EMPTY);
			}
			return;
		}
		for (int i = 0; i < 9; i++) {
			inv.setStackInSlot(i, grid[i]);
			buffer.setStackInSlot(i, ItemStack.EMPTY);
		}
		this.handleCrafting(found.get(0), null, false);
		if (bufferSide == null)
			return;
		IItemHandler target = world.getCapability(net.neoforged.neoforge.capabilities.Capabilities.ItemHandler.BLOCK, pos.relative(bufferSide), bufferSide.getOpposite());
		if (target == null)
			return;
		for (int i = 9; i < 18; i++) {
			ItemStack is = inv.getStackInSlot(i);
			if (!is.isEmpty())
				inv.setStackInSlot(i, net.neoforged.neoforge.items.ItemHandlerHelper.insertItemStacked(target, is, false));
		}
	}

	public boolean isReadyToCraft() {
		for (int i = 9; i < 18; i++) {
			if (!inv.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * Original handleCrafting. Fixed here: the original only checked the main output's stack size, so a different item already lying
	 * there (possible when crafting by redstone) made the result vanish; now crafting stops instead.
	 */
	public boolean handleCrafting(WorktableRecipe wr, @Nullable Player ep, boolean craftAll) {
		int maxCrafts = craftAll ? this.getSmallestInputStack() : 1;
		int crafts = 0;
		if (wr instanceof WorktableRecipe.Recycling rec) {
			int i = 9;
			for (ItemStack is : rec.getSplitOutput()) {
				InventoryHelper.addOrSetStack(is, inv, i);
				i++;
			}
			crafts = 1;
		}
		else {
			for (int i = 0; i < maxCrafts; i++) {
				ItemStack is = wr.assemble(matrix(inv), level.registryAccess());
				ItemStack at = inv.getStackInSlot(OUTPUT);
				if (!at.isEmpty() && (!ItemStack.isSameItemSameComponents(at, is) || at.getCount()+is.getCount() > Math.min(64, is.getMaxStackSize())))
					break;
				if (ep != null)
					is.onCraftedBy(level, ep, is.getCount());
				InventoryHelper.addOrSetStack(is, inv, OUTPUT);
				crafts++;
			}
		}
		if (crafts > 0) {
			for (int i = 0; i < 9; ++i) {
				if (!inv.getStackInSlot(i).isEmpty())
					InventoryHelper.decrStack(inv, i, crafts);
			}
			RotarySounds.play(level, worldPosition, RotarySounds.CRAFT.get(), 0.3F, 1.5F);
			return true;
		}
		return false;
	}

	private int getSmallestInputStack() {
		int smallest = Integer.MAX_VALUE;
		for (int i = 0; i < 9; ++i) {
			ItemStack item = inv.getStackInSlot(i);
			if (!item.isEmpty() && item.getCount() < smallest) {
				smallest = item.getCount();
			}
		}
		return smallest < Integer.MAX_VALUE ? smallest : -1;
	}

	/** Original WorktableRecipes.getInputRecipe: a (non-recycling) recipe making this item; lubricant and bearing data are ignored. */
	@Nullable
	private WorktableRecipe getInputRecipe(ItemStack is) {
		ItemStack cmp = is.copy();
		cmp.remove(RotaryComponents.GEARBOX_DATA.get());
		List<RecipeHolder<WorktableRecipe>> all = new ArrayList<>(level.getRecipeManager().getAllRecipesFor(RotaryRecipeTypes.WORKTABLE.get()));
		all.sort(java.util.Comparator.comparing(h -> h.id().toString())); //the original searched in registration order; recipe ids give a fixed order
		for (RecipeHolder<WorktableRecipe> h : all) {
			WorktableRecipe wr = h.value();
			if (wr.isRecycling())
				continue;
			ItemStack out = wr.getResultItem(level.registryAccess()).copy();
			out.remove(RotaryComponents.GEARBOX_DATA.get());
			if (ItemStack.isSameItem(cmp, out) && cmp.getCount() >= out.getCount()) {
				if (cmp.getComponentsPatch().isEmpty() || ItemStack.isSameItemSameComponents(cmp, out))
					return wr;
			}
		}
		return null;
	}

	/**
	 * Original isNotUncraftable: damaged tools and machines (gear damage, gas turbine damage), anything holding lubricant, gearboxes
	 * whose bearing does not match, enchanted items.
	 */
	private boolean isNotUncraftable(ItemStack is) {
		if (is.isDamageableItem() && is.getDamageValue() > 0)
			return true;
		RotaryComponents.GearboxData data = is.get(RotaryComponents.GEARBOX_DATA.get());
		if (data != null) {
			if (data.damage() > 0 || data.lubricant() > 0)
				return true;
			if (data.bearing().material != is.getOrDefault(RotaryComponents.GEARBOX_TYPE.get(), data.bearing()).material)
				return true;
		}
		if (is.getOrDefault(RotaryComponents.JET_DAMAGE.get(), 0) > 0)
			return true;
		return is.isEnchanted();
	}

	public boolean canUncraft() {
		boolean can = false;
		for (int i = 0; i < 9; i++) {
			ItemStack is = inv.getStackInSlot(i);
			if (i == 4) {
				if (is.isEmpty() || this.isNotUncraftable(is))
					return false;
				WorktableRecipe ir = this.getInputRecipe(is);
				if (ir == null)
					return false;
				List<Ingredient> in = ir.getGridLayout();
				boolean flag = true;
				for (int k = 0; k < 9; k++) {
					if (k < in.size() && !in.get(k).isEmpty()) {
						ItemStack at = inv.getStackInSlot(k+9);
						if (!at.isEmpty()) {
							if (!in.get(k).test(at))
								flag = false;
							if (at.getCount() >= Math.min(64, at.getMaxStackSize()))
								flag = false;
						}
					}
				}
				can = flag;
			}
			else {
				if (!is.isEmpty())
					return false;
			}
		}
		return can;
	}

	private void uncraft() {
		ItemStack is = inv.getStackInSlot(4);
		WorktableRecipe ir = this.getInputRecipe(is);
		if (ir == null)
			return;
		List<Ingredient> in = ir.getGridLayout();

		InventoryHelper.decrStack(inv, 4, ir.getResultItem(level.registryAccess()).getCount());

		for (int i = 0; i < 9 && i < in.size(); i++) {
			if (!in.get(i).isEmpty()) {
				ItemStack at = inv.getStackInSlot(i+9);
				if (at.isEmpty()) {
					ItemStack[] options = in.get(i).getItems();
					if (options.length > 0)
						inv.setStackInSlot(i+9, options[0].copyWithCount(1));
				}
				else {
					inv.setStackInSlot(i+9, at.copyWithCount(at.getCount()+1));
				}
			}
		}
	}

	@Override
	protected void animateWithTick(Level world, BlockPos pos) {

	}

	@Override
	public int getRedstoneOverride() {
		return 0;
	}

	@Override
	protected void writeSyncTag(CompoundTag NBT, HolderLookup.Provider registries) {
		super.writeSyncTag(NBT, registries);
		NBT.putBoolean("redstoneUpgrade", hasUpgrade);
		NBT.putBoolean("patternMode", patternMode);
	}

	@Override
	protected void readSyncTag(CompoundTag NBT, HolderLookup.Provider registries) {
		super.readSyncTag(NBT, registries);
		hasUpgrade = NBT.getBoolean("redstoneUpgrade");
		patternMode = NBT.getBoolean("patternMode");
	}

	@Override
	protected void writeAdditional(CompoundTag NBT, HolderLookup.Provider registries) {
		super.writeAdditional(NBT, registries);
		NBT.put("inv", inv.serializeNBT(registries));
		NBT.putBoolean("lastRedstone", lastRedstone);
		NBT.put("buffer", buffer.serializeNBT(registries));
		if (bufferSide != null)
			NBT.putInt("bufferSide", bufferSide.get3DDataValue());
	}

	@Override
	protected void readAdditional(CompoundTag NBT, HolderLookup.Provider registries) {
		super.readAdditional(NBT, registries);
		inv.deserializeNBT(registries, NBT.getCompound("inv"));
		lastRedstone = NBT.getBoolean("lastRedstone");
		if (NBT.contains("buffer"))
			buffer.deserializeNBT(registries, NBT.getCompound("buffer"));
		bufferSide = NBT.contains("bufferSide") ? Direction.from3DDataValue(NBT.getInt("bufferSide")) : null;
	}

	@Override
	public void onEMP() {

	}

	/** Original isItemValidForSlot / canExtractItem: pipes and hoppers fill the grid and empty the outputs; in pattern mode, the buffer. */
	public IItemHandler getAutomationHandler(@Nullable Direction side) {
		return patternMode ? this.getPatternHandler(side) : this.getAutomationHandler();
	}

	/** Pattern mode: slots 0-8 are the buffer (any slot takes any item, while the buffer is open), 9-17 the outputs as usual. */
	private IItemHandler getPatternHandler(@Nullable Direction side) {
		return new IItemHandler() {
			@Override
			public int getSlots() {
				return 18;
			}

			@Override
			public ItemStack getStackInSlot(int slot) {
				return slot < 9 ? buffer.getStackInSlot(slot) : inv.getStackInSlot(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if (slot >= 9 || level == null)
					return stack;
				boolean open = WorktableBlockEntity.this.isBufferEmpty() || bufferTick == level.getGameTime();
				if (!open || !WorktableBlockEntity.this.isMatrixEmpty() || !WorktableBlockEntity.this.isReadyToCraft())
					return stack;
				ItemStack left = buffer.insertItem(slot, stack, simulate);
				if (!simulate && left.getCount() < stack.getCount()) {
					bufferTick = level.getGameTime();
					bufferSide = side;
				}
				return left;
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				return slot >= 9 ? inv.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
			}

			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return slot < 9;
			}
		};
	}

	public IItemHandler getAutomationHandler() {
		return new IItemHandler() {
			@Override
			public int getSlots() {
				return 18;
			}

			@Override
			public ItemStack getStackInSlot(int slot) {
				return inv.getStackInSlot(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				return slot < 9 ? inv.insertItem(slot, stack, simulate) : stack;
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				return slot >= 9 ? inv.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
			}

			@Override
			public int getSlotLimit(int slot) {
				return 64;
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return slot < 9;
			}
		};
	}

	@Override
	public void upgrade(ItemStack is) {
		this.addRedstoneUpgrade();
	}

	@Override
	public boolean canUpgradeWith(ItemStack item) {
		return !this.hasRedstoneUpgrade() && EngineUpgradeItem.is(item, EngineUpgradeItem.Upgrades.REDSTONE);
	}

	public void addRedstoneUpgrade() {
		hasUpgrade = true;
	}

	public boolean hasRedstoneUpgrade() {
		return hasUpgrade;
	}

	@Override
	public void onBlockBroken(Level world, BlockPos pos) {
		for (int i = 0; i < inv.getSlots(); i++)
			net.minecraft.world.Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inv.getStackInSlot(i).copy());
		for (int i = 0; i < buffer.getSlots(); i++)
			net.minecraft.world.Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), buffer.getStackInSlot(i).copy());
		if (this.hasRedstoneUpgrade())
			net.minecraft.world.Containers.dropItemStack(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, new ItemStack(RotaryItems.UPGRADE_REDSTONE.get()));
	}
}
