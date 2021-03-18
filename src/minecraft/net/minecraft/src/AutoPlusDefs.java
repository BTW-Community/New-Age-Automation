package net.minecraft.src;

public class AutoPlusDefs {
	private static int
		id_stonecutter = 2500,
		id_loom = 2501,
		
		_ = 0;

	private static int
		id_beltLubricated = 25000;
	
	public static Block stonecutter;
	public static Block loom;
	
	public static Item beltLubricated;
	
	public static void addDefinitions() {
		stonecutter = new AutoPlusBlockStonecutter(id_stonecutter);
		Item.itemsList[stonecutter.blockID] = new ItemBlock(stonecutter.blockID - 256);
		
		loom = new AutoPlusBlockLoom(id_loom);
		Item.itemsList[loom.blockID] = new ItemBlock(loom.blockID - 256);
		TileEntity.addMapping(AutoPlusTileEntityLoom.class, "autoPlusLoom");

		beltLubricated = (new Item(id_beltLubricated)).SetBuoyant().SetIncineratedInCrucible().SetFilterableProperties(4).setUnlocalizedName("autoPlusItemBeltLubricated").setCreativeTab(CreativeTabs.tabMaterials);
		
		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			FCBetterThanWolves.fcAestheticOpaque = Block.replaceBlock(FCBetterThanWolves.fcAestheticOpaque.blockID, AutoPlusBlockAestheticOpaque.class);
		}

        FCBetterThanWolves.fcItemWheat.SetFilterableProperties(8);
		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			FCBetterThanWolves.fcBlockWickerPane = (FCBlockWickerPane) Block.replaceBlock(FCBetterThanWolves.fcBlockWickerPane.blockID, AutoPlusBlockWickerPane.class);
		}
		
		Block.redstoneComparatorIdle = (BlockComparator) Block.replaceBlock(Block.redstoneComparatorIdle.blockID, AutoPlusBlockComparator.class, false);
		Block.redstoneComparatorActive = (BlockComparator) Block.replaceBlock(Block.redstoneComparatorActive.blockID, AutoPlusBlockComparator.class, true);
		
		Item.comparator = Item.replaceItem(Item.comparator.itemID, FCItemPlacesAsBlock.class, Block.redstoneComparatorIdle.blockID).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone);
		
		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			FCBetterThanWolves.fcBlockDispenser = (FCBlockBlockDispenser) Block.replaceBlock(FCBetterThanWolves.fcBlockDispenser.blockID, AutoPlusBlockBlockDispenser.class);
		}
		FCBetterThanWolves.fcHopper = Block.replaceBlock(FCBetterThanWolves.fcHopper.blockID, AutoPlusBlockHopper.class);
		FCBetterThanWolves.fcBlockArcaneVessel = Block.replaceBlock(FCBetterThanWolves.fcBlockArcaneVessel.blockID, AutoPlusBlockArcaneVessel.class);
		FCBetterThanWolves.fcMillStone = Block.replaceBlock(FCBetterThanWolves.fcMillStone.blockID, AutoPlusBlockMillStone.class);
		FCBetterThanWolves.fcPulley = Block.replaceBlock(FCBetterThanWolves.fcPulley.blockID, AutoPlusBlockPulley.class);
		
		FCBetterThanWolves.fcBlockDetectorGlowingLogic.setCreativeTab(null);
		FCBetterThanWolves.fcBlockDetector.setCreativeTab(CreativeTabs.tabRedstone);
		FCBetterThanWolves.fcBlockArcaneVessel.setCreativeTab(CreativeTabs.tabRedstone);
	}
}