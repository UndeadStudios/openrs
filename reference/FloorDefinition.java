
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class FloorDefinition {

	/**
	 * The single logger for this class.
	 */
	private static final Logger LOGGER = Logger.getLogger(ItemDefinition.class.getName());

	public static FloorDefinition[] overlays;
	public static FloorDefinition[] underlays;

	public int texture;
	public int rgb;
	public boolean occlude;
	public int anotherRgb;

	public int hue;
	public int saturation;
	public int lumiance;

	public int anotherHue;
	public int anotherSaturation;
	public int anotherLuminance;

	public int blendHue;
	public int blendHueMultiplier;
	public int hslToRgb;

	private FloorDefinition() {
		texture = -1;
		occlude = true;
	}

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer buffer = ByteBuffer.wrap(streamLoader.getDataForName("flo.dat"));
		int underlayAmount = buffer.getShort();
		underlays = new FloorDefinition[underlayAmount];

		System.out.println(String.format("Loaded: %d underlays", underlayAmount));

		for (int i = 0; i < underlayAmount; i++) {
			if (underlays[i] == null) {
				underlays[i] = new FloorDefinition();
			}
			underlays[i].readValuesUnderlay(buffer);
			underlays[i].generateHsl(true);
		}
		int overlayAmount = buffer.getShort();

		System.out.println(String.format("Loaded: %d overlays", overlayAmount));

		overlays = new FloorDefinition[overlayAmount];
		for (int i = 0; i < overlayAmount; i++) {
			if (overlays[i] == null) {
				overlays[i] = new FloorDefinition();
			}
			overlays[i].readValuesOverlay(buffer);
			overlays[i].generateHsl(false);
		}
	}

	private void generateHsl(boolean isUnderlay) {
		if (anotherRgb != -1) {
			rgbToHsl(anotherRgb);
			anotherHue = hue;
			anotherSaturation = saturation;
			anotherLuminance = lumiance;
		}
		int color = isUnderlay && Settings.SNOW ? 0xFFFFFF : rgb;
		rgbToHsl(color);
	}

	private void readValuesUnderlay(ByteBuffer buffer) {
		for (; ; ) {
			int opcode = buffer.get();
			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				rgb = ((buffer.get() & 0xff) << 16) + ((buffer.get() & 0xff) << 8) + (buffer.get() & 0xff);
			} else {
				System.out.println("Error unrecognised underlay code: " + opcode);
			}
		}
	}

	private void readValuesOverlay(ByteBuffer buffer) {
		for (; ; ) {
			int opcode = buffer.get();
			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				rgb = ((buffer.get() & 0xff) << 16) + ((buffer.get() & 0xff) << 8) + (buffer.get() & 0xff);
			} else if (opcode == 2) {
				texture = buffer.get() & 0xff;
			} else if (opcode == 5) {
				occlude = false;
			} else if (opcode == 7) {
				anotherRgb = ((buffer.get() & 0xff) << 16) + ((buffer.get() & 0xff) << 8) + (buffer.get() & 0xff);
			} else {
				System.out.println("Error unrecognised overlay code: " + opcode);
			}
		}
	}

	private void rgbToHsl(int rgb) {
		double r = (rgb >> 16 & 0xff) / 256.0;
		double g = (rgb >> 8 & 0xff) / 256.0;
		double b = (rgb & 0xff) / 256.0;
		double min = r;
		if (g < min) {
			min = g;
		}
		if (b < min) {
			min = b;
		}
		double max = r;
		if (g > max) {
			max = g;
		}
		if (b > max) {
			max = b;
		}
		double h = 0.0;
		double s = 0.0;
		double l = (min + max) / 2.0;
		if (min != max) {
			if (l < 0.5) {
				s = (max - min) / (max + min);
			}
			if (l >= 0.5) {
				s = (max - min) / (2.0 - max - min);
			}
			if (r == max) {
				h = (g - b) / (max - min);
			} else if (g == max) {
				h = 2.0 + (b - r) / (max - min);
			} else if (b == max) {
				h = 4.0 + (r - g) / (max - min);
			}
		}
		h /= 6.0;
		hue = (int) (h * 256.0);
		saturation = (int) (s * 256.0);
		lumiance = (int) (l * 256.0);
		if (saturation < 0) {
			saturation = 0;
		} else if (saturation > 255) {
			saturation = 255;
		}
		if (lumiance < 0) {
			lumiance = 0;
		} else if (lumiance > 255) {
			lumiance = 255;
		}
		if (l > 0.5) {
			blendHueMultiplier = (int) ((1.0 - l) * s * 512.0);
		} else {
			blendHueMultiplier = (int) (l * s * 512.0);
		}
		if (blendHueMultiplier < 1) {
			blendHueMultiplier = 1;
		}
		blendHue = (int) (h * blendHueMultiplier);
		hslToRgb = hslToRgb(hue, saturation, lumiance);
	}

	private final static int hslToRgb(int h, int s, int l) {
		if (l > 179) {
			s /= 2;
		}
		if (l > 192) {
			s /= 2;
		}
		if (l > 217) {
			s /= 2;
		}
		if (l > 243) {
			s /= 2;
		}
		return (h / 4 << 10) + (s / 32 << 7) + l / 2;
	}
}