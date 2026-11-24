/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.client.render.AdvancedGearRenderer;
import reika.rotarycraft.client.render.AerosolizerRenderer;
import reika.rotarycraft.client.render.AggregatorRenderer;
import reika.rotarycraft.client.render.AutoBreederRenderer;
import reika.rotarycraft.client.render.BaitBoxRenderer;
import reika.rotarycraft.client.render.BeamMirrorRenderer;
import reika.rotarycraft.client.render.BeltHubRenderer;
import reika.rotarycraft.client.render.BevelRenderer;
import reika.rotarycraft.client.render.BigFurnaceRenderer;
import reika.rotarycraft.client.render.CaveScannerRenderer;
import reika.rotarycraft.client.render.CctvRenderer;
import reika.rotarycraft.client.render.CentrifugeRenderer;
import reika.rotarycraft.client.render.ChunkLoaderRenderer;
import reika.rotarycraft.client.render.ClutchRenderer;
import reika.rotarycraft.client.render.CompactorRenderer;
import reika.rotarycraft.client.render.ComposterRenderer;
import reika.rotarycraft.client.render.ConverterRenderer;
import reika.rotarycraft.client.render.CoolingFinRenderer;
import reika.rotarycraft.client.render.CrystallizerRenderer;
import reika.rotarycraft.client.render.DefoliatorRenderer;
import reika.rotarycraft.client.render.DischargeRenderer;
import reika.rotarycraft.client.render.DisplayRenderer;
import reika.rotarycraft.client.render.DistributionClutchRenderer;
import reika.rotarycraft.client.render.DomeRenderer;
import reika.rotarycraft.client.render.DryingBedRenderer;
import reika.rotarycraft.client.render.DynamometerRenderer;
import reika.rotarycraft.client.render.BedrockBreakerRenderer;
import reika.rotarycraft.client.render.EngineRenderer;
import reika.rotarycraft.client.render.ExtractorRenderer;
import reika.rotarycraft.client.render.FanRenderer;
import reika.rotarycraft.client.render.FertilizerRenderer;
import reika.rotarycraft.client.render.FillingStationRenderer;
import reika.rotarycraft.client.render.FloodlightRenderer;
import reika.rotarycraft.client.render.FluidCompressorRenderer;
import reika.rotarycraft.client.render.FractionatorRenderer;
import reika.rotarycraft.client.render.FrictionHeaterRenderer;
import reika.rotarycraft.client.render.FuelEnhancerRenderer;
import reika.rotarycraft.client.render.GrinderRenderer;
import reika.rotarycraft.client.render.GrindstoneRenderer;
import reika.rotarycraft.client.render.GroundHydratorRenderer;
import reika.rotarycraft.client.render.HeatRayRenderer;
import reika.rotarycraft.client.render.HeaterRenderer;
import reika.rotarycraft.client.render.IORenderer;
import reika.rotarycraft.client.render.FlywheelRenderer;
import reika.rotarycraft.client.render.GearboxRenderer;
import reika.rotarycraft.client.render.ItemCannonRenderer;
import reika.rotarycraft.client.render.LavaMakerRenderer;
import reika.rotarycraft.client.render.LawnSprinklerRenderer;
import reika.rotarycraft.client.render.LightBridgeRenderer;
import reika.rotarycraft.client.render.LineBuilderRenderer;
import reika.rotarycraft.client.render.MagnetizerRenderer;
import reika.rotarycraft.client.render.MirrorRenderer;
import reika.rotarycraft.client.render.MobHarvesterRenderer;
import reika.rotarycraft.client.render.MobRadarRenderer;
import reika.rotarycraft.client.render.MultiClutchRenderer;
import reika.rotarycraft.client.render.ObsidianMakerRenderer;
import reika.rotarycraft.client.render.OriginalModelRenderer;
import reika.rotarycraft.client.render.PileDriverRenderer;
import reika.rotarycraft.client.render.PipeFluidRenderer;
import reika.rotarycraft.client.render.PipePumpRenderer;
import reika.rotarycraft.client.render.PlayerDetectorRenderer;
import reika.rotarycraft.client.render.PortalShaftRenderer;
import reika.rotarycraft.client.render.ProjectorRenderer;
import reika.rotarycraft.client.render.PulseJetFurnaceRenderer;
import reika.rotarycraft.client.render.PumpRenderer;
import reika.rotarycraft.client.render.RefrigeratorRenderer;
import reika.rotarycraft.client.render.ReservoirRenderer;
import reika.rotarycraft.client.render.ScaleableChestRenderer;
import reika.rotarycraft.client.render.ScreenRenderer;
import reika.rotarycraft.client.render.ShaftRenderer;
import reika.rotarycraft.client.render.SmokeDetectorRenderer;
import reika.rotarycraft.client.render.SolarTowerRenderer;
import reika.rotarycraft.client.render.SonicBorerRenderer;
import reika.rotarycraft.client.render.SonicShotRenderer;
import reika.rotarycraft.client.render.SpawnerControllerRenderer;
import reika.rotarycraft.client.render.SpillwayRenderer;
import reika.rotarycraft.client.render.SplitterRenderer;
import reika.rotarycraft.client.render.SprinklerRenderer;
import reika.rotarycraft.client.render.SpyCamRenderer;
import reika.rotarycraft.client.render.VacuumRenderer;
import reika.rotarycraft.client.render.VanDeGraffRenderer;
import reika.rotarycraft.client.render.WeatherControllerRenderer;
import reika.rotarycraft.client.render.WetterRenderer;
import reika.rotarycraft.client.render.WinderRenderer;
import reika.rotarycraft.client.render.WoodcutterRenderer;
import reika.rotarycraft.client.screen.AerosolizerScreen;
import reika.rotarycraft.client.screen.AmmoScreen;
import reika.rotarycraft.client.screen.AutoBreederScreen;
import reika.rotarycraft.client.screen.AutoCrafterScreen;
import reika.rotarycraft.client.screen.BaitBoxScreen;
import reika.rotarycraft.client.screen.BevelScreen;
import reika.rotarycraft.client.screen.BigFurnaceScreen;
import reika.rotarycraft.client.screen.BlastFurnaceScreen;
import reika.rotarycraft.client.screen.BlowerScreen;
import reika.rotarycraft.client.screen.BorerScreen;
import reika.rotarycraft.client.screen.BucketFillerScreen;
import reika.rotarycraft.client.screen.CctvScreen;
import reika.rotarycraft.client.screen.CentrifugeScreen;
import reika.rotarycraft.client.screen.CoilScreen;
import reika.rotarycraft.client.screen.CompactorScreen;
import reika.rotarycraft.client.screen.ComposterScreen;
import reika.rotarycraft.client.screen.CraftPatternScreen;
import reika.rotarycraft.client.screen.CrystallizerScreen;
import reika.rotarycraft.client.screen.CvtScreen;
import reika.rotarycraft.client.screen.DefoliatorScreen;
import reika.rotarycraft.client.screen.DistributionClutchScreen;
import reika.rotarycraft.client.screen.DropProcessorScreen;
import reika.rotarycraft.client.screen.DryingBedScreen;
import reika.rotarycraft.client.screen.EngineScreens;
import reika.rotarycraft.client.screen.ExtractorScreen;
import reika.rotarycraft.client.screen.FermenterScreen;
import reika.rotarycraft.client.screen.FertilizerScreen;
import reika.rotarycraft.client.screen.FillingStationScreen;
import reika.rotarycraft.client.screen.FireworkMachineScreen;
import reika.rotarycraft.client.screen.FractionatorScreen;
import reika.rotarycraft.client.screen.GprScreen;
import reika.rotarycraft.client.screen.GrinderScreen;
import reika.rotarycraft.client.screen.HeaterScreen;
import reika.rotarycraft.client.screen.IgniterScreen;
import reika.rotarycraft.client.screen.ItemCannonScreen;
import reika.rotarycraft.client.screen.ItemFilterScreen;
import reika.rotarycraft.client.screen.LandmineScreen;
import reika.rotarycraft.client.screen.LavaMakerScreen;
import reika.rotarycraft.client.screen.LineBuilderScreen;
import reika.rotarycraft.client.screen.MagneticEngineScreen;
import reika.rotarycraft.client.screen.MobRadarScreen;
import reika.rotarycraft.client.screen.MultiClutchScreen;
import reika.rotarycraft.client.screen.MusicBoxScreen;
import reika.rotarycraft.client.screen.ObsidianMakerScreen;
import reika.rotarycraft.client.screen.ParticleEmitterScreen;
import reika.rotarycraft.client.screen.PlayerDetectorScreen;
import reika.rotarycraft.client.screen.PowerBusScreen;
import reika.rotarycraft.client.screen.ProjectorScreen;
import reika.rotarycraft.client.screen.PulseJetFurnaceScreen;
import reika.rotarycraft.client.screen.PurifierScreen;
import reika.rotarycraft.client.screen.RefrigeratorScreen;
import reika.rotarycraft.client.screen.ScaleableChestScreen;
import reika.rotarycraft.client.screen.ScreenScreen;
import reika.rotarycraft.client.screen.SortingScreen;
import reika.rotarycraft.client.screen.SpawnerControllerScreen;
import reika.rotarycraft.client.screen.SpyCamScreen;
import reika.rotarycraft.client.screen.SpyCamViewScreen;
import reika.rotarycraft.client.screen.TerraformerScreen;
import reika.rotarycraft.client.screen.VacuumScreen;
import reika.rotarycraft.client.screen.WeatherControllerScreen;
import reika.rotarycraft.client.screen.WinderScreen;
import reika.rotarycraft.client.screen.WorktableScreen;
import reika.rotarycraft.client.screen.GearboxScreen;
import reika.rotarycraft.client.screen.SplitterScreen;
import reika.rotarycraft.content.item.IntegratedGearboxItem;
import reika.rotarycraft.content.menu.MachineMenu.Layout;
import reika.rotarycraft.registry.RotaryBlockEntities;
import reika.rotarycraft.registry.RotaryEntities;
import reika.rotarycraft.registry.RotaryItems;
import reika.rotarycraft.registry.RotaryMenus;
import reika.rotarycraft.registry.RotaryBlocks;
import reika.rotarycraft.registry.RotaryFluids;

import java.util.List;

/** Original: Reika.RotaryCraft.ClientProxy (renderer registration). */
@EventBusSubscriber(modid = RotaryCraft.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RotaryClient {

	private RotaryClient() {}

	/** Original ItemIntegratedGearbox.getItemSpriteIndex: the sprite follows the fill (item model overrides on "rotarycraft:fill"). */
	@SubscribeEvent
	public static void setup(FMLClientSetupEvent e) {
		e.enqueueWork(() -> {
			for (var item : RotaryItems.GEAR_UPGRADES)
				ItemProperties.register(item.get(), ResourceLocation.fromNamespaceAndPath(RotaryCraft.MOD_ID, "fill"),
						(is, level, entity, seed) -> IntegratedGearboxItem.fillState(is));
		});
	}

	@SubscribeEvent
	public static void extensions(RegisterClientExtensionsEvent e) {
		MachineItemRenderer[] renderer = new MachineItemRenderer[1];
		IClientItemExtensions items = new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer[0] == null)
					renderer[0] = new MachineItemRenderer();
				return renderer[0];
			}
		};
		for (var block : RotaryBlocks.BLOCKS.getEntries())
			if (block.get().asItem() != Items.AIR)
				e.registerItem(items, block.get().asItem());

		fluid(e, RotaryFluids.LUBRICANT_TYPE.get(), "lubricant_anim");
		fluid(e, RotaryFluids.ETHANOL_TYPE.get(), "ethanol_anim");
		fluid(e, RotaryFluids.JET_FUEL_TYPE.get(), "jetfuel_anim");
		fluid(e, RotaryFluids.LIQUID_NITROGEN_TYPE.get(), "nitrogen_anim");
	}

	/** Original: EnhancedFluid.setIcons with the animated fluid texture for still and flowing. */
	private static void fluid(RegisterClientExtensionsEvent e, FluidType type, String texture) {
		ResourceLocation tex = ResourceLocation.fromNamespaceAndPath(RotaryCraft.MOD_ID, "block/fluid/"+texture);
		e.registerFluidType(new IClientFluidTypeExtensions() {
			@Override
			public ResourceLocation getStillTexture() {
				return tex;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return tex;
			}
		}, type);
	}

	@SubscribeEvent
	public static void screens(RegisterMenuScreensEvent e) {
		e.register(RotaryMenus.get(Layout.STEAM), EngineScreens.Steam::new);
		e.register(RotaryMenus.get(Layout.ETHANOL), EngineScreens.Ethanol::new);
		e.register(RotaryMenus.get(Layout.PERFORMANCE), EngineScreens.Performance::new);
		e.register(RotaryMenus.get(Layout.JET), EngineScreens.Jet::new);
		e.register(RotaryMenus.BLAST_FURNACE.get(), BlastFurnaceScreen::new);
		e.register(RotaryMenus.WORKTABLE.get(), WorktableScreen::new);
		e.register(RotaryMenus.EXTRACTOR.get(), ExtractorScreen::new);
		e.register(RotaryMenus.BORER.get(), BorerScreen::new);
		e.register(RotaryMenus.PLAYER_DETECTOR.get(), PlayerDetectorScreen::new);
		e.register(RotaryMenus.MOB_RADAR.get(), MobRadarScreen::new);
		e.register(RotaryMenus.LAVA_MAKER.get(), LavaMakerScreen::new);
		e.register(RotaryMenus.DEFOLIATOR.get(), DefoliatorScreen::new);
		e.register(RotaryMenus.WEATHER_CONTROLLER.get(), WeatherControllerScreen::new);
		e.register(RotaryMenus.ITEM_CANNON.get(), ItemCannonScreen::new);
		e.register(RotaryMenus.SPAWNER_CONTROLLER.get(), SpawnerControllerScreen::new);
		e.register(RotaryMenus.COIL.get(), CoilScreen::new);
		e.register(RotaryMenus.CVT.get(), CvtScreen::new);
		e.register(RotaryMenus.ITEM_FILTER.get(), ItemFilterScreen::new);
		e.register(RotaryMenus.AUTO_CRAFTER.get(), AutoCrafterScreen::new);
		e.register(RotaryMenus.DROP_PROCESSOR.get(), DropProcessorScreen::new);
		e.register(RotaryMenus.MUSIC_BOX.get(), MusicBoxScreen::new);
		e.register(RotaryMenus.CCTV.get(), CctvScreen::new);
		e.register(RotaryMenus.TERRAFORMER.get(), TerraformerScreen::new);
		e.register(RotaryMenus.PROJECTOR.get(), ProjectorScreen::new);
		e.register(RotaryMenus.MAGNETIC_ENGINE.get(), MagneticEngineScreen::new);
		e.register(RotaryMenus.PURIFIER.get(), PurifierScreen::new);
		e.register(RotaryMenus.LANDMINE.get(), LandmineScreen::new);
		e.register(RotaryMenus.AMMO.get(), AmmoScreen::new);
		e.register(RotaryMenus.CRAFT_PATTERN.get(), CraftPatternScreen::new);
		e.register(RotaryMenus.FIREWORK_MACHINE.get(), FireworkMachineScreen::new);
		e.register(RotaryMenus.SPY_CAM.get(), SpyCamScreen::new);
		e.register(RotaryMenus.SPY_CAM_VIEW.get(), SpyCamViewScreen::new);
		e.register(RotaryMenus.SCREEN.get(), ScreenScreen::new);
		e.register(RotaryMenus.GPR.get(), GprScreen::new);
		e.register(RotaryMenus.POWER_BUS.get(), PowerBusScreen::new);
		e.register(RotaryMenus.DISTRIBUTION_CLUTCH.get(), DistributionClutchScreen::new);
		e.register(RotaryMenus.AEROSOLIZER.get(), AerosolizerScreen::new);
		e.register(RotaryMenus.BLOWER.get(), BlowerScreen::new);
		e.register(RotaryMenus.VACUUM.get(), VacuumScreen::new);
		e.register(RotaryMenus.SCALEABLE_CHEST.get(), ScaleableChestScreen::new);
		e.register(RotaryMenus.BAIT_BOX.get(), BaitBoxScreen::new);
		e.register(RotaryMenus.AUTO_BREEDER.get(), AutoBreederScreen::new);
		e.register(RotaryMenus.OBSIDIAN_MAKER.get(), ObsidianMakerScreen::new);
		e.register(RotaryMenus.IGNITER.get(), IgniterScreen::new);
		e.register(RotaryMenus.FERTILIZER.get(), FertilizerScreen::new);
		e.register(RotaryMenus.SORTING.get(), SortingScreen::new);
		e.register(RotaryMenus.BUCKET_FILLER.get(), BucketFillerScreen::new);
		e.register(RotaryMenus.LINE_BUILDER.get(), LineBuilderScreen::new);
		e.register(RotaryMenus.BIG_FURNACE.get(), BigFurnaceScreen::new);
		e.register(RotaryMenus.MULTI_CLUTCH.get(), MultiClutchScreen::new);
		e.register(RotaryMenus.PARTICLE_EMITTER.get(), ParticleEmitterScreen::new);
		e.register(RotaryMenus.CRYSTALLIZER.get(), CrystallizerScreen::new);
		e.register(RotaryMenus.COMPOSTER.get(), ComposterScreen::new);
		e.register(RotaryMenus.HEATER.get(), HeaterScreen::new);
		e.register(RotaryMenus.GRINDER.get(), GrinderScreen::new);
		e.register(RotaryMenus.FERMENTER.get(), FermenterScreen::new);
		e.register(RotaryMenus.DRYING_BED.get(), DryingBedScreen::new);
		e.register(RotaryMenus.WINDER.get(), WinderScreen::new);
		e.register(RotaryMenus.REFRIGERATOR.get(), RefrigeratorScreen::new);
		e.register(RotaryMenus.COMPACTOR.get(), CompactorScreen::new);
		e.register(RotaryMenus.FILLING_STATION.get(), FillingStationScreen::new);
		e.register(RotaryMenus.CENTRIFUGE.get(), CentrifugeScreen::new);
		e.register(RotaryMenus.FRACTIONATOR.get(), FractionatorScreen::new);
		e.register(RotaryMenus.PULSE_JET.get(), PulseJetFurnaceScreen::new);
		e.register(RotaryMenus.get(Layout.ONE_SLOT), EngineScreens.OneSlot::new);
		e.register(RotaryMenus.get(Layout.GEARBOX), GearboxScreen::new);
		e.register(RotaryMenus.get(Layout.SPLITTER), SplitterScreen::new);
		e.register(RotaryMenus.get(Layout.BEVEL), BevelScreen::new);
	}

	@SubscribeEvent
	public static void layers(EntityRenderersEvent.RegisterLayerDefinitions e) {
		RotaryModelLayers.register(e);
	}

	private static <T extends BlockEntity> void originalOnly(EntityRenderersEvent.RegisterRenderers e,
			BlockEntityType<T> type) {
		String id = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(type).getPath();
		e.registerBlockEntityRenderer(type, ctx -> new OriginalModelRenderer<>(id, null));
	}

	/** The optional private original-models pack is read on every resource reload. */
	@SubscribeEvent
	public static void reloadListeners(RegisterClientReloadListenersEvent e) {
		e.registerReloadListener(OriginalModels.INSTANCE);
	}

	@SubscribeEvent
	public static void renderers(EntityRenderersEvent.RegisterRenderers e) {
		e.registerBlockEntityRenderer(RotaryBlockEntities.SHAFT.get(), ctx -> new IORenderer<>(new ShaftRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.GEARBOX.get(), ctx -> new IORenderer<>(new GearboxRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.BEVEL_GEARS.get(), ctx -> new IORenderer<>(new BevelRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CLUTCH.get(), ctx -> new IORenderer<>(new ClutchRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DYNAMOMETER.get(), ctx -> new IORenderer<>(new DynamometerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SHAFT_JUNCTION.get(), ctx -> new IORenderer<>(new SplitterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FLYWHEEL.get(), ctx -> new IORenderer<>(new FlywheelRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FRICTION_HEATER.get(), ctx -> new IORenderer<>(new FrictionHeaterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.COOLING_FIN.get(), ctx -> new CoolingFinRenderer(ctx));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FUEL_ENHANCER.get(), ctx -> new IORenderer<>(new FuelEnhancerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.MIRROR.get(), ctx -> new MirrorRenderer(ctx));
		e.registerBlockEntityRenderer(RotaryBlockEntities.WINDER.get(), ctx -> new IORenderer<>(new WinderRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FILLING_STATION.get(), ctx -> new IORenderer<>(new FillingStationRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.COMPACTOR.get(), ctx -> new IORenderer<>(new CompactorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.HEAT_RAY.get(), ctx -> new IORenderer<>(new HeatRayRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.REFRIGERATOR.get(), ctx -> new IORenderer<>(new RefrigeratorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DRYING_BED.get(), DryingBedRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.VAN_DE_GRAFF.get(), ctx -> new IORenderer<>(new VanDeGraffRenderer(ctx)));
		e.registerEntityRenderer(RotaryEntities.DISCHARGE.get(), DischargeRenderer::new);
		e.registerEntityRenderer(RotaryEntities.SONIC_SHOT.get(), SonicShotRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.PIPE_PUMP.get(), ctx -> new IORenderer<>(new PipePumpRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SOLAR_TOWER.get(), ctx -> new IORenderer<>(new SolarTowerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.EXTRACTOR.get(), ctx -> new IORenderer<>(new ExtractorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.GRINDER.get(), ctx -> new IORenderer<>(new GrinderRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CENTRIFUGE.get(), ctx -> new IORenderer<>(new CentrifugeRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FAN.get(), ctx -> new IORenderer<>(new FanRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.MOB_HARVESTER.get(), ctx -> new IORenderer<>(new MobHarvesterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.AGGREGATOR.get(), ctx -> new IORenderer<>(new AggregatorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FRACTIONATOR.get(), ctx -> new IORenderer<>(new FractionatorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PUMP.get(), ctx -> new IORenderer<>(new PumpRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SMOKE_DETECTOR.get(), ctx -> new IORenderer<>(new SmokeDetectorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PLAYER_DETECTOR.get(), ctx -> new IORenderer<>(new PlayerDetectorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.MOB_RADAR.get(), ctx -> new IORenderer<>(new MobRadarRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.LAVA_MAKER.get(), ctx -> new IORenderer<>(new LavaMakerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DEFOLIATOR.get(), ctx -> new IORenderer<>(new DefoliatorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.WEATHER_CONTROLLER.get(), ctx -> new IORenderer<>(new WeatherControllerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CHUNK_LOADER.get(), ctx -> new IORenderer<>(new ChunkLoaderRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.ITEM_CANNON.get(), ctx -> new IORenderer<>(new ItemCannonRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SPAWNER_CONTROLLER.get(), ctx -> new IORenderer<>(new SpawnerControllerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.ADVANCED_GEAR.get(), ctx -> new IORenderer<>(new AdvancedGearRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CCTV.get(), ctx -> new IORenderer<>(new CctvRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PORTAL_SHAFT.get(), ctx -> new IORenderer<>(new PortalShaftRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PROJECTOR.get(), ProjectorRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.MAGNETIC_ENGINE.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "magnetic_engine")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DYNAMO.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "dynamo")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FUEL_ENGINE.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "fuel_engine")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.STEAM_TURBINE.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "steam_turbine")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.AIR_COMPRESSOR.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "air_compressor")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PNEUMATIC_ENGINE.get(), ctx -> new IORenderer<>(new ConverterRenderer<>(ctx, "pneumatic_engine")));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FORCE_FIELD.get(), ctx -> new DomeRenderer<>(ctx, 0x2a50a0ff));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CONTAINMENT.get(), ctx -> new OriginalModelRenderer<>("containment",
				new DomeRenderer<>(ctx, 0x2aff4060)));
		//cubes in the port, models in the original: drawn only with the private original-models pack (OriginalModels)
		for (var t : List.of(RotaryBlockEntities.TNT_CANNON, RotaryBlockEntities.BLOCK_CANNON, RotaryBlockEntities.SONIC_WEAPON, RotaryBlockEntities.RAILGUN,
				RotaryBlockEntities.FREEZE_GUN, RotaryBlockEntities.LASER_GUN, RotaryBlockEntities.LANDMINE, RotaryBlockEntities.EMP, RotaryBlockEntities.AIR_GUN,
				RotaryBlockEntities.DISTILLERY, RotaryBlockEntities.FLAME_TURRET, RotaryBlockEntities.ANTI_AIR_GUN, RotaryBlockEntities.MULTI_CANNON, RotaryBlockEntities.BOILER))
			originalOnly(e, t.get());
		e.registerBlockEntityRenderer(RotaryBlockEntities.PILE_DRIVER.get(), ctx -> new IORenderer<>(new PileDriverRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SONIC_BORER.get(), ctx -> new IORenderer<>(new SonicBorerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SPY_CAM.get(), ctx -> new IORenderer<>(new SpyCamRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SCREEN.get(), ctx -> new IORenderer<>(new ScreenRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DISPLAY.get(), ctx -> new IORenderer<>(new DisplayRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CAVE_SCANNER.get(), ctx -> new IORenderer<>(new CaveScannerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.DISTRIBUTION_CLUTCH.get(), ctx -> new IORenderer<>(new DistributionClutchRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SPLIT_BELT.get(), ctx -> new IORenderer<>(new BeltHubRenderer<>(ctx, RotaryModelLayers.SPLIT_BELT)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CHAIN_DRIVE.get(), ctx -> new IORenderer<>(new BeltHubRenderer<>(ctx, RotaryModelLayers.CHAIN_DRIVE)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.BELT_HUB.get(), ctx -> new IORenderer<>(new BeltHubRenderer<>(ctx, RotaryModelLayers.BELT_HUB)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.LIGHT_BRIDGE.get(), ctx -> new IORenderer<>(new LightBridgeRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.WOODCUTTER.get(), ctx -> new IORenderer<>(new WoodcutterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FLOODLIGHT.get(), ctx -> new IORenderer<>(new FloodlightRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.AEROSOLIZER.get(), ctx -> new IORenderer<>(new AerosolizerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.VACUUM.get(), ctx -> new IORenderer<>(new VacuumRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SCALEABLE_CHEST.get(), ctx -> new IORenderer<>(new ScaleableChestRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.BAIT_BOX.get(), ctx -> new IORenderer<>(new BaitBoxRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.AUTO_BREEDER.get(), ctx -> new IORenderer<>(new AutoBreederRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.OBSIDIAN_MAKER.get(), ctx -> new IORenderer<>(new ObsidianMakerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SPRINKLER.get(), ctx -> new IORenderer<>(new SprinklerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.LAWN_SPRINKLER.get(), ctx -> new IORenderer<>(new LawnSprinklerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FERTILIZER.get(), ctx -> new IORenderer<>(new FertilizerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.GRINDSTONE.get(), ctx -> new IORenderer<>(new GrindstoneRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.BEAM_MIRROR.get(), ctx -> new IORenderer<>(new BeamMirrorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.MAGNETIZER.get(), ctx -> new IORenderer<>(new MagnetizerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.LINE_BUILDER.get(), ctx -> new IORenderer<>(new LineBuilderRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.FLUID_COMPRESSOR.get(), ctx -> new IORenderer<>(new FluidCompressorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.SPILLWAY.get(), ctx -> new IORenderer<>(new SpillwayRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.BIG_FURNACE.get(), ctx -> new IORenderer<>(new BigFurnaceRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.MULTI_CLUTCH.get(), ctx -> new IORenderer<>(new MultiClutchRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.WETTER.get(), ctx -> new IORenderer<>(new WetterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.HYDRATOR.get(), ctx -> new IORenderer<>(new GroundHydratorRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.CRYSTALLIZER.get(), ctx -> new IORenderer<>(new CrystallizerRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.COMPOSTER.get(), ctx -> new IORenderer<>(new ComposterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.HEATER.get(), ctx -> new IORenderer<>(new HeaterRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.PULSE_JET.get(), ctx -> new IORenderer<>(new PulseJetFurnaceRenderer(ctx)));
		e.registerBlockEntityRenderer(RotaryBlockEntities.RESERVOIR.get(), ReservoirRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.BEDROCK_BREAKER.get(), ctx -> new IORenderer<>(new BedrockBreakerRenderer(ctx)));
		//Original PipeRenderer: no I/O overlay
		e.registerBlockEntityRenderer(RotaryBlockEntities.HOSE.get(), PipeFluidRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.PIPE.get(), PipeFluidRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.FUEL_LINE.get(), PipeFluidRenderer::new);
		e.registerBlockEntityRenderer(RotaryBlockEntities.BEDROCK_PIPE.get(), PipeFluidRenderer::new);
		for (var t : List.of(RotaryBlockEntities.VALVE, RotaryBlockEntities.BYPASS, RotaryBlockEntities.SEPARATION, RotaryBlockEntities.SUCTION))
			e.registerBlockEntityRenderer(t.get(), PipeFluidRenderer::new);
		for (var type : RotaryBlockEntities.engines())
			e.registerBlockEntityRenderer(type.get(), ctx -> new IORenderer<>(new EngineRenderer(ctx)));
	}
}
