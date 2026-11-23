/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.create;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import reika.rotarycraft.RotaryConfig;
import reika.rotarycraft.api.power.ShaftPowerEmitter;

/**
 * Create -> RotaryCraft: a Create stress consumer at its back with a settable load (SU per RPM, right-click; 1 to 1024), giving shaft power
 * at its front: RPM*radsPerRpm rad/s and load*RPM*wattsPerSU*(1-loss) W. Overstressed or still, it gives nothing. RotaryCraft shafts and
 * machines read it through {@link ShaftPowerEmitter}.
 */
public class CreateToRotaryBlockEntity extends KineticBlockEntity implements ShaftPowerEmitter {

	public static final int MAX_IMPACT = 1024;

	private int impact = 8;
	private int omega;
	private int torque;
	private long power;
	private int ioAlpha;

	public CreateToRotaryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public CreateToRotaryBlockEntity(BlockPos pos, BlockState state) {
		this(CreateCompat.CREATE_TO_ROTARY_BE.get(), pos, state);
	}

	private Direction rotarySide() {
		return StressConverterBlock.rotarySide(this.getBlockState());
	}

	public int getImpact() {
		return impact;
	}

	/** Doubles the load (halves it when sneaking), wrapping around between 1 and {@link #MAX_IMPACT} SU per RPM. */
	public void cycleImpact(boolean down) {
		this.setImpact(down ? (impact <= 1 ? MAX_IMPACT : impact/2) : (impact >= MAX_IMPACT ? 1 : impact*2));
	}

	public void setImpact(int value) {
		impact = Math.max(1, Math.min(MAX_IMPACT, value));
		if (this.hasNetwork())
			this.getOrCreateNetwork().updateStressFor(this, this.calculateStressApplied());
		this.setChanged();
		this.sendData();
	}

	/** Create multiplies this by the speed, so it is SU per RPM. */
	@Override
	public float calculateStressApplied() {
		lastStressApplied = impact;
		return impact;
	}

	@Override
	public void tick() {
		super.tick();
		if (level == null || level.isClientSide)
			return;
		if (ioAlpha > 0)
			ioAlpha -= 8;
		float rpm = Math.abs(this.getSpeed());
		if (rpm <= 0 || this.isOverStressed()) {
			omega = torque = 0;
			power = 0;
			return;
		}
		omega = Math.round(rpm*RotaryConfig.CREATE_RADS_PER_RPM.get());
		power = (long)(impact*rpm*RotaryConfig.CREATE_WATTS_PER_SU.get()*(1-RotaryConfig.CREATE_LOSS.get()));
		torque = omega > 0 ? (int)Math.min(Integer.MAX_VALUE, power/omega) : 0;
		power = (long)omega*torque; //what the shaft really carries
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean sneaking) {
		super.addToGoggleTooltip(tooltip, sneaking);
		tooltip.add(Component.translatable("gui.rotarycraft.create.load", impact));
		tooltip.add(Component.translatable("gui.rotarycraft.create.shaft_out", omega, torque, String.format(java.util.Locale.ROOT, "%,d", power)));
		return true;
	}

	@Override
	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		tag.putInt("impact", impact);
		tag.putInt("rcOmega", omega);
		tag.putInt("rcTorque", torque);
	}

	@Override
	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		impact = tag.contains("impact") ? Math.max(1, tag.getInt("impact")) : 8;
		omega = tag.getInt("rcOmega");
		torque = tag.getInt("rcTorque");
		power = (long)omega*torque;
	}

	@Override
	public boolean canWriteTo(Direction dir) {
		return dir == this.rotarySide();
	}

	@Override
	public boolean isEmitting() {
		return power > 0;
	}

	@Override
	public int getOmega() {
		return omega;
	}

	@Override
	public int getTorque() {
		return torque;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public String getName() {
		return this.getBlockState().getBlock().getName().getString();
	}

	@Override
	public int getIORenderAlpha() {
		return ioAlpha;
	}

	@Override
	public void setIORenderAlpha(int io) {
		ioAlpha = io;
	}

	@Override
	public long getMaxPower() {
		return (long)MAX_IMPACT*RotaryConfig.CREATE_MAX_RPM.get()*RotaryConfig.CREATE_WATTS_PER_SU.get();
	}

	@Override
	public long getCurrentPower() {
		return power;
	}

	@Override
	public BlockPos getEmittingPos() {
		return worldPosition.relative(this.rotarySide());
	}
}
