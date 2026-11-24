/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.compat.create;

import java.util.List;
import java.util.Locale;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import reika.rotarycraft.RotaryConfig;
import reika.rotarycraft.api.power.ShaftPowerReceiver;

/**
 * RotaryCraft -> Create: takes shaft power at its back (shafts and machines write it in through {@link ShaftPowerReceiver}) and drives a
 * Create network from its front at speed/radsPerRpm RPM (at most maxRpm) with a stress capacity of power*(1-loss)/wattsPerSU SU.
 */
public class RotaryToCreateBlockEntity extends GeneratingKineticBlockEntity implements ShaftPowerReceiver {

	private int omegaIn;
	private int torqueIn;
	private long powerIn;
	/** Game time of the last write from the shaft; inputs older than a few ticks count as gone (the source was removed). */
	private long lastFed = -100;
	private int ioAlpha;
	/** The speed and SU last given to Create, to only update the network on a change. */
	private int rpm;
	private float su;

	public RotaryToCreateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public RotaryToCreateBlockEntity(BlockPos pos, BlockState state) {
		this(CreateCompat.ROTARY_TO_CREATE_BE.get(), pos, state);
	}

	private Direction rotarySide() {
		return StressConverterBlock.rotarySide(this.getBlockState());
	}

	/** RPM for a shaft speed: speed/radsPerRpm, at most maxRpm; under 1 RPM it does not turn. */
	public static int rpmFor(int omega) {
		return Math.min(RotaryConfig.CREATE_MAX_RPM.get(), omega/RotaryConfig.CREATE_RADS_PER_RPM.get());
	}

	/** Stress units (total, not per RPM) for a shaft power, after the loss. */
	public static float suFor(long power) {
		return (float)(power*(1-RotaryConfig.CREATE_LOSS.get())/RotaryConfig.CREATE_WATTS_PER_SU.get());
	}

	@Override
	public void tick() {
		super.tick();
		if (level == null || level.isClientSide)
			return;
		if (ioAlpha > 0)
			ioAlpha -= 8;
		if (level.getGameTime()-lastFed > 2)
			omegaIn = torqueIn = 0;
		int r = rpmFor(omegaIn);
		float s = r > 0 ? suFor((long)omegaIn*torqueIn) : 0;
		if (r != rpm) {
			rpm = r;
			su = s;
			this.updateGeneratedRotation();
		}
		else if (Math.abs(s-su) > Math.max(1, su*0.005F)) {
			su = s;
			this.notifyStressCapacityChange(this.calculateAddedStressCapacity());
		}
	}

	@Override
	public float getGeneratedSpeed() {
		return rpm;
	}

	/** Create multiplies this by the speed, so it is SU per RPM. */
	@Override
	public float calculateAddedStressCapacity() {
		float c = rpm > 0 ? su/rpm : 0;
		lastCapacityProvided = c;
		return c;
	}

	/** Total SU this converter gives at the moment. */
	public float getStressUnits() {
		return rpm > 0 ? su : 0;
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean sneaking) {
		boolean added = super.addToGoggleTooltip(tooltip, sneaking);
		tooltip.add(Component.translatable("gui.rotarycraft.create.shaft_in", omegaIn, torqueIn, String.format(Locale.ROOT, "%,d", (long)omegaIn*torqueIn)));
		return true;
	}

	@Override
	protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.write(tag, registries, clientPacket);
		tag.putInt("rcOmega", omegaIn);
		tag.putInt("rcTorque", torqueIn);
		tag.putInt("rpm", rpm);
		tag.putFloat("su", su);
	}

	@Override
	protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
		super.read(tag, registries, clientPacket);
		omegaIn = tag.getInt("rcOmega");
		torqueIn = tag.getInt("rcTorque");
		rpm = tag.getInt("rpm");
		su = tag.getFloat("su");
	}

	@Override
	public void setOmega(int omega) {
		omegaIn = omega;
		if (level != null)
			lastFed = level.getGameTime();
	}

	@Override
	public void setTorque(int torque) {
		torqueIn = torque;
	}

	@Override
	public void setPower(long power) {
		powerIn = power;
	}

	@Override
	public void noInputMachine() {
		omegaIn = torqueIn = 0;
		powerIn = 0;
	}

	@Override
	public boolean canReadFrom(Direction dir) {
		return dir == this.rotarySide();
	}

	@Override
	public boolean isReceiving() {
		return true;
	}

	@Override
	public int getMinTorque(int available) {
		return 1;
	}

	@Override
	public int getOmega() {
		return omegaIn;
	}

	@Override
	public int getTorque() {
		return torqueIn;
	}

	@Override
	public long getPower() {
		return (long)omegaIn*torqueIn;
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
}
