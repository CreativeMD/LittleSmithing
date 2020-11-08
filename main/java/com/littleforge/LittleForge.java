package com.littleforge;

import com.creativemd.creativecore.common.config.holder.CreativeConfigRegistry;
import com.creativemd.littletiles.common.structure.attribute.LittleStructureAttribute;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.server.LittleTilesServer;
import com.littleforge.common.item.ItemStructurePremade;
import com.littleforge.common.item.PremadeItemIronSludgeHammer;
import com.littleforge.common.item.PremadeItemStoneHammer;
import com.littleforge.common.item.PremadeItemSword;
import com.littleforge.common.item.PremadeItemWoodenTongs;
import com.littleforge.heated.structures.DirtyIronStructurePremade;
import com.littleforge.multitile.strucutres.BrickForgeInteractiveMultiTilePremade;
import com.littleforge.multitile.strucutres.ClaySmelteryInteractiveMultiTilePremade;
import com.littleforge.multitile.strucutres.ClaySmelteryTickingMultiTilePremade;
import com.littleforge.premade.structures.StoneAnvilStructurePremade;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = LittleForge.MODID, name = LittleForge.NAME, version = LittleForge.VERSION, guiFactory = "com.littleforge.client.LittleSmithingSettings", dependencies = "required-after:creativecore;required-after:littletiles")
@Mod.EventBusSubscriber
public class LittleForge {
	@SidedProxy(clientSide = "com.littleforge.client.LittleForgeClient", serverSide = "com.littleforge.server.LittleForgeServer")
	public static LittleTilesServer proxy;
	
	public static LittleSmithingConfig CONFIG;
	
	public static final String MODID = "littleforge";
	public static final String NAME = "Little Forge";
	public static final String VERSION = "1.0";
	
	public static ItemSword sword;
	public static Item hammer;
	public static Item ironHammer;
	public static Item woodenTongs;
	
	public static final ToolMaterial Test = EnumHelper.addToolMaterial(MODID, 3, 250, 8.0F, 100.0F, 10);
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		proxy.loadSidePre();
		sword = new PremadeItemSword(Test, "Sword", "sword");
		hammer = new PremadeItemStoneHammer("StoneHammer", "stone_hammer");
		ironHammer = new PremadeItemIronSludgeHammer("IronHammer", "iron_sludge_hammer");
		woodenTongs = new PremadeItemWoodenTongs("WoodenTongs", "wooden_tongs");
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(sword, hammer, woodenTongs, ironHammer);
		proxy.loadSide();
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		
	}
	
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		CreativeConfigRegistry.ROOT.registerValue(MODID, CONFIG = new LittleSmithingConfig());
		
		LittleStructurePremade.registerPremadeStructureType("dirty_iron", LittleForge.MODID, DirtyIronStructurePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		
		LittleStructurePremade.registerPremadeStructureType("stone_anvil", LittleForge.MODID, StoneAnvilStructurePremade.class);
		
		LittleStructurePremade.registerPremadeStructureType("sword", LittleForge.MODID, ItemStructurePremade.class);
		LittleStructurePremade.registerPremadeStructureType("stone_hammer", LittleForge.MODID, ItemStructurePremade.class);
		LittleStructurePremade.registerPremadeStructureType("iron_sludge_hammer", LittleForge.MODID, ItemStructurePremade.class);
		LittleStructurePremade.registerPremadeStructureType("wooden_tongs", LittleForge.MODID, ItemStructurePremade.class);
		LittleStructurePremade.registerPremadeStructureType("wooden_tongs_dirtyiron", LittleForge.MODID, ItemStructurePremade.class);
		
		LittleStructurePremade.registerPremadeStructureType(new LittleStructureTypePremade("brickForgeBasic_1", "premade", BrickForgeInteractiveMultiTilePremade.class, LittleStructureAttribute.PREMADE, LittleForge.MODID)).setNotShowCreativeTab();
		
		LittleStructurePremade.registerPremadeStructureType("brickForgeBasic_2", LittleForge.MODID, 
				BrickForgeInteractiveMultiTilePremade.class).setFieldDefault("facing", EnumFacing.UP);
		LittleStructurePremade.registerPremadeStructureType("testing", LittleForge.MODID, 
				BrickForgeInteractiveMultiTilePremade.class).setFieldDefault("facing", EnumFacing.UP);
		
		LittleStructurePremade.registerPremadeStructureType("clayForge_1", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_2", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_3", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_4", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_5", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		LittleStructurePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, ClaySmelteryInteractiveMultiTilePremade.class);
		
		//LittleStructurePremade.registerPremadeStructureType("clayForge_7", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_8", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_9", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_10", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_11", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_12", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING);
		LittleStructurePremade.registerPremadeStructureType("clayForge_13", LittleForge.MODID, ClaySmelteryTickingMultiTilePremade.class);
		
		proxy.loadSidePost();
		
		//LittleStructurePremade.registerPremadeStructureType("clayForge_6", LittleForge.MODID, MultiTileClayForge.class, LittleStructureAttribute.PREMADE | LittleStructureAttribute.TICKING );
		
		//MultiTileStructureRegistry.registerPremadeStructureType("clayForge", LittleForge.MODID, LittlePhotoImporter.class,6); 
		/*
		LittleForgeRecipeFactory.addRecipe("clayForge_1", new ItemStack(Items.CLAY_BALL, 8));
		LittleForgeRecipeFactory.addRecipe("clayForge_2", new ItemStack(Items.CLAY_BALL, 5));
		LittleForgeRecipeFactory.addRecipe("clayForge_3", new ItemStack(Items.CLAY_BALL, 5));
		LittleForgeRecipeFactory.addRecipe("clayForge_4", new ItemStack(Items.CLAY_BALL, 5));
		LittleForgeRecipeFactory.addRecipe("clayForge_5", new ItemStack(Items.FLINT, 1));
		LittleForgeRecipeFactory.addRecipe("clayForge_6", new ItemStack(Blocks.IRON_ORE, 1));
		LittleForgeRecipeFactory.addRecipe("clayForge_7", new ItemStack(Items.STICK, 64));
		*/
	}
}
