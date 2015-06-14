package com.example.dell.grupo1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.*;


public class PictureActivity extends ActionBarActivity {

    static Bitmap acumulator;
    static ImageView Pic;
    private static final String TAG = "MyActivity";
    static {
        if (!OpenCVLoader.initDebug())
            Log.d("ERROR", "Unable to load OpenCV");
        else
            Log.d("SUCCESS", "OpenCV loaded");
    }
    public void Snap(View v) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("ScannerApp");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Bundle extras = data.getExtras();
            Bitmap foto = (Bitmap) extras.get("data");
            acumulator = foto;
            Pic.setImageBitmap(acumulator);
        }
    }

    public void toMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        Pic = (ImageView)findViewById(R.id.Picture);
        Snap(new View(this));
        Mat teste = new Mat();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

}
