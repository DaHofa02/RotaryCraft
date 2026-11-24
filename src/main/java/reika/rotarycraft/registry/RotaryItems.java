/*******************************************************************************
 * @author Reika Kalseki (original RotaryCraft). Private NeoForge 1.21.1 port; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.registry;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.content.item.BedrockTools;
import reika.rotarycraft.content.item.CanolaSeedItem;
import reika.rotarycraft.content.item.CoilItem;
import reika.rotarycraft.content.item.CraftPatternItem;
import reika.rotarycraft.content.item.CraftingPart;
import reika.rotarycraft.content.item.EngineUpgradeItem;
import reika.rotarycraft.content.item.FluidBucketItem;
import reika.rotarycraft.content.item.HandbookItem;
import reika.rotarycraft.content.item.IntegratedGearboxItem;
import reika.rotarycraft.content.item.MeterItem;
import reika.rotarycraft.content.item.ScrewdriverItem;
import reika.rotarycraft.content.item.SpawnerItem;
import reika.rotarycraft.registry.GearboxTypes.GearPart;

/** Original: Reika.RotaryCraft.Registry.ItemRegistry, flattened: one item per former metadata value. */
public final class RotaryItems {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RotaryCraft.MOD_ID);

	private static final Map<CraftingPart, DeferredItem<Item>> PARTS = new EnumMap<>(CraftingPart.class);
	private static final Map<GearboxTypes, Map<GearPart, DeferredItem<Item>>> GEAR_PARTS = new EnumMap<>(GearboxTypes.class);
	private static final Map<Flywheels, DeferredItem<Item>> FLYWHEEL_CORES = new EnumMap<>(Flywheels.class);

	static {
		for (CraftingPart p : CraftingPart.values()) {
			PARTS.put(p, ITEMS.registerSimpleItem(p.id));
		}
		for (GearboxTypes g : GearboxTypes.typeList) {
			Map<GearPart, DeferredItem<Item>> m = new EnumMap<>(GearPart.class);
			for (GearPart p : GearPart.list) {
				m.put(p, ITEMS.registerSimpleItem(g.getSerializedName()+"_"+p.getSerializedName()));
			}
			GEAR_PARTS.put(g, m);
		}
		for (Flywheels f : Flywheels.list) {
			FLYWHEEL_CORES.put(f, ITEMS.registerSimpleItem(f.getSerializedName()+"_flywheel_core"));
		}
	}

	/** Original: ItemRegistry.YEAST / ETHANOL (ItemBasic). */
	public static final DeferredItem<Item> YEAST = ITEMS.registerSimpleItem("yeast");
	public static final DeferredItem<Item> ETHANOL = ITEMS.registerSimpleItem("ethanol");

	/** Original: ItemRegistry.SCREWDRIVER / METER (Angular Transducer). */
	/** Original SPRING (wind spring) and STRONGCOIL (high-strength spring), ItemCoil. */
	public static final DeferredItem<CoilItem> SPRING = ITEMS.register("spring",
			() -> new CoilItem(new Item.Properties(), false));
	public static final DeferredItem<CoilItem> STRONGCOIL = ITEMS.register("strongcoil",
			() -> new CoilItem(new Item.Properties(), true));
	public static final DeferredItem<ScrewdriverItem> SCREWDRIVER = ITEMS.register("screwdriver", () -> new ScrewdriverItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<MeterItem> METER = ITEMS.register("meter", () -> new MeterItem(new Item.Properties().stacksTo(1)));
	public static final DeferredItem<CraftPatternItem> CRAFT_PATTERN = ITEMS.register("craft_pattern", () -> new CraftPatternItem(new Item.Properties()));

	/** Original: ItemRegistry.UPGRADE (ItemEngineUpgrade), the ported variants. */
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_PERFORMANCE = ITEMS.register("upgrade_performance", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.PERFORMANCE, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_AFTERBURNER = ITEMS.register("upgrade_afterburner", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.AFTERBURNER, new Item.Properties()));
	/** Original GEARUPGRADE: the integrated gearbox frame (gear_upgrade) and the 2x/4x/8x/16x gears. */
	public static final List<DeferredItem<IntegratedGearboxItem>> GEAR_UPGRADES = new ArrayList<>();

	static {
		for (int i = 0; i <= 4; i++) {
			int lvl = i;
			GEAR_UPGRADES.add(ITEMS.register(i == 0 ? "gear_upgrade" : "gear_upgrade_"+(1 << i), () -> new IntegratedGearboxItem(new Item.Properties(), lvl)));
		}
	}

	public static final DeferredItem<EngineUpgradeItem> UPGRADE_REDSTONE = ITEMS.register("upgrade_redstone", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.REDSTONE, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_MAGNET_1 = ITEMS.register("upgrade_magnet_1", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.MAGNET_1, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_MAGNET_2 = ITEMS.register("upgrade_magnet_2", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.MAGNET_2, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_MAGNET_3 = ITEMS.register("upgrade_magnet_3", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.MAGNET_3, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_MAGNET_4 = ITEMS.register("upgrade_magnet_4", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.MAGNET_4, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_MAGNET_5 = ITEMS.register("upgrade_magnet_5", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.MAGNET_5, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_EFFICIENCY = ITEMS.register("upgrade_efficiency", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.EFFICIENCY, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_FLUX = ITEMS.register("upgrade_flux", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.FLUX, new Item.Properties()));
	public static final DeferredItem<EngineUpgradeItem> UPGRADE_LODESTONE = ITEMS.register("upgrade_lodestone", () -> new EngineUpgradeItem(EngineUpgradeItem.Upgrades.LODESTONE, new Item.Properties()));
	/** The port's own handbook (2026-11-18). */
	/** Original ItemRegistry.SPAWNER: a mob spawner taken with the bedrock pickaxe. */
	public static final DeferredItem<SpawnerItem> SPAWNER = ITEMS.register("spawner", () -> new SpawnerItem(new Item.Properties()));
	public static final DeferredItem<HandbookItem> HANDBOOK = ITEMS.register("handbook", () -> new HandbookItem(new Item.Properties()));
	/** Bedrock tools and armour (2026-11-18, own implementation; see BedrockTools). */
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
			DeferredRegister.create(Registries.ARMOR_MATERIAL, RotaryCraft.MOD_ID);
	public static final Holder<ArmorMaterial> BEDROCK_ARMOR = ARMOR_MATERIALS.register("bedrock", () -> new ArmorMaterial(
			Map.of(ArmorItem.Type.BOOTS, 4, ArmorItem.Type.LEGGINGS, 7, ArmorItem.Type.CHESTPLATE, 9,
					ArmorItem.Type.HELMET, 4, ArmorItem.Type.BODY, 12), 10,
			SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(part(CraftingPart.BEDINGOT)),
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(RotaryCraft.MOD_ID, "bedrock"))), 4F, 0.2F));
	public static final DeferredItem<Item> BEDROCK_PICKAXE = ITEMS.register("bedrock_pickaxe", BedrockTools.Pickaxe::new);
	public static final DeferredItem<Item> BEDROCK_AXE = ITEMS.register("bedrock_axe", BedrockTools.Axe::new);
	public static final DeferredItem<Item> BEDROCK_SHOVEL = ITEMS.register("bedrock_shovel", BedrockTools.Shovel::new);
	public static final DeferredItem<Item> BEDROCK_SWORD = ITEMS.register("bedrock_sword", BedrockTools.Sword::new);
	public static final DeferredItem<Item> BEDROCK_HOE = ITEMS.register("bedrock_hoe", BedrockTools.Hoe::new);
	public static final DeferredItem<Item> BEDROCK_SHEARS = ITEMS.register("bedrock_shears", BedrockTools.Shears::new);
	public static final DeferredItem<Item> BEDROCK_SCYTHE = ITEMS.register("bedrock_scythe", BedrockTools.Scythe::new);
	public static final DeferredItem<Item> BEDROCK_PAXEL = ITEMS.register("bedrock_paxel", BedrockTools.Paxel::new);
	public static final DeferredItem<Item> BEDROCK_HELMET = ITEMS.register("bedrock_helmet", () -> new BedrockTools.Armour(BEDROCK_ARMOR, ArmorItem.Type.HELMET));
	public static final DeferredItem<Item> BEDROCK_CHESTPLATE = ITEMS.register("bedrock_chestplate", () -> new BedrockTools.Armour(BEDROCK_ARMOR, ArmorItem.Type.CHESTPLATE));
	public static final DeferredItem<Item> BEDROCK_LEGGINGS = ITEMS.register("bedrock_leggings", () -> new BedrockTools.Armour(BEDROCK_ARMOR, ArmorItem.Type.LEGGINGS));
	public static final DeferredItem<Item> BEDROCK_BOOTS = ITEMS.register("bedrock_boots", () -> new BedrockTools.Armour(BEDROCK_ARMOR, ArmorItem.Type.BOOTS));

	/** Original: ItemRegistry.CANOLA (ItemCanolaSeed): seeds, dense seeds (plant 3x3), husks (from the Grinder). */
	public static final DeferredItem<CanolaSeedItem> CANOLA_SEEDS = ITEMS.register("canola_seeds", () -> new CanolaSeedItem(false, new Item.Properties()));
	public static final DeferredItem<CanolaSeedItem> DENSE_CANOLA_SEEDS = ITEMS.register("dense_canola_seeds", () -> new CanolaSeedItem(true, new Item.Properties()));
	public static final DeferredItem<Item> CANOLA_HUSKS = ITEMS.registerSimpleItem("canola_husks");

	/** Original: ItemRegistry.BUCKET (ItemFuelLubeBucket) 0-2; liquid nitrogen and molten HSLA come with their machines. */
	public static final DeferredItem<FluidBucketItem> LUBRICANT_BUCKET = bucket("lubricant_bucket", RotaryFluids.LUBRICANT);
	public static final DeferredItem<FluidBucketItem> JET_FUEL_BUCKET = bucket("jet_fuel_bucket", RotaryFluids.JET_FUEL);
	public static final DeferredItem<FluidBucketItem> ETHANOL_BUCKET = bucket("ethanol_bucket", RotaryFluids.ETHANOL);
	public static final DeferredItem<FluidBucketItem> LIQUID_NITROGEN_BUCKET = bucket("liquid_nitrogen_bucket", RotaryFluids.LIQUID_NITROGEN);

	private RotaryItems() {}

	private static DeferredItem<FluidBucketItem> bucket(String name, Supplier<? extends Fluid> fluid) {
		return ITEMS.register(name, () -> new FluidBucketItem(fluid.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
	}

	/** The bucket of a RotaryCraft fluid (BaseFlowingFluid.Properties.bucket, used to fill empty buckets from tanks). */
	public static Item bucketFor(String fluidId) {
		return switch (fluidId) {
			case "lubricant" -> LUBRICANT_BUCKET.get();
			case "jet_fuel" -> JET_FUEL_BUCKET.get();
			case "ethanol" -> ETHANOL_BUCKET.get();
			case "liquid_nitrogen" -> LIQUID_NITROGEN_BUCKET.get();
			default -> Items.AIR;
		};
	}

	public static Item ethanol() {
		return ETHANOL.get();
	}

	public static Item part(CraftingPart p) {
		return PARTS.get(p).get();
	}

	public static Item gearPart(GearboxTypes g, GearPart p) {
		return GEAR_PARTS.get(g).get(p).get();
	}

	public static Item flywheelCore(Flywheels f) {
		return FLYWHEEL_CORES.get(f).get();
	}

	/** Original: MaterialRegistry.getShaftUnitItem() - sticks for wood, otherwise the material's shaft part. */
	public static Item shaftUnitItem(MaterialRegistry mat) {
		if (mat == MaterialRegistry.WOOD)
			return Items.STICK;
		return gearPart(GearboxTypes.getFromMaterial(mat), GearPart.SHAFT);
	}

	public static Iterable<? extends Supplier<? extends Item>> all() {
		return ITEMS.getEntries();
	}
}
