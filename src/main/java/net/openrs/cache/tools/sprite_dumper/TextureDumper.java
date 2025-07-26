/**
* Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.openrs.cache.tools.sprite_dumper;

import net.openrs.cache.Container;
import net.openrs.cache.*;
import net.openrs.cache.sprite.Sprite;
import net.openrs.cache.sprite.Texture;
import net.openrs.cache.type.CacheIndex;
import net.openrs.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

/**
 * @author Kyle Friz
 * @since Dec 30, 2015
 */
public class TextureDumper {

	private static int[] colors;
	public static void main(String[] args) throws IOException {
		File directory = new File(Constants.TEXTURE_PATH);
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		int count = 0;
		try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH_four))) {
			ReferenceTable table = cache.getReferenceTable(28);
			ReferenceTable.Entry entry = table.getEntry(0);
			Archive archive = Archive.decode(cache.read(28, 0).getData(), entry.size());

			int[] ids = new int[entry.capacity()];
			colors = new int[entry.capacity()];
			for (int id = 0; id < entry.capacity(); id++) {
				ReferenceTable.ChildEntry child = entry.getEntry(id);
				if (child == null)
					continue;

				ByteBuffer buffer = archive.getEntry(child.index());
				Texture texture = Texture.decode(buffer);
				ids[id] = texture.getIds(0);
				count++;
			}
			table = cache.getReferenceTable(CacheIndex.SPRITES);
			for (int id = 0; id < entry.capacity(); id++) {
				int file = ids[id];

				ReferenceTable.Entry e = table.getEntry(file);
				if (e == null)
					continue;

				Container containers = cache.read(8, file);
				Sprite sprite = Sprite.decode(containers.getData());
				BufferedImage img = sprite.getFrame(0);
				int width = img.getWidth();
				int height = img.getHeight();
				int[] pixels = new int[width * height];
				PixelGrabber pixelgrabber = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
				try {
					pixelgrabber.grabPixels();
					for (int frame = 0; frame < sprite.size(); frame++) {
						File files = new File(Constants.TEXTURE_PATH, id +  ".png");
						BufferedImage image = ImageUtils.createColoredBackground(ImageUtils.makeColorTransparent(sprite.getFrame(frame), Color.WHITE), new java.awt.Color(0xFF00FF, false));

						ImageIO.write(image, "png", files);
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				colors[id] = averageColorForPixels(pixels);
			}
		} catch (IOException e) {
		}

	}
	private static int averageColorForPixels(int[] pixels) {
		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;
		int totalPixels = pixels.length;

		for (int i = 0; i < totalPixels; i++) {
			if (pixels[i] == 0xff00ff) {
				totalPixels--;
				continue;
			}

			redTotal += pixels[i] >> 16 & 0xff;
			greenTotal += pixels[i] >> 8 & 0xff;
			blueTotal += pixels[i] & 0xff;
		}

		int averageRGB = (redTotal / totalPixels << 16) + (greenTotal / totalPixels << 8) + blueTotal / totalPixels;
		if (averageRGB == 0) {
			averageRGB = 1;
		}

		return averageRGB;
	}

	public static int getColors(int i) {
		return colors[i];
	}
}
