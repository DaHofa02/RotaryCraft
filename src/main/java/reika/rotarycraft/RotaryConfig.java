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
	/** Original options not ported at first; the ranges default to the port's built-in limits (the original raised them to its option). */
	public static final ModConfigSpec.IntValue FLOODLIGHTRANGE;
	public static final ModConfigSpec.IntValue BRIDGERANGE;
	public static final ModConfigSpec.IntValue AERORANGE;
	public static final ModConfigSpec.IntValue VACUUMRANGE;
	public static final ModConfigSpec.IntValue VACPOWER;
	public static final ModConfigSpec.IntValue FORCERANGE;
	public static final ModConfigSpec.IntValue DETECTORRANGE;
	public static final ModConfigSpec.IntValue BREEDERRANGE;
	public static final ModConfigSpec.IntValue BAITRANGE;
	public static final ModConfigSpec.IntValue LINEBUILDER;
	public static final ModConfigSpec.IntValue CHUNKLOADERSIZE;
	public static final ModConfigSpec.BooleanValue BLOWERSPILL;
	public static final ModConfigSpec.BooleanValue TURRETPLAYERS;
	public static final ModConfigSpec.BooleanValue PREENCHANT;
	public static final ModConfigSpec.BooleanValue BANRAIN;
	public static final ModConfigSpec.BooleanValue ALLOWTNTCANNON;
	public static final ModConfigSpec.BooleanValue ALLOWEMP;
	public static final ModConfigSpec.BooleanValue ALLOWLIGHTBRIDGE;
	public static final ModConfigSpec.BooleanValue ALLOWITEMCANNON;
	public static final ModConfigSpec.BooleanValue ALLOWCHUNKLOADER;
	public static final ModConfigSpec.BooleanValue NOMINERS;
	public static final ModConfigSpec.BooleanValue BEDPICKSPAWNERS;
	public static final ModConfigSpec.BooleanValue SPAWNERLEAK;
	public static final ModConfigSpec.BooleanValue HANDBOOK;
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
		b.push("machines");
		FLOODLIGHTRANGE = b.comment("Max Floodlight Range (floodlight and beam mirror beams; the original's default was 128)").defineInRange("floodlightRange", 64, 1, 1024);
		BRIDGERANGE = b.comment("Max Light Bridge Range (original default 128)").defineInRange("bridgeRange", 64, 1, 1024);
		AERORANGE = b.comment("Max Aerosolizer Range (original default 128)").defineInRange("aerosolizerRange", 64, 1, 1024);
		VACUUMRANGE = b.comment("Max Item Vacuum Range (original default 128)").defineInRange("vacuumRange", 32, 1, 1024);
		VACPOWER = b.comment("Item Vacuum Power Per Meter (W per block of range beyond the first 8)").defineInRange("vacuumPowerPerMeter", 4096, 1, 524288);
		FORCERANGE = b.comment("Max Force Field Range (force field and containment dome; original default 128)").defineInRange("forceFieldRange", 64, 1, 1024);
		DETECTORRANGE = b.comment("Max Player Detector Range").defineInRange("detectorRange", 128, 1, 1024);
		BREEDERRANGE = b.comment("Max Auto Breeder Range (original default 128)").defineInRange("breederRange", 24, 1, 256);
		BAITRANGE = b.comment("Max Bait Box Range").defineInRange("baitRange", 24, 1, 256);
		LINEBUILDER = b.comment("Max Line Builder (Block Ram) Range (original default 512)").defineInRange("lineBuilderRange", 64, 1, 1024);
		CHUNKLOADERSIZE = b.comment("Chunk Loader Max Radius in Chunks").defineInRange("chunkLoaderRadius", 8, 0, 32);
		BLOWERSPILL = b.comment("Item Pump (Blower) Spills Items If Dumping To Air").define("blowerSpill", true);
		TURRETPLAYERS = b.comment("Turrets can target players (each turret can still be set not to in its GUI)").define("turretPlayers", true);
		PREENCHANT = b.comment("Lock enchants on bedrock tools and armour (their built-in enchantments)").define("bedrockEnchants", true);
		BANRAIN = b.comment("Disable rain and storms from the weather controller (silver iodide)").define("banRain", false);
		ALLOWTNTCANNON = b.comment("Allow TNT Cannon (recipe)").define("allowTntCannon", true);
		ALLOWEMP = b.comment("Allow EMP (recipe)").define("allowEmp", true);
		ALLOWLIGHTBRIDGE = b.comment("Enable Light Bridge (recipe)").define("allowLightBridge", true);
		ALLOWITEMCANNON = b.comment("Enable Item Cannon (recipe)").define("allowItemCannon", true);
		ALLOWCHUNKLOADER = b.comment("Enable Chunk Loader (recipe)").define("allowChunkLoader", true);
		NOMINERS = b.comment("Disable Automining Machines (borer and sonic borer recipes)").define("noMiners", false);
		BEDPICKSPAWNERS = b.comment("Allow Bedrock Pickaxe to Harvest Spawners (as a spawner item that keeps its mob)").define("bedrockPickSpawners", true);
		SPAWNERLEAK = b.comment("Spawn Mobs When Harvesting Spawners By Hand (12-36 of its mobs; the original's default was on)").define("spawnerLeak", false);
		HANDBOOK = b.comment("Spawn with RC Handbook (once per player, on the first join)").define("handbookOnJoin", true);
		b.pop();
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
