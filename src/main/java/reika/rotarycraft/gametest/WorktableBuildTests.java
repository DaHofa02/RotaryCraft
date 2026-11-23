/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;

import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.registry.RotaryBlocks;

/** Original RotaryEventManager.buildWorktables (see WorktableBuilder): the Worktable built in the world by a (simulated) survival player. */
@GameTestHolder(RotaryCraft.MOD_ID)
@PrefixGameTestTemplate(false)
public class WorktableBuildTests {

	private static final String PLATFORM = "platform_9x4x9";
	/** Centre column: redstone block at y 1, steel block at y 2, crafting table at y 3. */
	private static final BlockPos LOWER = new BlockPos(4, 1, 4);
	private static final BlockPos UPPER = LOWER.above();
	private static final BlockPos TABLE = UPPER.above();

	private static void structure(GameTestHelper h) {
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				if (i == 0 && k == 0)
					continue;
				h.setBlock(LOWER.offset(i, 0, k), Blocks.SMOOTH_STONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE));
				h.setBlock(UPPER.offset(i, 0, k), Blocks.BRICKS);
			}
		}
		h.setBlock(LOWER, Blocks.REDSTONE_BLOCK);
		h.setBlock(UPPER, RotaryBlocks.STEEL_BLOCK.get());
	}

	private static void assertBuilt(GameTestHelper h) {
		h.assertBlockPresent(RotaryBlocks.WORKTABLE.get(), LOWER);
		h.assertBlockPresent(Blocks.AIR, TABLE);
		h.assertBlockPresent(Blocks.AIR, UPPER);
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				h.assertBlockPresent(Blocks.AIR, UPPER.offset(i, 0, k));
				if (i != 0 || k != 0)
					h.assertBlockPresent(Blocks.AIR, LOWER.offset(i, 0, k));
			}
		}
	}

	/** Setting the crafting table down onto the steel block builds the Worktable; the table is used up. */
	@GameTest(template = PLATFORM)
	public static void worktableFromPlacedCraftingTable(GameTestHelper h) {
		structure(h);
		SimPlayer p = new SimPlayer(h, GameType.SURVIVAL);
		p.give(Items.CRAFTING_TABLE, 1);
		p.hold(Items.CRAFTING_TABLE);
		p.look(Direction.NORTH);
		p.useOn(UPPER, Direction.UP);
		assertBuilt(h);
		h.assertTrue(p.count(Items.CRAFTING_TABLE) == 0, "crafting table not used up");
		p.leave();
		h.succeed();
	}

	/** Right-clicking a crafting table that already stands on the structure builds it too. */
	@GameTest(template = PLATFORM)
	public static void worktableFromRightClick(GameTestHelper h) {
		structure(h);
		h.setBlock(TABLE, Blocks.CRAFTING_TABLE);
		SimPlayer p = new SimPlayer(h, GameType.SURVIVAL);
		p.emptyHand();
		p.use(TABLE);
		assertBuilt(h);
		p.leave();
		h.succeed();
	}

	/** One brick missing (stone instead): nothing happens, the crafting table stays. */
	@GameTest(template = PLATFORM)
	public static void worktableNeedsWholeStructure(GameTestHelper h) {
		structure(h);
		h.setBlock(UPPER.offset(1, 0, 1), Blocks.STONE);
		h.setBlock(TABLE, Blocks.CRAFTING_TABLE);
		SimPlayer p = new SimPlayer(h, GameType.SURVIVAL);
		p.emptyHand();
		p.use(TABLE);
		h.assertBlockPresent(Blocks.CRAFTING_TABLE, TABLE);
		h.assertBlockPresent(RotaryBlocks.STEEL_BLOCK.get(), UPPER);
		h.assertBlockPresent(Blocks.REDSTONE_BLOCK, LOWER);
		p.closeMenu();
		p.leave();
		h.succeed();
	}

	private static net.minecraft.world.item.ItemStack rcItem(String id, int n) {
		return new net.minecraft.world.item.ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(
				net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(RotaryCraft.MOD_ID, id)), n);
	}

	/** Puts stacks in like an AE2 pattern provider: each into the first slots that take it, equal items merged. */
	private static net.neoforged.neoforge.items.IItemHandler patternWorktable(GameTestHelper h, BlockPos at, Direction from) {
		h.setBlock(at, RotaryBlocks.WORKTABLE.get());
		var te = (reika.rotarycraft.content.blockentity.WorktableBlockEntity)h.getBlockEntity(at);
		te.togglePatternMode();
		h.assertTrue(te.isPatternMode(), "pattern mode not on");
		return h.getLevel().getCapability(net.neoforged.neoforge.capabilities.Capabilities.ItemHandler.BLOCK, h.absolutePos(at), from);
	}

	/**
	 * ME pattern mode (own addition): the heater's ingredients put in from the west in any order (4 steel ingots as one stack) are laid out
	 * and crafted; the heater goes back west into the chest standing there.
	 */
	@GameTest(template = PLATFORM, timeoutTicks = 40)
	public static void worktablePatternModeCraftsUnorderedInput(GameTestHelper h) {
		BlockPos table = new BlockPos(4, 1, 4), chest = table.west();
		h.setBlock(chest, Blocks.CHEST);
		var in = patternWorktable(h, table, Direction.WEST);
		for (var is : new net.minecraft.world.item.ItemStack[] {rcItem("combustor", 1), rcItem("steelingot", 4), rcItem("basepanel", 2),
				new net.minecraft.world.item.ItemStack(Items.IRON_BARS), rcItem("tungsteningot", 1)})
			h.assertTrue(net.neoforged.neoforge.items.ItemHandlerHelper.insertItemStacked(in, is, false).isEmpty(), "not taken: "+is);
		h.succeedWhen(() -> {
			var box = (net.minecraft.world.level.block.entity.ChestBlockEntity)h.getBlockEntity(chest);
			int heaters = 0;
			for (int i = 0; i < box.getContainerSize(); i++)
				if (box.getItem(i).is(rcItem("heater", 1).getItem()))
					heaters += box.getItem(i).getCount();
			h.assertTrue(heaters == 1, "heaters in the chest: "+heaters);
			for (int i = 0; i < 18; i++)
				h.assertTrue(in.getStackInSlot(i).isEmpty(), "left in slot "+i+": "+in.getStackInSlot(i));
		});
	}

	/** Items that are no recipe are moved into the grid, where the player sees them, instead of blocking the buffer for good. */
	@GameTest(template = PLATFORM, timeoutTicks = 20)
	public static void worktablePatternModeHandsBackNonRecipes(GameTestHelper h) {
		BlockPos table = new BlockPos(4, 1, 4);
		var in = patternWorktable(h, table, Direction.NORTH);
		net.neoforged.neoforge.items.ItemHandlerHelper.insertItemStacked(in, new net.minecraft.world.item.ItemStack(Items.DIRT, 3), false);
		var te = (reika.rotarycraft.content.blockentity.WorktableBlockEntity)h.getBlockEntity(table);
		h.succeedWhen(() -> {
			int dirt = 0;
			for (int i = 0; i < 9; i++)
				if (te.getInventory().getStackInSlot(i).is(Items.DIRT))
					dirt += te.getInventory().getStackInSlot(i).getCount();
			h.assertTrue(dirt == 3, "dirt in the grid: "+dirt);
		});
	}
}
