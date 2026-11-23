/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft;

import net.neoforged.neoforge.common.ModConfigSpec;

/** Original: Reika.RotaryCraft.RotaryConfig and Registry.ConfigRegistry. Options are added here as the features using them are ported. */
public final class RotaryConfig {

	public static final int torquelimit = (Integer.MAX_VALUE-1)/2;	// ~1 billion
	public static final int omegalimit = (Integer.MAX_VALUE-1)/2;

	public static final ModConfigSpec SPEC;

	/** 1-3 for easy/medium/hard; controls some recipe/fuel costs, failure chances, and other numerical tweaks. Progression is not changed. */
	public static final ModConfigSpec.IntValue DIFFICULTY;
	public static final ModConfigSpec.BooleanValue LOCKMACHINES;
	public static final ModConfigSpec.BooleanValue BLOCKDAMAGE;
	public static final ModConfigSpec.BooleanValue TABLEMACHINES;
	public static final ModConfigSpec.DoubleValue ENGINEVOLUME;
	public static final ModConfigSpec.DoubleValue MACHINEVOLUME;
	public static final ModConfigSpec.BooleanValue COLORBLIND;
	public static final ModConfigSpec.BooleanValue FRICTIONXP;
	public static final ModConfigSpec.BooleanValue VOIDHOLE;
	public static final ModConfigSpec.DoubleValue BORERPOW;
	public static final ModConfigSpec.BooleanValue BORERMAINTAIN;
	public static final ModConfigSpec.BooleanValue EXTRACTORMAINTAIN;
	public static final ModConfigSpec.BooleanValue ROTATEHOSE;
	public static final ModConfigSpec.IntValue FLOWSPEED;
	public static final ModConfigSpec.DoubleValue PIPEHARDNESS;
	public static final ModConfigSpec.IntValue HEATRAYRANGE;
	public static final ModConfigSpec.IntValue FANRANGE;
	public static final ModConfigSpec.IntValue SPILLERRANGE;
	public static final ModConfigSpec.BooleanValue ATTACKBLOCKS;
	/** Own additions: conversion to other power systems. */
	public static final ModConfigSpec.IntValue WATTS_PER_FE;
	public static final ModConfigSpec.IntValue CREATE_WATTS_PER_SU;
	public static final ModConfigSpec.DoubleValue CREATE_LOSS;
	public static final ModConfigSpec.IntValue CREATE_RADS_PER_RPM;
	public static final ModConfigSpec.IntValue CREATE_MAX_RPM;

	static {
		ModConfigSpec.Builder b = new ModConfigSpec.Builder();
		DIFFICULTY = b.comment("Difficulty Control (1-3 for easy/medium/hard)").defineInRange("difficulty", 2, 1, 3);
		LOCKMACHINES = b.comment("Owner-Only Machine Use").define("lockMachines", false);
		BLOCKDAMAGE = b.comment("Direct Block Damage from Machine Failures").define("blockDamage", true);
		TABLEMACHINES = b.comment("Crafting Table can Make Machines (if false, the Worktable is required to craft machines)").define("tableMachines", false);
		ENGINEVOLUME = b.comment("Engine Volume").defineInRange("engineVolume", 1D, 0D, 1D);
		MACHINEVOLUME = b.comment("Machine Volume Multiplier").defineInRange("machineVolume", 1D, 0D, 1D);
		COLORBLIND = b.comment("Color Blind Mode").define("colorBlind", false);
		FRICTIONXP = b.comment("Spawn XP from Friction Heater").define("frictionXP", true);
		EXTRACTORMAINTAIN = b.comment("Extractor Drill Wears Down").define("extractorMaintain", false);
		VOIDHOLE = b.comment("Allow Bedrock Breaker to Break Y=0 (the world's bottom layer)").define("voidHole", false);
		BORERPOW = b.comment("Borer Power Requirement Factor (0.5-8)").defineInRange("borerPowerFactor", 1D, 0.5D, 8D);
		BORERMAINTAIN = b.comment("Borer Requires Maintenance (its drill wears down after 256 digs; right-click with a drill to replace it)").define("borerMaintain", false);
		ROTATEHOSE = b.comment("Rotate Hose/Pipe/Fuel Line Recipes (recipe conflict avoidance)").define("rotateHose", false);
		FLOWSPEED = b.comment("Fluid Flow Speed (1-5)").defineInRange("flowSpeed", 5, 1, 5);
		PIPEHARDNESS = b.comment("Pipe Block Hardness (0-1)").defineInRange("pipeHardness", 0D, 0D, 1D);
		HEATRAYRANGE = b.comment("Max Heat Ray Range").defineInRange("heatRayRange", 128, 0, Integer.MAX_VALUE);
		FANRANGE = b.comment("Max Fan Range").defineInRange("fanRange", 128, 0, Integer.MAX_VALUE);
		SPILLERRANGE = b.comment("Liquid Spiller Range, Use Zero to Disable").defineInRange("spillerRange", 16, 0, 64);
		ATTACKBLOCKS = b.comment("Block Damage from Destructive Machines").define("attackBlocks", true);
		b.push("conversion");
		WATTS_PER_FE = b.comment("Shaft power (W) per 1 FE a tick: magnetic engine, dynamo, steam turbine, air compressor / pneumatic engine (mL of air)").defineInRange("wattsPerFE", 520, 1, Integer.MAX_VALUE);
		b.push("create");
		CREATE_WATTS_PER_SU = b.comment("Shaft power (W) per stress unit (SU) of Create, both converter directions").defineInRange("wattsPerSU", 16, 1, Integer.MAX_VALUE);
		CREATE_LOSS = b.comment("Share of the power lost in each converter direction").defineInRange("loss", 0.05, 0, 0.99);
		CREATE_RADS_PER_RPM = b.comment("Shaft speed (rad/s) per Create RPM: RotaryCraft -> Create gives speed/this RPM, Create -> RotaryCraft gives RPM*this rad/s").defineInRange("radsPerRpm", 16, 1, 65536);
		CREATE_MAX_RPM = b.comment("Highest Create speed (RPM) the RotaryCraft -> Create converter gives").defineInRange("maxRpm", 256, 1, 256);
		b.pop(2);
		SPEC = b.build();
	}

	private RotaryConfig() {}
}
