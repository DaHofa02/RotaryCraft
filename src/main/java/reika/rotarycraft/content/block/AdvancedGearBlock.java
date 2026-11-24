/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft design). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.content.block;

import reika.rotarycraft.content.blockentity.AdvancedGearBlockEntity;
import reika.rotarycraft.content.menu.CoilMenu;
import reika.rotarycraft.content.menu.CvtMenu;
import reika.rotarycraft.registry.RotaryBlockEntities;
import reika.rotarycraft.registry.RotaryFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** The worm gear, high-ratio gear, industrial coils and CVT: four-sided; coils and CVT open a GUI; lubricant buckets fill the high-ratio gear and CVT. */
public class AdvancedGearBlock extends MachineBlock {

	public final AdvancedGearBlockEntity.Kind kind;

	public AdvancedGearBlock(Properties props, AdvancedGearBlockEntity.Kind kind) {
		super(props, RotaryBlockEntities.ADVANCED_GEAR, Placement.FOUR_SIDED, false);
		this.kind = kind;
	}

	/** A lubricant bucket fills the high-ratio gear or the CVT (1000 mB). */
	@Override
	protected ItemInteractionResult useItemOn(ItemStack is, BlockState state,
			Level world, BlockPos pos, Player ep, InteractionHand hand,
			BlockHitResult hit) {
		if (!kind.isLubricated() || !is.is(RotaryFluids.LUBRICANT.get().getBucket()) || !(world.getBlockEntity(pos) instanceof AdvancedGearBlockEntity te))
			return super.useItemOn(is, state, world, pos, ep, hand, hit);
		if (!world.isClientSide && te.getLubricant()+1000 <= AdvancedGearBlockEntity.LUBE_CAPACITY) {
			te.addLubricantFromBucket(1000);
			if (!ep.getAbilities().instabuild)
				ep.setItemInHand(hand, new ItemStack(Items.BUCKET));
		}
		return ItemInteractionResult.sidedSuccess(world.isClientSide);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world,
			BlockPos pos, Player ep, BlockHitResult hit) {
		boolean gui = kind.storesEnergy() || kind == AdvancedGearBlockEntity.Kind.CVT;
		if (!gui || ep.isShiftKeyDown() || !(world.getBlockEntity(pos) instanceof AdvancedGearBlockEntity te))
			return InteractionResult.PASS;
		if (!world.isClientSide && ep instanceof ServerPlayer sp && te.isPlayerAccessible(sp))
			sp.openMenu(new SimpleMenuProvider((id, inv, p) -> kind == AdvancedGearBlockEntity.Kind.CVT
					? new CvtMenu(id, inv, te) : new CoilMenu(id, inv, te), state.getBlock().getName()),
					buf -> buf.writeBlockPos(pos));
		return InteractionResult.sidedSuccess(world.isClientSide);
	}
}
