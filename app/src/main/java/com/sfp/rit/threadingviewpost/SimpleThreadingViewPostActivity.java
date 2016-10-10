package com.sfp.rit.threadingviewpost;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SimpleThreadingViewPostActivity extends Activity {

	private Bitmap mBitmap;
	private Bitmap mBitmap2;
	private ImageView mImageView;
	private ImageView mImageView2;

	//slow down the loading of the image (for demonstration purpose)
	private int mDelay = 0;
	private int mDelay2 =0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mImageView = (ImageView) findViewById(R.id.imageView);
		mImageView2 = (ImageView) findViewById(R.id.imageView2);

		final Button button = (Button) findViewById(R.id.loadButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadIcon();
			}
		});

		final Button otherButton = (Button) findViewById(R.id.otherButton);
		otherButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SimpleThreadingViewPostActivity.this, "I'm Working",
						Toast.LENGTH_SHORT).show();
			}
		});
	}


	// loadIcon is run when the 'Load Icon' button is clicked
	private void loadIcon() {
		  mDelay = (int)(Math.random()*1000)*13;
		  mDelay2 = (int)(Math.random()*1000)*13;
		mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
		mImageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
		// Create a Thread:
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Log.i("Thread 1  Sleep:", "Time:" + mDelay);
					// First, create a delay of 10000 ms
					//  (mDelay was declared above in the Activity class)
					Thread.sleep(mDelay);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Set the bitmap to the 'painter' image
				mBitmap = BitmapFactory.decodeResource(getResources(),
						R.mipmap.tux);

				// Now set the ImageView mBitmap
				// The post method causes the Runnable to be added to the message queue
				// The runnable will be run on the user interface thread
				mImageView.post(new Runnable() {
					@Override
					public void run() {
						mImageView.setImageBitmap(mBitmap);
					}
				});
			}
		}).start();

		new Thread(new Runnable(){
			@Override
			public void run(){
				Log.i("Thread 2 Sleep:", "Time:" + mDelay2);
				try{
					Thread.sleep(mDelay2);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				mBitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.tuppence);

				mImageView2.post(new Runnable(){
					@Override
					public void run(){
						mImageView2.setImageBitmap(mBitmap2);
					}
				});
			}
		}).start();
	}
}
