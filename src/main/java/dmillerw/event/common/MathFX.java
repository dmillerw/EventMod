package dmillerw.event.common;

/**
 * Mostly pulled from Unity's Mathfx class
 *
 * @author dmillerw
 */
public class MathFX {

	/* UTILITY METHODS */
    public static float clamp(float low, float high, float value) {
		if (value < low) {
			return low;
		} else if (value > high) {
			return high;
		} else {
			return value;
		}
	}

	/* FX */
	public static float lerpF(float start, float end, float value) {
		return (start + (value * (end - start)));
	}

    public static double lerpD(double start, double end, float value) {
        return (start + (value * (end - start)));
    }

	public static float sinerp(float start, float end, float value) {
		return lerpF(start, end, (float) Math.sin(value * Math.PI * 0.5F));
	}

	public static float berp(float start, float end, float value) {
		value = clamp(0, 1, value);
		value = (float) ((Math.sin(value * Math.PI * (0.2f + 2.5f * value * value * value)) * Math.pow(1f - value, 2.2f) + value) * (1f + (1.2f * (1f - value))));
		return start + (end - start) * value;
	}

}