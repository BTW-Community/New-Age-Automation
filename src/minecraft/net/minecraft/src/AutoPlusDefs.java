package net.minecraft.src;

public class AutoPlusDefs {
	private static int
		id_stonecutter = 2500;
	
	public static Block stonecutter;
	
	public static void addDefinitions() {
		stonecutter = new AutoPlusBlockStonecutter(id_stonecutter);
		Item.itemsList[stonecutter.blockID] = new ItemBlock(stonecutter.blockID - 256);
	}
}