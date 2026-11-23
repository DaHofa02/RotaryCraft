/*******************************************************************************
 * Own addition to the private NeoForge 1.21.1 RotaryCraft port (not in the original); not for distribution.
 ******************************************************************************/
package reika.rotarycraft.gametest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.compat.OptionalMods;
import reika.rotarycraft.compat.create.CreateCompat;
import reika.rotarycraft.compat.create.CreateToRotaryBlockEntity;
import reika.rotarycraft.compat.create.RotaryToCreateBlockEntity;
import reika.rotarycraft.compat.create.StressConverterBlock;
import reika.rotarycraft.content.blockentity.DynamometerBlockEntity;
import reika.rotarycraft.content.blockentity.engine.EngineBlockEntity;
import reika.rotarycraft.registry.RotaryBlocks;

/** The Create converters with the default config (16 W per SU, 5 % loss, 16 rad/s per RPM); they pass without Create (nothing to test). */
@GameTestHolder(RotaryCraft.MOD_ID)
@PrefixGameTestTemplate(false)
public class CreateConverterTests {

	private static final String PLATFORM = "platform_9x4x9";

	private static Block create(String id) {
		return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", id));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static BlockState with(BlockState s, String prop, Comparable value) {
		Property p = s.getBlock().getStateDefinition().getProperty(prop);
		return s.setValue(p, value);
	}

	private static <T> T rc(GameTestHelper h, Block block, int x, int z, int meta, Class<T> cls) {
		BlockPos p = new BlockPos(x, 1, z);
		h.setBlock(p, block);
		var te = (reika.rotarycraft.content.blockentity.RotaryCraftBlockEntity)h.getBlockEntity(p);
		te.setBlockMetadata(meta);
		return cls.cast(te);
	}

	/** DC engine (256 rad/s, 4 Nm = 1024 W) -> converter -> Create shaft: 16 RPM, 1024*0.95/16 = 60.8 SU, and the shaft turns at 16 RPM. */
	@GameTest(template = PLATFORM, timeoutTicks = 100)
	public static void shaftPowerDrivesCreate(GameTestHelper h) {
		if (!OptionalMods.create()) {
			h.succeed();
			return;
		}
		rc(h, RotaryBlocks.DC_ENGINE.get(), 6, 4, 0, EngineBlockEntity.class);
		h.setBlock(new BlockPos(6, 1, 5), Blocks.REDSTONE_BLOCK);
		BlockPos conv = new BlockPos(5, 1, 4), shaft = new BlockPos(4, 1, 4);
		h.setBlock(conv, CreateCompat.ROTARY_TO_CREATE.get().defaultBlockState().setValue(StressConverterBlock.FACING, Direction.WEST));
		h.setBlock(shaft, with(create("shaft").defaultBlockState(), "axis", Direction.Axis.X));
		h.succeedWhen(() -> {
			var te = (RotaryToCreateBlockEntity)h.getBlockEntity(conv);
			h.assertTrue(te.getOmega() == 256 && te.getTorque() == 4, "shaft in "+te.getOmega()+" rad/s "+te.getTorque()+" Nm");
			h.assertTrue(te.getGeneratedSpeed() == 16, "speed "+te.getGeneratedSpeed());
			h.assertTrue(Math.abs(te.getStressUnits()-60.8F) < 0.01F, "SU "+te.getStressUnits());
			var s = (com.simibubi.create.content.kinetics.base.KineticBlockEntity)h.getBlockEntity(shaft);
			h.assertTrue(Math.abs(s.getSpeed()) == 16, "Create shaft at "+s.getSpeed()+" RPM");
		});
	}

	/**
	 * Creative motor at 256 RPM -> converter (load 8 SU/RPM) -> dynamometer: 4096 rad/s and 8*256*16*0.95 = 31129 W, i.e. 7 Nm; at a
	 * load of 64 SU/RPM, 249036 W, i.e. 60 Nm.
	 */
	@GameTest(template = PLATFORM, timeoutTicks = 100)
	public static void createDrivesShaftPower(GameTestHelper h) {
		if (!OptionalMods.create()) {
			h.succeed();
			return;
		}
		BlockPos motor = new BlockPos(0, 1, 4), conv = new BlockPos(1, 1, 4);
		h.setBlock(motor, with(create("creative_motor").defaultBlockState(), "facing", Direction.EAST));
		((com.simibubi.create.content.kinetics.motor.CreativeMotorBlockEntity)h.getBlockEntity(motor)).generatedSpeed.setValue(256);
		h.setBlock(conv, CreateCompat.CREATE_TO_ROTARY.get().defaultBlockState().setValue(StressConverterBlock.FACING, Direction.WEST));
		DynamometerBlockEntity dyna = rc(h, RotaryBlocks.DYNAMOMETER.get(), 2, 4, 1, DynamometerBlockEntity.class);
		int[] stage = {0};
		h.succeedWhen(() -> {
			var te = (CreateToRotaryBlockEntity)h.getBlockEntity(conv);
			if (stage[0] == 0) {
				h.assertTrue(te.getOmega() == 4096 && te.getTorque() == 7, "converter gives "+te.getOmega()+" rad/s "+te.getTorque()+" Nm");
				h.assertTrue(dyna.omega == 4096 && dyna.torque == 7, "dynamometer reads "+dyna.omega+" rad/s "+dyna.torque+" Nm");
				te.setImpact(64);
				stage[0] = 1;
				h.fail("load raised");
			}
			h.assertTrue(te.getTorque() == 60 && dyna.torque == 60, "at 64 SU/RPM: converter "+te.getTorque()+" Nm, dynamometer "+dyna.torque+" Nm");
		});
	}
}
