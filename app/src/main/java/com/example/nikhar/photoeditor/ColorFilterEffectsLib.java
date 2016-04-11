package com.example.nikhar.photoeditor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;

import java.io.FileNotFoundException;

public class ColorFilterEffectsLib extends Activity
{

	public Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException
	{
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 100;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(
				getContentResolver().openInputStream(selectedImage), null, o2);

	}


	public static Bitmap mergeImage(Bitmap bmp11, Bitmap bmp12,Bitmap bmp21, Bitmap bmp22)
	{
		int adWDelta = (int)(bmp11.getWidth() - bmp12.getWidth())/2 ;
		int adHDelta = (int)(bmp11.getHeight() - bmp12.getHeight())/2;

		Bitmap mBitmap = Bitmap.createBitmap(bmp11.getWidth(), bmp11.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		canvas.drawBitmap(bmp11, 0, 0, null);
		canvas.drawBitmap(bmp12, adWDelta, adHDelta, null);
		canvas.drawBitmap(bmp21,0,bmp11.getHeight(), null);
		canvas.drawBitmap(bmp22, bmp11.getWidth(),bmp11.getHeight(), null);

		return mBitmap;
	}






	public static Bitmap mergeImage_edgeburn(Bitmap base, Bitmap overlay)
	{
		int adWDelta = (int)(base.getWidth() - overlay.getWidth()) ;
		int adHDelta = (int)(base.getHeight() - overlay.getHeight());

		Bitmap mBitmap = Bitmap.createBitmap(base.getWidth(), base.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(base, overlay.getWidth(), overlay.getHeight(), true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(overlay, overlay.getWidth(), overlay.getHeight(), true);
		canvas.drawBitmap(scaled, 0, 0, null);
	    
	    /*canvas.drawBitmap(scaled, 0,scaled.getHeight(), null);
	    canvas.drawBitmap(scaled, scaled.getWidth(),0, null);*/
		canvas.drawBitmap(overlay1,0,0, null);

		return mBitmap;
	}

	public static Bitmap mergeImage_template(Bitmap bm_tmp,Bitmap bm1, Bitmap bm2,Bitmap bm3, Bitmap bm4)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(bm_tmp.getWidth(), bm_tmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);

		Bitmap scaled = Bitmap.createScaledBitmap(bm1, bm_tmp.getWidth() / 2, 2 * (bm_tmp.getWidth() / 5), true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, bm_tmp.getWidth() / 2, 2 * (bm_tmp.getWidth() / 5), true);
		Bitmap overlay2 = Bitmap.createScaledBitmap(bm3, bm_tmp.getWidth() / 2, 2 * (bm_tmp.getWidth() / 5), true);
		Bitmap overlay3 = Bitmap.createScaledBitmap(bm4, bm_tmp.getWidth() / 2, 2 * (bm_tmp.getWidth() / 5), true);
	    
	    
	    /*Bitmap scaled = Bitmap.createScaledBitmap(bm1, 150,150, true);
	    Bitmap overlay1 = Bitmap.createScaledBitmap(bm2,150,150, true);
	    Bitmap overlay2 = Bitmap.createScaledBitmap(bm3,150,150, true);
	    Bitmap overlay3 = Bitmap.createScaledBitmap(bm4,150,150, true);*/
		canvas.drawBitmap(bm_tmp,0,0, null);
		canvas.drawBitmap(scaled,0,5, null);

		canvas.drawBitmap(overlay1,bm_tmp.getWidth()/2,5, null);
		canvas.drawBitmap(overlay2,0,2*(bm_tmp.getWidth()/5), null);
		canvas.drawBitmap(overlay3,bm_tmp.getWidth()/2,2*(bm_tmp.getWidth()/5), null);

		return mBitmap;
	}

	public static Bitmap mergeImage_template2(Bitmap bm_tmp,Bitmap bm1, Bitmap bm2)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(bm_tmp.getWidth(), bm_tmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(bm1, bm_tmp.getWidth() / 2, 4 * (bm_tmp.getWidth() / 5), true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, bm_tmp.getWidth() / 2, 4 * (bm_tmp.getWidth() / 5), true);
		canvas.drawBitmap(bm_tmp,0,0, null);
		canvas.drawBitmap(scaled,bm_tmp.getWidth()/2,5, null);

		canvas.drawBitmap(overlay1,0,5, null);

		return mBitmap;
	}

	public static Bitmap mergeImage_template1(Bitmap bm_tmp,Bitmap bm1)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(bm_tmp.getWidth(), bm_tmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(bm1, bm_tmp.getWidth() - 40, bm_tmp.getHeight() / 2, true);
		canvas.drawBitmap(bm_tmp,0,0, null);
		canvas.drawBitmap(scaled,30,30, null);

		return mBitmap;
	}

	public static Bitmap mergeImage_template8(Bitmap bm_tmp,Bitmap bm1, Bitmap bm2,Bitmap bm3, Bitmap bm4,Bitmap bm5, Bitmap bm6,Bitmap bm7, Bitmap bm8)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(bm_tmp.getWidth(), bm_tmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
	   /* Bitmap scaled = Bitmap.createScaledBitmap(bm1, 140,140, true);
	    Bitmap overlay1 = Bitmap.createScaledBitmap(bm2,140,140, true);
	    Bitmap overlay2 = Bitmap.createScaledBitmap(bm3,140,140, true);
	    Bitmap overlay3 = Bitmap.createScaledBitmap(bm4,140,140, true);
	    Bitmap overlay4 = Bitmap.createScaledBitmap(bm5,140,140, true);
	    Bitmap overlay5 = Bitmap.createScaledBitmap(bm6,140,140, true);*/

		Bitmap scaled = Bitmap.createScaledBitmap(bm1, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay2 = Bitmap.createScaledBitmap(bm3, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay3 = Bitmap.createScaledBitmap(bm4, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay4 = Bitmap.createScaledBitmap(bm5, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay5 = Bitmap.createScaledBitmap(bm6, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);

		Bitmap overlay6 = Bitmap.createScaledBitmap(bm7, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
		Bitmap overlay7 = Bitmap.createScaledBitmap(bm8, bm_tmp.getWidth() / 3, 1 * (bm_tmp.getWidth() / 3), true);
	    
	   /* canvas.drawBitmap(bm_tmp,0,0, null);
	    canvas.drawBitmap(scaled,bm_tmp.getWidth()/6,30, null);
	    
	    canvas.drawBitmap(overlay1, bm_tmp.getWidth()/6+200,35, null);
	    canvas.drawBitmap(overlay2, bm_tmp.getWidth()/6,35+160, null);
	    canvas.drawBitmap(overlay3,bm_tmp.getWidth()/6+200,35+160, null);
	    canvas.drawBitmap(overlay4, bm_tmp.getWidth()/6,35+320, null);
	    canvas.drawBitmap(overlay5, bm_tmp.getWidth()/6+200,35+320, null);*/

		canvas.drawBitmap(bm_tmp,0,0, null);
		canvas.drawBitmap(scaled,15,20, null);
		canvas.drawBitmap(overlay1,bm_tmp.getWidth()-15-(bm_tmp.getWidth()/3),20, null);
		canvas.drawBitmap(overlay2,15,20+(bm_tmp.getWidth()/3), null);
		canvas.drawBitmap(overlay3,bm_tmp.getWidth()-15-(bm_tmp.getWidth()/3),20+(bm_tmp.getWidth()/3), null);
		canvas.drawBitmap(overlay4,15,2*(bm_tmp.getWidth()/3)+20, null);
		canvas.drawBitmap(overlay5,bm_tmp.getWidth()-15-(bm_tmp.getWidth()/3),2*(bm_tmp.getWidth()/3)+20, null);

		canvas.drawBitmap(overlay6,15,3*(bm_tmp.getWidth()/3)+20, null);
		canvas.drawBitmap(overlay7,bm_tmp.getWidth()-15-(bm_tmp.getWidth()/3),3*(bm_tmp.getWidth()/3)+20, null);

		return mBitmap;
	}

	public static Bitmap mergeImage_template6(Bitmap bm_tmp,Bitmap bm1, Bitmap bm2,Bitmap bm3, Bitmap bm4,Bitmap bm5, Bitmap bm6)
	{

		Bitmap mBitmap = Bitmap.createBitmap(bm_tmp.getWidth(), bm_tmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);

		Bitmap scaled   = Bitmap.createScaledBitmap(bm1, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);
		Bitmap overlay2 = Bitmap.createScaledBitmap(bm3, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);
		Bitmap overlay3 = Bitmap.createScaledBitmap(bm4, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);
		Bitmap overlay4 = Bitmap.createScaledBitmap(bm5, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);
		Bitmap overlay5 = Bitmap.createScaledBitmap(bm6, 20 + (bm_tmp.getWidth() / 3), 20 + (bm_tmp.getWidth() / 3), true);


		canvas.drawBitmap(bm_tmp,0,0, null);
		canvas.drawBitmap(scaled,15,20, null);
		canvas.drawBitmap(overlay1,bm_tmp.getWidth()-15-(20+(bm_tmp.getWidth()/3)),20, null);

		canvas.drawBitmap(overlay2,15,20+(bm_tmp.getWidth()/3), null);
		canvas.drawBitmap(overlay3,bm_tmp.getWidth()-15-(20+(bm_tmp.getWidth()/3)),20+(bm_tmp.getWidth()/3), null);

		canvas.drawBitmap(overlay4,15,00+(2*(20+(bm_tmp.getWidth()/3))), null);
		canvas.drawBitmap(overlay5,bm_tmp.getWidth()-15-(20+(bm_tmp.getWidth()/3)),00+(2*(20+(bm_tmp.getWidth()/3))), null);


		return mBitmap;
	}

	public static Bitmap mergeImage1(Bitmap bm1, Bitmap bm2,Bitmap bm3, Bitmap bm4)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(bm1, 100, 100, true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, 100, 100, true);
		Bitmap overlay2 = Bitmap.createScaledBitmap(bm3, 100, 100, true);
		Bitmap overlay3 = Bitmap.createScaledBitmap(bm4, 100, 100, true);
		canvas.drawBitmap(scaled, 0, 0, null);

		canvas.drawBitmap(overlay1, 0,scaled.getHeight(), null);
		canvas.drawBitmap(overlay2, scaled.getWidth(),0, null);
		canvas.drawBitmap(overlay3,scaled.getWidth(), scaled.getHeight(), null);

		return mBitmap;
	}

	public static Bitmap mergeImage6(Bitmap size,Bitmap bm1, Bitmap bm2,Bitmap bm3, Bitmap bm4,Bitmap bm5, Bitmap bm6)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/

		Bitmap mBitmap = Bitmap.createBitmap(300, 400, Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(bm1, 150, 133, true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, 150, 133, true);
		Bitmap overlay2 = Bitmap.createScaledBitmap(bm3, 150, 133, true);
		Bitmap overlay3 = Bitmap.createScaledBitmap(bm4, 150, 133, true);
		Bitmap overlay4 = Bitmap.createScaledBitmap(bm5, 150, 133, true);
		Bitmap overlay5 = Bitmap.createScaledBitmap(bm6, 150, 133, true);
		canvas.drawBitmap(scaled, 0,0, null);
		canvas.drawBitmap(overlay1,scaled.getWidth(),0, null);
		canvas.drawBitmap(overlay2,0,scaled.getHeight(), null);
		canvas.drawBitmap(overlay3,scaled.getWidth(),scaled.getHeight(), null);
		canvas.drawBitmap(overlay4,0,scaled.getHeight()+overlay2.getHeight(), null);
		canvas.drawBitmap(overlay5,scaled.getWidth(),scaled.getHeight()+overlay2.getHeight(), null);

		return mBitmap;
	}


	public static Bitmap mergeImage2(Bitmap bm1, Bitmap bm2)
	{
/*	    int adWDelta = (int)(bm1.getWidth() - overlay.getWidth()) ;
	    int adHDelta = (int)(bm1.getHeight() - overlay.getHeight());
*/
		Bitmap mBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		Bitmap scaled = Bitmap.createScaledBitmap(bm1, bm2.getWidth() / 2, bm2.getHeight() / 2, true);
		Bitmap overlay1 = Bitmap.createScaledBitmap(bm2, bm2.getWidth() / 2, bm2.getHeight() / 2, true);
		canvas.drawBitmap(scaled, 0, 0, null);

		canvas.drawBitmap(overlay1, 0,scaled.getHeight(), null);

		return mBitmap;
	}

	public static Bitmap invert(Bitmap src) {
		Bitmap output = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
		int A, R, G, B;
		int pixelColor;
		int height = src.getHeight();
		int width = src.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixelColor = src.getPixel(x, y);
				A = Color.alpha(pixelColor);

				R = Color.red(pixelColor)+52;
				G = Color.green(pixelColor)+40;
				B = Color.blue(pixelColor)+90;

				output.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		return output;
	}

	public static Bitmap doBrightness(Bitmap src, int value) {
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan through all pixels
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);

				// increase/decrease each channel
				R += value;
				if(R > 255) { R = 255; }
				else if(R < 0) { R = 0; }

				G += value;
				if(G > 255) { G = 255; }
				else if(G < 0) { G = 0; }

				B += value;
				if(B > 255) { B = 255; }
				else if(B < 0) { B = 0; }

				// apply new pixel color to output bitmap

				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap adjustedContrast(Bitmap src, double value)
	{
		// image size
		int width = src.getWidth();
		int height = src.getHeight();
		// create output bitmap

		// create a mutable empty bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

		// create a canvas so that we can draw the bmOut Bitmap from source bitmap
		Canvas c = new Canvas();
		c.setBitmap(bmOut);

		// draw bitmap to bmOut from src bitmap so we can modify it
		c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


		// color information
		int A, R, G, B;
		int pixel;
		// get contrast value
		double contrast = Math.pow((100 + value) / 100, 2);

		// scan through all pixels
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				// get pixel color
				pixel = src.getPixel(x, y);
				A = Color.alpha(pixel);
				// apply filter contrast for every channel R, G, B
				R = Color.red(pixel);
				R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if(R < 0) { R = 0; }
				else if(R > 255) { R = 255; }

				G = Color.green(pixel);
				G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if(G < 0) { G = 0; }
				else if(G > 255) { G = 255; }

				B = Color.blue(pixel);
				B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
				if(B < 0) { B = 0; }
				else if(B > 255) { B = 255; }

				// set new pixel color to output bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));


			}
		}
		return bmOut;
	}


	public static Bitmap ConvertToNegative(Bitmap sampleBitmap){
		
	}


	public static Bitmap ConvertToBlackAndWhite(Bitmap sampleBitmap){
		ColorMatrix bwMatrix =new ColorMatrix();
		bwMatrix.setSaturation(0);
		final ColorMatrixColorFilter colorFilter= new ColorMatrixColorFilter(bwMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Config.ARGB_8888, true);
		Paint paint=new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas =new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}


	public static Bitmap ConvertToSepia(Bitmap sampleBitmap){

	}


}




