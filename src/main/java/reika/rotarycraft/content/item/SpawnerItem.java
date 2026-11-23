/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.content.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.core.component.DataComponents;

/**
 * Original: ItemRegistry.SPAWNER (ItemSpawner) - a mob spawner taken with the bedrock pickaxe, keeping its spawner data (mob, delays,
 * counts). Placing it puts the spawner back with that data. Unlike the vanilla spawner item, whose block data only operators may apply,
 * this item applies it itself.
 */
public class SpawnerItem extends Item {

	private static final String KEY = "spawner";

	public SpawnerItem(Properties props) {
		super(props.stacksTo(1));
	}

	/** A spawner item carrying this spawner's data. */
	public static ItemStack of(Item item, SpawnerBlockEntity spawner) {
		ItemStack is = new ItemStack(item);
		CompoundTag data = spawner.getSpawner().save(new CompoundTag());
		CompoundTag tag = new CompoundTag();
		tag.put(KEY, data);
		is.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		return is;
	}

	private static CompoundTag data(ItemStack is) {
		CompoundTag tag = is.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
		return tag.getCompound(KEY);
	}

	/** The mob the spawner makes, if it has one. */
	public static Optional<EntityType<?>> mob(ItemStack is) {
		CompoundTag entity = data(is).getCompound("SpawnData").getCompound("entity");
		return EntityType.byString(entity.getString("id"));
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		BlockPlaceContext place = new BlockPlaceContext(ctx);
		if (!place.canPlace())
			return InteractionResult.FAIL;
		Level world = ctx.getLevel();
		BlockPos pos = place.getClickedPos();
		if (!world.isClientSide) {
			world.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 3);
			if (world.getBlockEntity(pos) instanceof SpawnerBlockEntity te) {
				te.getSpawner().load(world, pos, data(ctx.getItemInHand()));
				te.setChanged();
				world.sendBlockUpdated(pos, te.getBlockState(), te.getBlockState(), 3);
			}
			world.playSound(null, pos, SoundEvents.METAL_PLACE, SoundSource.BLOCKS, 1, 1);
			if (ctx.getPlayer() == null || !ctx.getPlayer().getAbilities().instabuild)
				ctx.getItemInHand().shrink(1);
		}
		return InteractionResult.sidedSuccess(world.isClientSide);
	}

	@Override
	public Component getName(ItemStack is) {
		return mob(is).<Component>map(t -> Component.translatable("item.rotarycraft.spawner.mob", t.getDescription()))
				.orElseGet(() -> super.getName(is));
	}

	@Override
	public void appendHoverText(ItemStack is, TooltipContext ctx, List<Component> tip, TooltipFlag flag) {
		tip.add(Component.translatable("item.rotarycraft.spawner.desc"));
	}
}
