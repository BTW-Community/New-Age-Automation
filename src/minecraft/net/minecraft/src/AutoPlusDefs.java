package net.minecraft.src;

public class AutoPlusDefs {
	private static int
		id_stonecutter = 2500;

	private static int
		id_beltLubricated = 25000;
	
	public static Block stonecutter;
	
	public static Item beltLubricated;
	
	public static void addDefinitions() {
		stonecutter = new AutoPlusBlockStonecutter(id_stonecutter);
		Item.itemsList[stonecutter.blockID] = new ItemBlock(stonecutter.blockID - 256);

		beltLubricated = (new Item(id_beltLubricated)).SetBuoyant().SetIncineratedInCrucible().SetFilterableProperties(4).setUnlocalizedName("autoPlusItemBeltLubricated").setCreativeTab(CreativeTabs.tabMaterials);
		
		if (!AutoPlusDecoIntegration.isDecoInstalled()) {
			FCBetterThanWolves.fcAestheticOpaque = Block.replaceBlock(FCBetterThanWolves.fcAestheticOpaque.blockID, AutoPlusBlockAestheticOpaque.class);
		}
	}
}