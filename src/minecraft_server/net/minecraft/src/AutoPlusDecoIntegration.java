package net.minecraft.src;

public class AutoPlusDecoIntegration {
	private static boolean isDecoInstalled = false;
	private static Class decoDefs = null;

	public static void init() {
		try {
			try {
				decoDefs = Class.forName("DecoDefs");
			} catch (ClassNotFoundException e) {
				try {
					decoDefs = Class.forName("net.minecraft.src.DecoDefs");
				} catch (ClassNotFoundException e1) {

				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
