/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import reika.rotarycraft.content.blockentity.BlowerBlockEntity;
import reika.rotarycraft.content.menu.BlowerMenu;
import reika.rotarycraft.registry.RotaryBlockEntities;

/** Original: MachineRegistry.BLOWER in BlockDMachine - six directions (where it pumps to, kept in the blockstate for the model); opens its filter GUI (GuiBlower) on right-click. */
public class BlowerBlock extends MachineBlock {

	public static final DirectionProperty FACING = BlockStateProperties.FACING;


	public BlowerBlock(Properties props) {
		super(props, RotaryBlockEntities.BLOWER, Placement.SIX_SIDED, false);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
		b.add(FACING);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player ep, BlockHitResult hit) {
		if (ep.isShiftKeyDown() || !(world.getBlockEntity(pos) instanceof BlowerBlockEntity te))
			return InteractionResult.PASS;
		if (!world.isClientSide && ep instanceof ServerPlayer sp && te.isPlayerAccessible(sp))
			sp.openMenu(new SimpleMenuProvider((id, inv, p) -> new BlowerMenu(id, inv, te), state.getBlock().getName()), buf -> buf.writeBlockPos(pos));
		return InteractionResult.sidedSuccess(world.isClientSide);
	}
}
