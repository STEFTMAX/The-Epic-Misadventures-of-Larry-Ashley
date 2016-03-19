package com.steftmax.temol.tool;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * @author pieter3457
 *
 */
public class PixelArtScaler {

	Pixmap original;

	public PixelArtScaler(FileHandle file) {
		original = new Pixmap(file);

	}

	public Pixmap scaleX2() {
		return scaleX2(original);
	}
	
	public Pixmap scaleX2(float iterations) {

		Pixmap original = this.original;
		Pixmap scaled = null;

		for (int i = 0; i < iterations; i++) {
			scaled = scaleX2(original);
			
			if (original != this.original)
				original.dispose();
			original = scaled;

		}

		return scaled;
	}

	public Pixmap scaleX2(Pixmap original) {
		Pixmap originalX2 = new Pixmap(original.getWidth() * 2, original.getHeight() * 2, original.getFormat());

		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {

				// Note: commmented pixels are not used.
				//
				// format:
				// from:
				// a b c
				// d e f
				// g h i
				// to:
				// e0 e1
				// e2 e3
				//
				// with y pointing down

				// int a = original.getPixel(x - 1, y - 1);
				int b = original.getPixel(x, y - 1);
				// int c = original.getPixel(x + 1, y - 1);

				int d = original.getPixel(x - 1, y);
				int e = original.getPixel(x, y);
				int f = original.getPixel(x + 1, y);

				// int g = original.getPixel(x - 1, y + 1);
				int h = original.getPixel(x, y + 1);
				// int i = original.getPixel(x + 1, y + 1);

				int e0;
				int e1;
				int e2;
				int e3;

				if (b != h && d != f) {
					e0 = d == b ? d : e;
					e1 = b == f ? f : e;
					e2 = d == h ? d : e;
					e3 = h == f ? f : e;
				} else {
					e0 = e;
					e1 = e;
					e2 = e;
					e3 = e;
				}

				originalX2.drawPixel(x * 2, y * 2, e0);
				originalX2.drawPixel(x * 2 + 1, y * 2, e1);
				originalX2.drawPixel(x * 2, y * 2 + 1, e2);
				originalX2.drawPixel(x * 2 + 1, y * 2 + 1, e3);
			}
		}

		return originalX2;
	}

}
