/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.content;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.RotaryConfig;
import reika.rotarycraft.content.item.BedrockTools;
import reika.rotarycraft.content.item.SpawnerItem;
import reika.rotarycraft.registry.RotaryItems;

/**
 * Original: ItemBedrockPickaxe.onBlockStartBreak (spawners) and RotaryCraft's HandbookTracker (the handbook on a player's first join).
 */
@EventBusSubscriber(modid = RotaryCraft.MOD_ID)
public final class PlayerEvents {

	/** Kept in the player's persisted data, so a player gets the handbook once (not again after dying). */
	private static final String HANDBOOK_GIVEN = "rotarycraft:handbook_given";

	private PlayerEvents() {}

	/** The bedrock pickaxe (and paxel) takes a mob spawner with its data as a spawner item (config bedrockPickSpawners). */
	@SubscribeEvent
	public static void onBreak(BlockEvent.BreakEvent evt) {
		Player ep = evt.getPlayer();
		if (!(evt.getLevel() instanceof ServerLevel world) || !evt.getState().is(Blocks.SPAWNER) || !RotaryConfig.BEDPICKSPAWNERS.get())
			return;
		Item held = ep.getMainHandItem().getItem();
		if (!(held instanceof BedrockTools.Pickaxe || held instanceof BedrockTools.Paxel) || !(world.getBlockEntity(evt.getPos()) instanceof SpawnerBlockEntity te))
			return;
		if (RotaryConfig.SPAWNERLEAK.get() && !(ep instanceof FakePlayer))
			leak(world, evt.getPos(), te, 12+world.random.nextInt(25));
		Block.popResource(world, evt.getPos(), SpawnerItem.of(RotaryItems.SPAWNER.get(), te));
	}

	/** Original ReikaSpawnerHelper.forceSpawn: the spawner lets out a crowd of its mobs when taken by hand (config spawnerLeak). */
	private static void leak(ServerLevel world, BlockPos pos, SpawnerBlockEntity te, int n) {
		CompoundTag entity = te.getSpawner().save(new CompoundTag()).getCompound("SpawnData").getCompound("entity");
		if (!entity.contains("id"))
			return;
		for (int i = 0; i < n; i++) {
			double x = pos.getX()+0.5+(world.random.nextDouble()-0.5)*6, z = pos.getZ()+0.5+(world.random.nextDouble()-0.5)*6;
			var e = EntityType.loadEntityRecursive(entity, world, m -> {
				m.moveTo(x, pos.getY(), z, world.random.nextFloat()*360, 0);
				return m;
			});
			if (e != null)
				world.addFreshEntity(e);
		}
	}

	/** Original HandbookTracker: a new player gets the handbook once (config handbookOnJoin); the recipe stays. */
	@SubscribeEvent
	public static void onJoin(PlayerEvent.PlayerLoggedInEvent evt) {
		giveHandbookOnce(evt.getEntity());
	}

	/** Returns whether the book was given. */
	public static boolean giveHandbookOnce(Player ep) {
		if (!RotaryConfig.HANDBOOK.get() || ep.level().isClientSide) //fake players never log in, so they never get here
			return false;
		CompoundTag data = ep.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
		if (data.getBoolean(HANDBOOK_GIVEN))
			return false;
		data.putBoolean(HANDBOOK_GIVEN, true);
		ep.getPersistentData().put(Player.PERSISTED_NBT_TAG, data);
		ItemStack book = new ItemStack(RotaryItems.HANDBOOK.get());
		if (!ep.getInventory().add(book))
			ep.drop(book, false);
		return true;
	}
}
