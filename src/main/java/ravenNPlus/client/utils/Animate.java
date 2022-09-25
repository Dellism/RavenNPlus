package ravenNPlus.client.utils;

public class Animate {
	
	public static float ease(float alpha) {
	    return (float) (alpha < 0.5 ? 4 * alpha * alpha * alpha : 1 - Math.pow(-2 * alpha + 2, 3) / 2);
	}

	public static float getProgress(long startTime, float duration) {
	    long time = System.currentTimeMillis();
	    float progress = (time - startTime) / duration;

	    if (progress <= 0.0F) return 0.0F;
	    if (progress >= 1.0F) return 1.0F;

	    return progress;
	}

	public static int toPixels(float start, float end, float alpha) {
	    return (int) ((end - start) * ease(alpha));
	}
	
	public static Object startTime(long l) {
		return l;
	}
	
}