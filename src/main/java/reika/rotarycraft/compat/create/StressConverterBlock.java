/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.create;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;

/**
 * Both converters: FACING points at the Create side (a Create shaft connects there), the RotaryCraft shaft goes on the opposite side.
 * The Create-to-RotaryCraft converter's stress load is changed by right-clicking it with an empty hand.
 */
public class StressConverterBlock extends DirectionalKineticBlock implements IBE<KineticBlockEntity> {

	/** True: RotaryCraft shaft power in, Create rotation out. */
	public final boolean toCreate;

	public StressConverterBlock(Properties props, boolean toCreate) {
		super(props);
		this.toCreate = toCreate;
	}

	/** The side the RotaryCraft shaft connects to. */
	public static Direction rotarySide(BlockState state) {
		return state.getValue(FACING).getOpposite();
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.getValue(FACING);
	}

	@Override
	public Direction.Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING).getAxis();
	}

	@Override
	public Class<KineticBlockEntity> getBlockEntityClass() {
		return KineticBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
		return toCreate ? CreateCompat.ROTARY_TO_CREATE_BE.get() : CreateCompat.CREATE_TO_ROTARY_BE.get();
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (toCreate || !(world.getBlockEntity(pos) instanceof CreateToRotaryBlockEntity te))
			return InteractionResult.PASS;
		if (!world.isClientSide) {
			te.cycleImpact(player.isShiftKeyDown());
			player.displayClientMessage(Component.translatable("message.rotarycraft.create_to_rotary.impact", te.getImpact()), true);
		}
		return InteractionResult.sidedSuccess(world.isClientSide);
	}
}
