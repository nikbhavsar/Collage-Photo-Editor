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
	
	
	public static Bitmap doBrightness(Bitmap src, int value) {
	  return null;
	}
	
	public static Bitmap adjustedContrast(Bitmap src, double value)
	{
	    return null;
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



	
}





