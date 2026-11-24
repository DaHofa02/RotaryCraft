/*******************************************************************************
 * Private NeoForge 1.21.1 port of RotaryCraft; not for distribution.
 ******************************************************************************/
package reika.rotarycraft.client;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joml.Matrix4f;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import reika.rotarycraft.RotaryCraft;
import reika.rotarycraft.client.model.TechneModel;

/**
 * Optional original models from a private resource pack built by tools/build_originals_pack.py (the pack is not part of the mod; the
 * original models may only be used privately). A model is assets/rotarycraft/models/block/original/&lt;id&gt;.json: a standard JSON block model
 * whose elements carry the original Techne part data under "rotarycraft:part" (texture offset, box, pivot, rotation, mirror, texture
 * size and the renderAll matrix). Those are built into model parts here and drawn in the machine renderer's model space, exactly as the
 * original drew them. Without the pack nothing is loaded and the machines keep the port's own models.
 */
public final class OriginalModels implements ResourceManagerReloadListener {

	public static final OriginalModels INSTANCE = new OriginalModels();

	/** Machines the pack may cover. */
	public static final List<String> IDS = List.of("worm_gear", "cvt", "industrial_coil", "bedrock_coil", "high_gear", "magnetic_engine", "dynamo",
			"fuel_engine", "steam_turbine", "air_compressor", "pneumatic_engine", "portal_shaft", "projector", "cctv",
			"tnt_cannon", "block_cannon", "sonic_weapon", "railgun", "freeze_gun", "laser_gun", "landmine", "emp", "air_gun", "distillery",
			"flame_turret", "anti_air_gun", "multi_cannon", "containment", "boiler");

	private record Drawn(ModelPart part, Matrix4f matrix) {}

	private record Model(ResourceLocation texture, List<Drawn> parts) {}

	private final Map<String, Model> models = new HashMap<>();

	private OriginalModels() {}

	public static boolean has(String id) {
		return INSTANCE.models.containsKey(id);
	}

	/**
	 * Draws the original model of a machine if the pack provides it; the pose must be the machine renderer's model space (after the
	 * usual translate 0,2,1 / scale 1,-1,-1 / translate 0.5 and the facing rotation). Returns false when there is no such model.
	 */
	public static boolean render(String id, PoseStack pose, MultiBufferSource buf, int light, int overlay) {
		Model m = INSTANCE.models.get(id);
		if (m == null)
			return false;
		var vc = buf.getBuffer(RenderType.entityCutout(m.texture));
		for (Drawn d : m.parts) {
			pose.pushPose();
			pose.mulPose(d.matrix);
			d.part.render(pose, vc, light, overlay);
			pose.popPose();
		}
		return true;
	}

	/**
	 * Draws the original model with the original renderer's facing rotation, for a pose in the renderer's base model space (after
	 * translate 0,2,1 / scale 1,-1,-1 / translate 0.5, before any facing rotation). Returns false when the pack has no such model.
	 */
	public static boolean renderFacing(String id, int meta, boolean inWorld, PoseStack pose, MultiBufferSource buf, int light, int overlay) {
		if (!has(id))
			return false;
		pose.pushPose();
		facing(id, meta, inWorld, pose);
		render(id, pose, buf, light, overlay);
		pose.popPose();
		return true;
	}

	private static void yaw(PoseStack pose, float deg) {
		pose.mulPose(TechneModel.rotation(deg, 0, 1, 0));
	}

	/**
	 * The original renderers' rotations, with the port's metadata: where a machine's metadata means something else in the port than in
	 * the original (the converters: port meta 0-3 = output west, east, north, south), the original metadata with the same output side
	 * is used.
	 */
	private static void facing(String id, int meta, boolean inWorld, PoseStack pose) {
		int m = meta%4;
		switch (id) {
			case "worm_gear", "cvt", "industrial_coil", "bedrock_coil", "high_gear" -> //RenderAdvGear
				yaw(pose, inWorld ? new int[] {0, 180, 90, 270}[m]+180 : -90);
			case "magnetic_engine" -> yaw(pose, new int[] {90, 270, 180, 0}[m]); //RenderMagnetic via EnergyToPowerBase sides
			case "steam_turbine" -> yaw(pose, new int[] {270, 90, 0, 180}[m]); //RenderSteam
			case "pneumatic_engine" -> yaw(pose, new int[] {0, 180, 90, 270}[m]); //RenderPneumatic
			case "fuel_engine" -> yaw(pose, new int[] {270, 90, 0, 180}[m]); //RenderFuelEngine (same sides as the port)
			case "dynamo", "air_compressor" -> { //RenderDynamo / RenderCompressor: models stand upright, laid down for the side they read from
				int orig = id.equals("dynamo") ? new int[] {3, 5, 2, 4}[m] : new int[] {5, 3, 4, 2}[m];
				int z = id.equals("dynamo") ? new int[] {0, 0, 0, 90, 180, 270}[orig] : new int[] {0, 180, 0, 90, 180, 270}[orig];
				pose.mulPose(TechneModel.rotation(90, 1, 0, 0));
				pose.mulPose(TechneModel.rotation(z, 0, 0, 1));
				pose.translate(0, -1, -1);
			}
			case "portal_shaft" -> { //RenderPortalShaft
				if (meta < 4)
					yaw(pose, new int[] {0, 180, 90, 270}[m]);
				else {
					pose.translate(0, 1, 0);
					pose.mulPose(TechneModel.rotation(90, 1, 0, 0));
					pose.translate(0, -1, 0);
				}
			}
			case "projector" -> yaw(pose, new int[] {0, 180, 270, 90}[m]+180); //RenderProjector
			case "cctv" -> { //RenderCCTV (the camera's own turning is not shown)
				pose.translate(0, -0.75F, 0);
				yaw(pose, -90);
			}
			default -> {} //the weapons and the rest: no facing in the original either
		}
	}

	@Override
	public void onResourceManagerReload(ResourceManager rm) {
		models.clear();
		for (String id : IDS) {
			Optional<Resource> res = rm.getResource(ResourceLocation.fromNamespaceAndPath(RotaryCraft.MOD_ID, "models/block/original/"+id+".json"));
			if (res.isEmpty())
				continue;
			try (Reader r = res.get().openAsReader()) {
				models.put(id, load(JsonParser.parseReader(r).getAsJsonObject()));
			}
			catch (Exception e) {
				RotaryCraft.LOGGER.warn("Original model {} could not be read: {}", id, e.toString());
			}
		}
		if (!models.isEmpty())
			RotaryCraft.LOGGER.info("Original models from a resource pack: {}", models.keySet());
	}

	private static Model load(JsonObject json) {
		ResourceLocation tex = ResourceLocation.parse(json.get("rotarycraft:texture").getAsString());
		List<Drawn> parts = new ArrayList<>();
		for (JsonElement e : json.getAsJsonArray("elements")) {
			JsonObject p = e.getAsJsonObject().getAsJsonObject("rotarycraft:part");
			if (p == null)
				continue;
			float[] box = floats(p.getAsJsonArray("box"));
			float[] pivot = floats(p.getAsJsonArray("pivot"));
			float[] rot = floats(p.getAsJsonArray("rotation"));
			float[] size = floats(p.getAsJsonArray("texture_size"));
			MeshDefinition mesh = new MeshDefinition();
			PartDefinition root = mesh.getRoot();
			CubeListBuilder cube = CubeListBuilder.create().texOffs(p.get("u").getAsInt(), p.get("v").getAsInt()).mirror(p.get("mirror").getAsBoolean())
					.addBox(box[0], box[1], box[2], box[3], box[4], box[5]);
			root.addOrReplaceChild("box", cube, PartPose.offsetAndRotation(pivot[0], pivot[1], pivot[2], rot[0], rot[1], rot[2]));
			ModelPart part = LayerDefinition.create(mesh, (int)size[0], (int)size[1]).bakeRoot();
			parts.add(new Drawn(part, new Matrix4f().set(floats(p.getAsJsonArray("matrix")))));
		}
		return new Model(tex, parts);
	}

	private static float[] floats(JsonArray a) {
		float[] f = new float[a.size()];
		for (int i = 0; i < f.length; i++)
			f[i] = a.get(i).getAsFloat();
		return f;
	}
}
