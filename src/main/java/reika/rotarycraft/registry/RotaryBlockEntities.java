/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.registry;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.content.blockentity.AdvancedGearBlockEntity;
import reika.rotarycraft.content.blockentity.AerosolizerBlockEntity;
import reika.rotarycraft.content.blockentity.AggregatorBlockEntity;
import reika.rotarycraft.content.blockentity.AirCompressorBlockEntity;
import reika.rotarycraft.content.blockentity.AirGunBlockEntity;
import reika.rotarycraft.content.blockentity.AutoBreederBlockEntity;
import reika.rotarycraft.content.blockentity.AutoCrafterBlockEntity;
import reika.rotarycraft.content.blockentity.BaitBoxBlockEntity;
import reika.rotarycraft.content.blockentity.BeamMirrorBlockEntity;
import reika.rotarycraft.content.blockentity.BeltHubBlockEntity;
import reika.rotarycraft.content.blockentity.BevelGearBlockEntity;
import reika.rotarycraft.content.blockentity.BigFurnaceBlockEntity;
import reika.rotarycraft.content.blockentity.BlockCannonBlockEntity;
import reika.rotarycraft.content.blockentity.BlockFillerBlockEntity;
import reika.rotarycraft.content.blockentity.BlowerBlockEntity;
import reika.rotarycraft.content.blockentity.BoilerBlockEntity;
import reika.rotarycraft.content.blockentity.BorerBlockEntity;
import reika.rotarycraft.content.blockentity.BucketFillerBlockEntity;
import reika.rotarycraft.content.blockentity.BusControllerBlockEntity;
import reika.rotarycraft.content.blockentity.CaveScannerBlockEntity;
import reika.rotarycraft.content.blockentity.CctvBlockEntity;
import reika.rotarycraft.content.blockentity.CentrifugeBlockEntity;
import reika.rotarycraft.content.blockentity.ChainDriveBlockEntity;
import reika.rotarycraft.content.blockentity.ChunkLoaderBlockEntity;
import reika.rotarycraft.content.blockentity.ClutchBlockEntity;
import reika.rotarycraft.content.blockentity.CompactorBlockEntity;
import reika.rotarycraft.content.blockentity.ComposterBlockEntity;
import reika.rotarycraft.content.blockentity.ContainmentBlockEntity;
import reika.rotarycraft.content.blockentity.CoolingFinBlockEntity;
import reika.rotarycraft.content.blockentity.CrystallizerBlockEntity;
import reika.rotarycraft.content.blockentity.DefoliatorBlockEntity;
import reika.rotarycraft.content.blockentity.DisplayBlockEntity;
import reika.rotarycraft.content.blockentity.DistilleryBlockEntity;
import reika.rotarycraft.content.blockentity.DistributionClutchBlockEntity;
import reika.rotarycraft.content.blockentity.DropProcessorBlockEntity;
import reika.rotarycraft.content.blockentity.DryingBedBlockEntity;
import reika.rotarycraft.content.blockentity.DynamoBlockEntity;
import reika.rotarycraft.content.blockentity.DynamometerBlockEntity;
import reika.rotarycraft.content.blockentity.EmpBlockEntity;
import reika.rotarycraft.content.blockentity.FanBlockEntity;
import reika.rotarycraft.content.blockentity.FermenterBlockEntity;
import reika.rotarycraft.content.blockentity.FertilizerBlockEntity;
import reika.rotarycraft.content.blockentity.FillingStationBlockEntity;
import reika.rotarycraft.content.blockentity.FireworkMachineBlockEntity;
import reika.rotarycraft.content.blockentity.FlooderBlockEntity;
import reika.rotarycraft.content.blockentity.FloodlightBlockEntity;
import reika.rotarycraft.content.blockentity.FluidCompressorBlockEntity;
import reika.rotarycraft.content.blockentity.FlywheelBlockEntity;
import reika.rotarycraft.content.blockentity.ForceFieldBlockEntity;
import reika.rotarycraft.content.blockentity.FractionatorBlockEntity;
import reika.rotarycraft.content.blockentity.FuelEngineBlockEntity;
import reika.rotarycraft.content.blockentity.FuelEnhancerBlockEntity;
import reika.rotarycraft.content.blockentity.GearboxBlockEntity;
import reika.rotarycraft.content.blockentity.GprBlockEntity;
import reika.rotarycraft.content.blockentity.GrinderBlockEntity;
import reika.rotarycraft.content.blockentity.GrindstoneBlockEntity;
import reika.rotarycraft.content.blockentity.GroundHydratorBlockEntity;
import reika.rotarycraft.content.blockentity.HeatRayBlockEntity;
import reika.rotarycraft.content.blockentity.HeaterBlockEntity;
import reika.rotarycraft.content.blockentity.IgniterBlockEntity;
import reika.rotarycraft.content.blockentity.ItemCannonBlockEntity;
import reika.rotarycraft.content.blockentity.ItemFilterBlockEntity;
import reika.rotarycraft.content.blockentity.ItemRefresherBlockEntity;
import reika.rotarycraft.content.blockentity.LampBlockEntity;
import reika.rotarycraft.content.blockentity.LandmineBlockEntity;
import reika.rotarycraft.content.blockentity.LavaMakerBlockEntity;
import reika.rotarycraft.content.blockentity.LawnSprinklerBlockEntity;
import reika.rotarycraft.content.blockentity.LightBridgeBlockEntity;
import reika.rotarycraft.content.blockentity.LineBuilderBlockEntity;
import reika.rotarycraft.content.blockentity.MachineGunBlockEntity;
import reika.rotarycraft.content.blockentity.MagneticEngineBlockEntity;
import reika.rotarycraft.content.blockentity.MagnetizerBlockEntity;
import reika.rotarycraft.content.blockentity.MirrorBlockEntity;
import reika.rotarycraft.content.blockentity.MobHarvesterBlockEntity;
import reika.rotarycraft.content.blockentity.MobRadarBlockEntity;
import reika.rotarycraft.content.blockentity.MultiClutchBlockEntity;
import reika.rotarycraft.content.blockentity.MusicBoxBlockEntity;
import reika.rotarycraft.content.blockentity.ObsidianMakerBlockEntity;
import reika.rotarycraft.content.blockentity.ParticleEmitterBlockEntity;
import reika.rotarycraft.content.blockentity.PileDriverBlockEntity;
import reika.rotarycraft.content.blockentity.PipePumpBlockEntity;
import reika.rotarycraft.content.blockentity.PlayerDetectorBlockEntity;
import reika.rotarycraft.content.blockentity.PneumaticEngineBlockEntity;
import reika.rotarycraft.content.blockentity.PortalShaftBlockEntity;
import reika.rotarycraft.content.blockentity.PowerBusBlockEntity;
import reika.rotarycraft.content.blockentity.ProjectorBlockEntity;
import reika.rotarycraft.content.blockentity.PulseJetFurnaceBlockEntity;
import reika.rotarycraft.content.blockentity.PumpBlockEntity;
import reika.rotarycraft.content.blockentity.PurifierBlockEntity;
import reika.rotarycraft.content.blockentity.RefrigeratorBlockEntity;
import reika.rotarycraft.content.blockentity.ReservoirBlockEntity;
import reika.rotarycraft.content.blockentity.ScaleableChestBlockEntity;
import reika.rotarycraft.content.blockentity.ScreenBlockEntity;
import reika.rotarycraft.content.blockentity.SelfDestructBlockEntity;
import reika.rotarycraft.content.blockentity.ShaftBlockEntity;
import reika.rotarycraft.content.blockentity.SmokeDetectorBlockEntity;
import reika.rotarycraft.content.blockentity.SolarTowerBlockEntity;
import reika.rotarycraft.content.blockentity.SonicBorerBlockEntity;
import reika.rotarycraft.content.blockentity.SonicWeaponBlockEntity;
import reika.rotarycraft.content.blockentity.SortingBlockEntity;
import reika.rotarycraft.content.blockentity.SpawnerControllerBlockEntity;
import reika.rotarycraft.content.blockentity.SpillwayBlockEntity;
import reika.rotarycraft.content.blockentity.SplitBeltBlockEntity;
import reika.rotarycraft.content.blockentity.SplitterBlockEntity;
import reika.rotarycraft.content.blockentity.SprinklerBlockEntity;
import reika.rotarycraft.content.blockentity.SpyCamBlockEntity;
import reika.rotarycraft.content.blockentity.SteamTurbineBlockEntity;
import reika.rotarycraft.content.blockentity.TerraformerBlockEntity;
import reika.rotarycraft.content.blockentity.TntCannonBlockEntity;
import reika.rotarycraft.content.blockentity.VacuumBlockEntity;
import reika.rotarycraft.content.blockentity.VanDeGraffBlockEntity;
import reika.rotarycraft.content.blockentity.WeatherControllerBlockEntity;
import reika.rotarycraft.content.blockentity.WetterBlockEntity;
import reika.rotarycraft.content.blockentity.WinderBlockEntity;
import reika.rotarycraft.content.blockentity.WoodcutterBlockEntity;
import reika.rotarycraft.content.blockentity.engine.ACEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.DCEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.EngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.GasEngineBlockEntity;
import reika.rotarycraft.content.blockentity.BedrockBreakerBlockEntity;
import reika.rotarycraft.content.blockentity.BlastFurnaceBlockEntity;
import reika.rotarycraft.content.blockentity.EngineControllerBlockEntity;
import reika.rotarycraft.content.blockentity.ExtractorBlockEntity;
import reika.rotarycraft.content.blockentity.FrictionHeaterBlockEntity;
import reika.rotarycraft.content.blockentity.WorktableBlockEntity;
import reika.rotarycraft.content.blockentity.engine.HydroEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.JetEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.MicroturbineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.PerformanceEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.SteamEngineBlockEntity;
import reika.rotarycraft.content.blockentity.engine.WindEngineBlockEntity;
import reika.rotarycraft.content.blockentity.turret.AntiAirGunBlockEntity;
import reika.rotarycraft.content.blockentity.turret.FlameTurretBlockEntity;
import reika.rotarycraft.content.blockentity.turret.FreezeGunBlockEntity;
import reika.rotarycraft.content.blockentity.turret.LaserGunBlockEntity;
import reika.rotarycraft.content.blockentity.turret.MultiCannonBlockEntity;
import reika.rotarycraft.content.blockentity.turret.RailgunBlockEntity;
import reika.rotarycraft.content.piping.BedrockPipeBlockEntity;
import reika.rotarycraft.content.piping.FlowPipeBlockEntity;
import reika.rotarycraft.content.piping.FuelLineBlockEntity;
import reika.rotarycraft.content.piping.HoseBlockEntity;
import reika.rotarycraft.content.piping.PipeBlockEntity;

public final class RotaryBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RotaryCraft.MOD_ID);

	public static final Supplier<BlockEntityType<ShaftBlockEntity>> SHAFT = type("shaft", ShaftBlockEntity::new, () -> RotaryBlocks.SHAFT.get());
	public static final Supplier<BlockEntityType<GearboxBlockEntity>> GEARBOX = type("gearbox", GearboxBlockEntity::new, () -> RotaryBlocks.GEARBOX.get());
	public static final Supplier<BlockEntityType<FlywheelBlockEntity>> FLYWHEEL = type("flywheel", FlywheelBlockEntity::new, () -> RotaryBlocks.FLYWHEEL.get());
	public static final Supplier<BlockEntityType<BevelGearBlockEntity>> BEVEL_GEARS = type("bevel_gears", BevelGearBlockEntity::new, () -> RotaryBlocks.BEVEL_GEARS.get());
	public static final Supplier<BlockEntityType<ClutchBlockEntity>> CLUTCH = type("clutch", ClutchBlockEntity::new, () -> RotaryBlocks.CLUTCH.get());
	public static final Supplier<BlockEntityType<MirrorBlockEntity>> MIRROR = type("solar_mirror",
			MirrorBlockEntity::new, () -> RotaryBlocks.MIRROR.get());
	public static final Supplier<BlockEntityType<SolarTowerBlockEntity>> SOLAR_TOWER = type("solar_tower",
			SolarTowerBlockEntity::new, () -> RotaryBlocks.SOLAR_TOWER.get());
	public static final Supplier<BlockEntityType<VanDeGraffBlockEntity>> VAN_DE_GRAFF = type("van_de_graff",
			VanDeGraffBlockEntity::new, () -> RotaryBlocks.VAN_DE_GRAFF.get());
	public static final Supplier<BlockEntityType<DryingBedBlockEntity>> DRYING_BED = type("drying_bed",
			DryingBedBlockEntity::new, () -> RotaryBlocks.DRYING_BED.get());
	public static final Supplier<BlockEntityType<WinderBlockEntity>> WINDER = type("winder",
			WinderBlockEntity::new, () -> RotaryBlocks.WINDER.get());
	public static final Supplier<BlockEntityType<FillingStationBlockEntity>> FILLING_STATION = type("filling_station",
			FillingStationBlockEntity::new, () -> RotaryBlocks.FILLING_STATION.get());
	public static final Supplier<BlockEntityType<CompactorBlockEntity>> COMPACTOR = type("compactor",
			CompactorBlockEntity::new, () -> RotaryBlocks.COMPACTOR.get());
	public static final Supplier<BlockEntityType<HeatRayBlockEntity>> HEAT_RAY = type("heat_ray",
			HeatRayBlockEntity::new, () -> RotaryBlocks.HEAT_RAY.get());
	public static final Supplier<BlockEntityType<RefrigeratorBlockEntity>> REFRIGERATOR = type("refrigerator",
			RefrigeratorBlockEntity::new, () -> RotaryBlocks.REFRIGERATOR.get());
	public static final Supplier<BlockEntityType<PipePumpBlockEntity>> PIPE_PUMP = type("pipe_pump",
			PipePumpBlockEntity::new, () -> RotaryBlocks.PIPE_PUMP.get());
	public static final Supplier<BlockEntityType<FuelEnhancerBlockEntity>> FUEL_ENHANCER = type("fuel_enhancer",
			FuelEnhancerBlockEntity::new, () -> RotaryBlocks.FUEL_ENHANCER.get());
	public static final Supplier<BlockEntityType<CoolingFinBlockEntity>> COOLING_FIN = type("cooling_fin",
			CoolingFinBlockEntity::new, () -> RotaryBlocks.COOLING_FIN.get());
	public static final Supplier<BlockEntityType<DynamometerBlockEntity>> DYNAMOMETER = type("dynamometer", DynamometerBlockEntity::new, () -> RotaryBlocks.DYNAMOMETER.get());
	public static final Supplier<BlockEntityType<SplitterBlockEntity>> SHAFT_JUNCTION = type("shaft_junction", SplitterBlockEntity::new, () -> RotaryBlocks.SHAFT_JUNCTION.get());

	public static final Supplier<BlockEntityType<DCEngineBlockEntity>> DC_ENGINE = type("dc_engine", DCEngineBlockEntity::new, () -> RotaryBlocks.DC_ENGINE.get());
	public static final Supplier<BlockEntityType<WindEngineBlockEntity>> WIND_ENGINE = type("wind_engine", WindEngineBlockEntity::new, () -> RotaryBlocks.WIND_ENGINE.get());
	public static final Supplier<BlockEntityType<SteamEngineBlockEntity>> STEAM_ENGINE = type("steam_engine", SteamEngineBlockEntity::new, () -> RotaryBlocks.STEAM_ENGINE.get());
	public static final Supplier<BlockEntityType<GasEngineBlockEntity>> GAS_ENGINE = type("gas_engine", GasEngineBlockEntity::new, () -> RotaryBlocks.GAS_ENGINE.get());
	public static final Supplier<BlockEntityType<ACEngineBlockEntity>> AC_ENGINE = type("ac_engine", ACEngineBlockEntity::new, () -> RotaryBlocks.AC_ENGINE.get());
	public static final Supplier<BlockEntityType<PerformanceEngineBlockEntity>> SPORT_ENGINE = type("sport_engine", PerformanceEngineBlockEntity::new, () -> RotaryBlocks.SPORT_ENGINE.get());
	public static final Supplier<BlockEntityType<MicroturbineBlockEntity>> MICRO_ENGINE = type("micro_engine", MicroturbineBlockEntity::new, () -> RotaryBlocks.MICRO_ENGINE.get());

	public static final Supplier<BlockEntityType<HydroEngineBlockEntity>> HYDRO_ENGINE = type("hydro_engine", HydroEngineBlockEntity::new, () -> RotaryBlocks.HYDRO_ENGINE.get());
	public static final Supplier<BlockEntityType<JetEngineBlockEntity>> JET_ENGINE = type("jet_engine", JetEngineBlockEntity::new, () -> RotaryBlocks.JET_ENGINE.get());

	public static final Supplier<BlockEntityType<EngineControllerBlockEntity>> ECU = type("ecu", EngineControllerBlockEntity::new, () -> RotaryBlocks.ECU.get());

	public static final Supplier<BlockEntityType<BlastFurnaceBlockEntity>> BLAST_FURNACE = type("blast_furnace", BlastFurnaceBlockEntity::new, () -> RotaryBlocks.BLAST_FURNACE.get());
	public static final Supplier<BlockEntityType<WorktableBlockEntity>> WORKTABLE = type("worktable", WorktableBlockEntity::new, () -> RotaryBlocks.WORKTABLE.get());

	public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR = type("extractor", ExtractorBlockEntity::new, () -> RotaryBlocks.EXTRACTOR.get());
	public static final Supplier<BlockEntityType<BedrockBreakerBlockEntity>> BEDROCK_BREAKER = type("bedrock_breaker", BedrockBreakerBlockEntity::new, () -> RotaryBlocks.BEDROCK_BREAKER.get());
	public static final Supplier<BlockEntityType<BorerBlockEntity>> BORER = type("borer", BorerBlockEntity::new, () -> RotaryBlocks.BORER.get());
	public static final Supplier<BlockEntityType<GrinderBlockEntity>> GRINDER = type("grinder", GrinderBlockEntity::new, () -> RotaryBlocks.GRINDER.get());
	public static final Supplier<BlockEntityType<FermenterBlockEntity>> FERMENTER = type("fermenter", FermenterBlockEntity::new, () -> RotaryBlocks.FERMENTER.get());
	public static final Supplier<BlockEntityType<FanBlockEntity>> FAN = type("fan", FanBlockEntity::new, () -> RotaryBlocks.FAN.get());
	public static final Supplier<BlockEntityType<MobHarvesterBlockEntity>> MOB_HARVESTER = type("mob_harvester", MobHarvesterBlockEntity::new, () -> RotaryBlocks.MOB_HARVESTER.get());
	public static final Supplier<BlockEntityType<AggregatorBlockEntity>> AGGREGATOR = type("aggregator", AggregatorBlockEntity::new, () -> RotaryBlocks.AGGREGATOR.get());
	public static final Supplier<BlockEntityType<FlooderBlockEntity>> SPILLER = type("spiller", FlooderBlockEntity::new, () -> RotaryBlocks.SPILLER.get());
	public static final Supplier<BlockEntityType<BlockFillerBlockEntity>> FILLER = type("filler", BlockFillerBlockEntity::new, () -> RotaryBlocks.FILLER.get());
	public static final Supplier<BlockEntityType<SmokeDetectorBlockEntity>> SMOKE_DETECTOR = type("smoke_detector", SmokeDetectorBlockEntity::new, () -> RotaryBlocks.SMOKE_DETECTOR.get());
	public static final Supplier<BlockEntityType<PlayerDetectorBlockEntity>> PLAYER_DETECTOR = type("player_detector", PlayerDetectorBlockEntity::new, () -> RotaryBlocks.PLAYER_DETECTOR.get());
	public static final Supplier<BlockEntityType<MobRadarBlockEntity>> MOB_RADAR = type("mob_radar", MobRadarBlockEntity::new, () -> RotaryBlocks.MOB_RADAR.get());
	public static final Supplier<BlockEntityType<LavaMakerBlockEntity>> LAVA_MAKER = type("lava_maker", LavaMakerBlockEntity::new, () -> RotaryBlocks.LAVA_MAKER.get());
	public static final Supplier<BlockEntityType<HeaterBlockEntity>> HEATER = type("heater", HeaterBlockEntity::new, () -> RotaryBlocks.HEATER.get());
	public static final Supplier<BlockEntityType<ComposterBlockEntity>> COMPOSTER = type("composter", ComposterBlockEntity::new, () -> RotaryBlocks.COMPOSTER.get());
	public static final Supplier<BlockEntityType<CrystallizerBlockEntity>> CRYSTALLIZER = type("crystallizer", CrystallizerBlockEntity::new, () -> RotaryBlocks.CRYSTALLIZER.get());
	public static final Supplier<BlockEntityType<LampBlockEntity>> LAMP = type("lamp", LampBlockEntity::new, () -> RotaryBlocks.LAMP.get());
	public static final Supplier<BlockEntityType<GroundHydratorBlockEntity>> HYDRATOR = type("hydrator", GroundHydratorBlockEntity::new, () -> RotaryBlocks.HYDRATOR.get());
	public static final Supplier<BlockEntityType<WetterBlockEntity>> WETTER = type("wetter", WetterBlockEntity::new, () -> RotaryBlocks.WETTER.get());
	public static final Supplier<BlockEntityType<ParticleEmitterBlockEntity>> PARTICLE_EMITTER = type("particle_emitter", ParticleEmitterBlockEntity::new, () -> RotaryBlocks.PARTICLE_EMITTER.get());
	public static final Supplier<BlockEntityType<MultiClutchBlockEntity>> MULTI_CLUTCH = type("multi_clutch", MultiClutchBlockEntity::new, () -> RotaryBlocks.MULTI_CLUTCH.get());
	public static final Supplier<BlockEntityType<BigFurnaceBlockEntity>> BIG_FURNACE = type("big_furnace", BigFurnaceBlockEntity::new, () -> RotaryBlocks.BIG_FURNACE.get());
	public static final Supplier<BlockEntityType<SpillwayBlockEntity>> SPILLWAY = type("spillway", SpillwayBlockEntity::new, () -> RotaryBlocks.SPILLWAY.get());
	public static final Supplier<BlockEntityType<FluidCompressorBlockEntity>> FLUID_COMPRESSOR = type("fluid_compressor", FluidCompressorBlockEntity::new, () -> RotaryBlocks.FLUID_COMPRESSOR.get());
	public static final Supplier<BlockEntityType<LineBuilderBlockEntity>> LINE_BUILDER = type("line_builder", LineBuilderBlockEntity::new, () -> RotaryBlocks.LINE_BUILDER.get());
	public static final Supplier<BlockEntityType<MagnetizerBlockEntity>> MAGNETIZER = type("magnetizer", MagnetizerBlockEntity::new, () -> RotaryBlocks.MAGNETIZER.get());
	public static final Supplier<BlockEntityType<BeamMirrorBlockEntity>> BEAM_MIRROR = type("beam_mirror", BeamMirrorBlockEntity::new, () -> RotaryBlocks.BEAM_MIRROR.get());
	public static final Supplier<BlockEntityType<ItemRefresherBlockEntity>> ITEM_REFRESHER = type("item_refresher", ItemRefresherBlockEntity::new, () -> RotaryBlocks.ITEM_REFRESHER.get());
	public static final Supplier<BlockEntityType<GrindstoneBlockEntity>> GRINDSTONE = type("grindstone", GrindstoneBlockEntity::new, () -> RotaryBlocks.GRINDSTONE.get());
	public static final Supplier<BlockEntityType<BucketFillerBlockEntity>> BUCKET_FILLER = type("bucket_filler", BucketFillerBlockEntity::new, () -> RotaryBlocks.BUCKET_FILLER.get());
	public static final Supplier<BlockEntityType<SortingBlockEntity>> SORTING = type("sorting", SortingBlockEntity::new, () -> RotaryBlocks.SORTING.get());
	public static final Supplier<BlockEntityType<FertilizerBlockEntity>> FERTILIZER = type("fertilizer", FertilizerBlockEntity::new, () -> RotaryBlocks.FERTILIZER.get());
	public static final Supplier<BlockEntityType<LawnSprinklerBlockEntity>> LAWN_SPRINKLER = type("lawn_sprinkler", LawnSprinklerBlockEntity::new, () -> RotaryBlocks.LAWN_SPRINKLER.get());
	public static final Supplier<BlockEntityType<SprinklerBlockEntity>> SPRINKLER = type("sprinkler", SprinklerBlockEntity::new, () -> RotaryBlocks.SPRINKLER.get());
	public static final Supplier<BlockEntityType<IgniterBlockEntity>> IGNITER = type("firestarter", IgniterBlockEntity::new, () -> RotaryBlocks.IGNITER.get());
	public static final Supplier<BlockEntityType<ObsidianMakerBlockEntity>> OBSIDIAN_MAKER = type("obsidian_maker", ObsidianMakerBlockEntity::new, () -> RotaryBlocks.OBSIDIAN_MAKER.get());
	public static final Supplier<BlockEntityType<AutoBreederBlockEntity>> AUTO_BREEDER = type("auto_breeder", AutoBreederBlockEntity::new, () -> RotaryBlocks.AUTO_BREEDER.get());
	public static final Supplier<BlockEntityType<BaitBoxBlockEntity>> BAIT_BOX = type("bait_box", BaitBoxBlockEntity::new, () -> RotaryBlocks.BAIT_BOX.get());
	public static final Supplier<BlockEntityType<ScaleableChestBlockEntity>> SCALEABLE_CHEST = type("scaleable_chest", ScaleableChestBlockEntity::new, () -> RotaryBlocks.SCALEABLE_CHEST.get());
	public static final Supplier<BlockEntityType<VacuumBlockEntity>> VACUUM = type("vacuum", VacuumBlockEntity::new, () -> RotaryBlocks.VACUUM.get());
	public static final Supplier<BlockEntityType<BlowerBlockEntity>> BLOWER = type("blower", BlowerBlockEntity::new, () -> RotaryBlocks.BLOWER.get());
	public static final Supplier<BlockEntityType<AerosolizerBlockEntity>> AEROSOLIZER = type("aerosolizer", AerosolizerBlockEntity::new, () -> RotaryBlocks.AEROSOLIZER.get());
	public static final Supplier<BlockEntityType<FloodlightBlockEntity>> FLOODLIGHT = type("floodlight", FloodlightBlockEntity::new, () -> RotaryBlocks.FLOODLIGHT.get());
	public static final Supplier<BlockEntityType<WoodcutterBlockEntity>> WOODCUTTER = type("woodcutter", WoodcutterBlockEntity::new, () -> RotaryBlocks.WOODCUTTER.get());
	public static final Supplier<BlockEntityType<LightBridgeBlockEntity>> LIGHT_BRIDGE = type("light_bridge", LightBridgeBlockEntity::new, () -> RotaryBlocks.LIGHT_BRIDGE.get());
	public static final Supplier<BlockEntityType<BeltHubBlockEntity>> BELT_HUB = type("belt_hub", BeltHubBlockEntity::new, () -> RotaryBlocks.BELT_HUB.get());
	public static final Supplier<BlockEntityType<ChainDriveBlockEntity>> CHAIN_DRIVE = type("chain_drive", ChainDriveBlockEntity::new, () -> RotaryBlocks.CHAIN_DRIVE.get());
	public static final Supplier<BlockEntityType<SplitBeltBlockEntity>> SPLIT_BELT = type("split_belt", SplitBeltBlockEntity::new, () -> RotaryBlocks.SPLIT_BELT.get());
	public static final Supplier<BlockEntityType<DistributionClutchBlockEntity>> DISTRIBUTION_CLUTCH = type("distribution_clutch", DistributionClutchBlockEntity::new, () -> RotaryBlocks.DISTRIBUTION_CLUTCH.get());
	public static final Supplier<BlockEntityType<BusControllerBlockEntity>> BUS_CONTROLLER = type("bus_controller", BusControllerBlockEntity::new, () -> RotaryBlocks.BUS_CONTROLLER.get());
	public static final Supplier<BlockEntityType<PowerBusBlockEntity>> POWER_BUS = type("power_bus", PowerBusBlockEntity::new, () -> RotaryBlocks.POWER_BUS.get());
	public static final Supplier<BlockEntityType<CaveScannerBlockEntity>> CAVE_SCANNER = type("cave_scanner", CaveScannerBlockEntity::new, () -> RotaryBlocks.CAVE_SCANNER.get());
	public static final Supplier<BlockEntityType<DisplayBlockEntity>> DISPLAY = type("display", DisplayBlockEntity::new, () -> RotaryBlocks.DISPLAY.get());
	public static final Supplier<BlockEntityType<ScreenBlockEntity>> SCREEN = type("cctv_screen", ScreenBlockEntity::new, () -> RotaryBlocks.SCREEN.get());
	public static final Supplier<BlockEntityType<SpyCamBlockEntity>> SPY_CAM = type("spy_cam", SpyCamBlockEntity::new, () -> RotaryBlocks.SPY_CAM.get());
	public static final Supplier<BlockEntityType<SonicBorerBlockEntity>> SONIC_BORER = type("sonic_borer", SonicBorerBlockEntity::new, () -> RotaryBlocks.SONIC_BORER.get());
	public static final Supplier<BlockEntityType<PileDriverBlockEntity>> PILE_DRIVER = type("pile_driver", PileDriverBlockEntity::new, () -> RotaryBlocks.PILE_DRIVER.get());
	public static final Supplier<BlockEntityType<DefoliatorBlockEntity>> DEFOLIATOR = type("defoliator", DefoliatorBlockEntity::new, () -> RotaryBlocks.DEFOLIATOR.get());
	public static final Supplier<BlockEntityType<WeatherControllerBlockEntity>> WEATHER_CONTROLLER = type("weather_controller", WeatherControllerBlockEntity::new, () -> RotaryBlocks.WEATHER_CONTROLLER.get());
	public static final Supplier<BlockEntityType<ChunkLoaderBlockEntity>> CHUNK_LOADER = type("chunk_loader", ChunkLoaderBlockEntity::new, () -> RotaryBlocks.CHUNK_LOADER.get());
	public static final Supplier<BlockEntityType<ItemCannonBlockEntity>> ITEM_CANNON = type("item_cannon", ItemCannonBlockEntity::new, () -> RotaryBlocks.ITEM_CANNON.get());
	public static final Supplier<BlockEntityType<SpawnerControllerBlockEntity>> SPAWNER_CONTROLLER = type("spawner_controller", SpawnerControllerBlockEntity::new, () -> RotaryBlocks.SPAWNER_CONTROLLER.get());
	public static final Supplier<BlockEntityType<AdvancedGearBlockEntity>> ADVANCED_GEAR = TYPES.register("advanced_gear", () -> BlockEntityType.Builder.of(AdvancedGearBlockEntity::new, RotaryBlocks.WORM_GEAR.get(), RotaryBlocks.HIGH_GEAR.get(), RotaryBlocks.INDUSTRIAL_COIL.get(), RotaryBlocks.BEDROCK_COIL.get(), RotaryBlocks.CVT.get()).build(null));
	public static final Supplier<BlockEntityType<ItemFilterBlockEntity>> ITEM_FILTER = type("item_filter", ItemFilterBlockEntity::new, () -> RotaryBlocks.ITEM_FILTER.get());
	public static final Supplier<BlockEntityType<AutoCrafterBlockEntity>> AUTO_CRAFTER = type("auto_crafter", AutoCrafterBlockEntity::new, () -> RotaryBlocks.AUTO_CRAFTER.get());
	public static final Supplier<BlockEntityType<DropProcessorBlockEntity>> DROP_PROCESSOR = type("drop_processor", DropProcessorBlockEntity::new, () -> RotaryBlocks.DROP_PROCESSOR.get());
	public static final Supplier<BlockEntityType<MusicBoxBlockEntity>> MUSIC_BOX = type("music_box", MusicBoxBlockEntity::new, () -> RotaryBlocks.MUSIC_BOX.get());
	public static final Supplier<BlockEntityType<CctvBlockEntity>> CCTV = type("cctv", CctvBlockEntity::new, () -> RotaryBlocks.CCTV.get());
	public static final Supplier<BlockEntityType<PortalShaftBlockEntity>> PORTAL_SHAFT = type("portal_shaft", PortalShaftBlockEntity::new, () -> RotaryBlocks.PORTAL_SHAFT.get());
	public static final Supplier<BlockEntityType<TerraformerBlockEntity>> TERRAFORMER = type("terraformer", TerraformerBlockEntity::new, () -> RotaryBlocks.TERRAFORMER.get());
	public static final Supplier<BlockEntityType<ProjectorBlockEntity>> PROJECTOR = type("projector", ProjectorBlockEntity::new, () -> RotaryBlocks.PROJECTOR.get());
	public static final Supplier<BlockEntityType<MagneticEngineBlockEntity>> MAGNETIC_ENGINE = type("magnetic_engine", MagneticEngineBlockEntity::new, () -> RotaryBlocks.MAGNETIC_ENGINE.get());
	public static final Supplier<BlockEntityType<DynamoBlockEntity>> DYNAMO = type("dynamo", DynamoBlockEntity::new, () -> RotaryBlocks.DYNAMO.get());
	public static final Supplier<BlockEntityType<PurifierBlockEntity>> PURIFIER = type("purifier", PurifierBlockEntity::new, () -> RotaryBlocks.PURIFIER.get());
	public static final Supplier<BlockEntityType<FuelEngineBlockEntity>> FUEL_ENGINE = type("fuel_engine", FuelEngineBlockEntity::new, () -> RotaryBlocks.FUEL_ENGINE.get());
	public static final Supplier<BlockEntityType<DistilleryBlockEntity>> DISTILLERY = type("distillery", DistilleryBlockEntity::new, () -> RotaryBlocks.DISTILLERY.get());
	public static final Supplier<BlockEntityType<BoilerBlockEntity>> BOILER = type("boiler", BoilerBlockEntity::new, () -> RotaryBlocks.BOILER.get());
	public static final Supplier<BlockEntityType<SteamTurbineBlockEntity>> STEAM_TURBINE = type("steam_turbine", SteamTurbineBlockEntity::new, () -> RotaryBlocks.STEAM_TURBINE.get());
	public static final Supplier<BlockEntityType<AirCompressorBlockEntity>> AIR_COMPRESSOR = type("air_compressor", AirCompressorBlockEntity::new, () -> RotaryBlocks.AIR_COMPRESSOR.get());
	public static final Supplier<BlockEntityType<PneumaticEngineBlockEntity>> PNEUMATIC_ENGINE = type("pneumatic_engine", PneumaticEngineBlockEntity::new, () -> RotaryBlocks.PNEUMATIC_ENGINE.get());
	public static final Supplier<BlockEntityType<ForceFieldBlockEntity>> FORCE_FIELD = type("force_field", ForceFieldBlockEntity::new, () -> RotaryBlocks.FORCE_FIELD.get());
	public static final Supplier<BlockEntityType<ContainmentBlockEntity>> CONTAINMENT = type("containment", ContainmentBlockEntity::new, () -> RotaryBlocks.CONTAINMENT.get());
	public static final Supplier<BlockEntityType<AirGunBlockEntity>> AIR_GUN = type("air_gun", AirGunBlockEntity::new, () -> RotaryBlocks.AIR_GUN.get());
	public static final Supplier<BlockEntityType<LandmineBlockEntity>> LANDMINE = type("landmine", LandmineBlockEntity::new, () -> RotaryBlocks.LANDMINE.get());
	public static final Supplier<BlockEntityType<SelfDestructBlockEntity>> SELF_DESTRUCT = type("self_destruct", SelfDestructBlockEntity::new, () -> RotaryBlocks.SELF_DESTRUCT.get());
	public static final Supplier<BlockEntityType<SonicWeaponBlockEntity>> SONIC_WEAPON = type("sonic_weapon", SonicWeaponBlockEntity::new, () -> RotaryBlocks.SONIC_WEAPON.get());
	public static final Supplier<BlockEntityType<EmpBlockEntity>> EMP = type("emp", EmpBlockEntity::new, () -> RotaryBlocks.EMP.get());
	public static final Supplier<BlockEntityType<MachineGunBlockEntity>> MACHINE_GUN = type("machine_gun", MachineGunBlockEntity::new, () -> RotaryBlocks.MACHINE_GUN.get());
	public static final Supplier<BlockEntityType<TntCannonBlockEntity>> TNT_CANNON = type("tnt_cannon", TntCannonBlockEntity::new, () -> RotaryBlocks.TNT_CANNON.get());
	public static final Supplier<BlockEntityType<BlockCannonBlockEntity>> BLOCK_CANNON = type("block_cannon", BlockCannonBlockEntity::new, () -> RotaryBlocks.BLOCK_CANNON.get());
	public static final Supplier<BlockEntityType<RailgunBlockEntity>> RAILGUN = type("railgun", RailgunBlockEntity::new, () -> RotaryBlocks.RAILGUN.get());
	public static final Supplier<BlockEntityType<FreezeGunBlockEntity>> FREEZE_GUN = type("freeze_gun", FreezeGunBlockEntity::new, () -> RotaryBlocks.FREEZE_GUN.get());
	public static final Supplier<BlockEntityType<LaserGunBlockEntity>> LASER_GUN = type("laser_gun", LaserGunBlockEntity::new, () -> RotaryBlocks.LASER_GUN.get());
	public static final Supplier<BlockEntityType<MultiCannonBlockEntity>> MULTI_CANNON = type("multi_cannon", MultiCannonBlockEntity::new, () -> RotaryBlocks.MULTI_CANNON.get());
	public static final Supplier<BlockEntityType<AntiAirGunBlockEntity>> ANTI_AIR_GUN = type("anti_air_gun", AntiAirGunBlockEntity::new, () -> RotaryBlocks.ANTI_AIR_GUN.get());
	public static final Supplier<BlockEntityType<FlameTurretBlockEntity>> FLAME_TURRET = type("flame_turret", FlameTurretBlockEntity::new, () -> RotaryBlocks.FLAME_TURRET.get());
	public static final Supplier<BlockEntityType<FireworkMachineBlockEntity>> FIREWORK_MACHINE = type("firework_machine", FireworkMachineBlockEntity::new, () -> RotaryBlocks.FIREWORK_MACHINE.get());
	public static final Supplier<BlockEntityType<GprBlockEntity>> GPR = type("gpr", GprBlockEntity::new, () -> RotaryBlocks.GPR.get());
	public static final Supplier<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE = type("centrifuge", CentrifugeBlockEntity::new, () -> RotaryBlocks.CENTRIFUGE.get());
	public static final Supplier<BlockEntityType<FractionatorBlockEntity>> FRACTIONATOR = type("fractionator", FractionatorBlockEntity::new, () -> RotaryBlocks.FRACTIONATOR.get());
	public static final Supplier<BlockEntityType<PumpBlockEntity>> PUMP = type("pump", PumpBlockEntity::new, () -> RotaryBlocks.PUMP.get());
	public static final Supplier<BlockEntityType<ReservoirBlockEntity>> RESERVOIR = type("reservoir", ReservoirBlockEntity::new, () -> RotaryBlocks.RESERVOIR.get());
	public static final Supplier<BlockEntityType<PulseJetFurnaceBlockEntity>> PULSE_JET = type("pulse_jet_furnace", PulseJetFurnaceBlockEntity::new, () -> RotaryBlocks.PULSE_JET.get());
	public static final Supplier<BlockEntityType<FrictionHeaterBlockEntity>> FRICTION_HEATER = type("friction_heater", FrictionHeaterBlockEntity::new, () -> RotaryBlocks.FRICTION_HEATER.get());

	public static final Supplier<BlockEntityType<HoseBlockEntity>> HOSE = type("hose", HoseBlockEntity::new, () -> RotaryBlocks.HOSE.get());
	public static final Supplier<BlockEntityType<PipeBlockEntity>> PIPE = type("pipe", PipeBlockEntity::new, () -> RotaryBlocks.PIPE.get());
	public static final Supplier<BlockEntityType<FuelLineBlockEntity>> FUEL_LINE = type("fuel_line", FuelLineBlockEntity::new, () -> RotaryBlocks.FUEL_LINE.get());
	public static final Supplier<BlockEntityType<BedrockPipeBlockEntity>> BEDROCK_PIPE = type("bedrock_pipe", BedrockPipeBlockEntity::new, () -> RotaryBlocks.BEDROCK_PIPE.get());

	public static final Supplier<BlockEntityType<FlowPipeBlockEntity>> VALVE = type("valve", FlowPipeBlockEntity::valve, () -> RotaryBlocks.VALVE.get());
	public static final Supplier<BlockEntityType<FlowPipeBlockEntity>> BYPASS = type("bypass", FlowPipeBlockEntity::bypass, () -> RotaryBlocks.BYPASS.get());
	public static final Supplier<BlockEntityType<FlowPipeBlockEntity>> SEPARATION = type("separation", FlowPipeBlockEntity::separation, () -> RotaryBlocks.SEPARATION.get());
	public static final Supplier<BlockEntityType<FlowPipeBlockEntity>> SUCTION = type("suction", FlowPipeBlockEntity::suction, () -> RotaryBlocks.SUCTION.get());

	@SuppressWarnings("unchecked")
	public static List<Supplier<? extends BlockEntityType<? extends EngineBlockEntity>>> engines() {
		return List.of(DC_ENGINE, WIND_ENGINE, STEAM_ENGINE, GAS_ENGINE, AC_ENGINE, SPORT_ENGINE, MICRO_ENGINE, HYDRO_ENGINE, JET_ENGINE);
	}

	private RotaryBlockEntities() {}

	@SuppressWarnings("DataFlowIssue")
	private static <T extends BlockEntity> Supplier<BlockEntityType<T>> type(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<Block> block) {
		return TYPES.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
	}
}
