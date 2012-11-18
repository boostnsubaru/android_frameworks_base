/*
<<<<<<< HEAD
 * Copyright (C) 2009 The Android Open Source Project
=======
 * Copyright (C) 2012 The Android Open Source Project
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.rs.image;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.ScriptC;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.Script;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.SeekBar;
=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
import android.widget.TextView;
import android.view.View;
import android.util.Log;
import java.lang.Math;

<<<<<<< HEAD
public class ImageProcessingActivity extends Activity
                                       implements SurfaceHolder.Callback,
                                       SeekBar.OnSeekBarChangeListener {
    private final String TAG = "Img";
    private Bitmap mBitmapIn;
    private Bitmap mBitmapOut;
    private ScriptC_threshold mScript;
    private ScriptC_vertical_blur mScriptVBlur;
    private ScriptC_horizontal_blur mScriptHBlur;
    private int mRadius = 0;
    private SeekBar mRadiusSeekBar;

    private float mInBlack = 0.0f;
    private SeekBar mInBlackSeekBar;
    private float mOutBlack = 0.0f;
    private SeekBar mOutBlackSeekBar;
    private float mInWhite = 255.0f;
    private SeekBar mInWhiteSeekBar;
    private float mOutWhite = 255.0f;
    private SeekBar mOutWhiteSeekBar;
    private float mGamma = 1.0f;
    private SeekBar mGammaSeekBar;

    private float mSaturation = 1.0f;
    private SeekBar mSaturationSeekBar;

    private TextView mBenchmarkResult;

    @SuppressWarnings({"FieldCanBeLocal"})
    private RenderScript mRS;
    @SuppressWarnings({"FieldCanBeLocal"})
    private Type mPixelType;
    @SuppressWarnings({"FieldCanBeLocal"})
    private Allocation mInPixelsAllocation;
    @SuppressWarnings({"FieldCanBeLocal"})
    private Allocation mOutPixelsAllocation;
    @SuppressWarnings({"FieldCanBeLocal"})
    private Allocation mScratchPixelsAllocation1;
    private Allocation mScratchPixelsAllocation2;
=======
import android.os.Environment;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageProcessingActivity extends Activity
                                       implements SeekBar.OnSeekBarChangeListener {
    private final String TAG = "Img";
    private final String RESULT_FILE = "image_processing_result.csv";

    Bitmap mBitmapIn;
    Bitmap mBitmapIn2;
    Bitmap mBitmapOut;
    String mTestNames[];

    private Spinner mSpinner;
    private SeekBar mBar1;
    private SeekBar mBar2;
    private SeekBar mBar3;
    private SeekBar mBar4;
    private SeekBar mBar5;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private TextView mText5;

    private float mSaturation = 1.0f;

    private TextView mBenchmarkResult;
    private Spinner mTestSpinner;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

    private SurfaceView mSurfaceView;
    private ImageView mDisplayView;

<<<<<<< HEAD
    private boolean mIsProcessing;

    class FilterCallback extends RenderScript.RSMessageHandler {
        private Runnable mAction = new Runnable() {
            public void run() {

                synchronized (mDisplayView) {
                    mIsProcessing = false;
                }

                mOutPixelsAllocation.copyTo(mBitmapOut);
                mDisplayView.invalidate();
            }
        };

        @Override
        public void run() {
            mSurfaceView.removeCallbacks(mAction);
            mSurfaceView.post(mAction);
        }
    }

    int in[];
    int interm[];
    int out[];
    int MAX_RADIUS = 25;
    // Store our coefficients here
    float gaussian[];

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {

            if (seekBar == mRadiusSeekBar) {
                float fRadius = progress / 100.0f;
                fRadius *= (float)(MAX_RADIUS);
                mRadius = (int)fRadius;

                mScript.set_radius(mRadius);
            } else if (seekBar == mInBlackSeekBar) {
                mInBlack = (float)progress;
                mScriptVBlur.invoke_setLevels(mInBlack, mOutBlack, mInWhite, mOutWhite);
            } else if (seekBar == mOutBlackSeekBar) {
                mOutBlack = (float)progress;
                mScriptVBlur.invoke_setLevels(mInBlack, mOutBlack, mInWhite, mOutWhite);
            } else if (seekBar == mInWhiteSeekBar) {
                mInWhite = (float)progress + 127.0f;
                mScriptVBlur.invoke_setLevels(mInBlack, mOutBlack, mInWhite, mOutWhite);
            } else if (seekBar == mOutWhiteSeekBar) {
                mOutWhite = (float)progress + 127.0f;
                mScriptVBlur.invoke_setLevels(mInBlack, mOutBlack, mInWhite, mOutWhite);
            } else if (seekBar == mGammaSeekBar) {
                mGamma = (float)progress/100.0f;
                mGamma = Math.max(mGamma, 0.1f);
                mGamma = 1.0f / mGamma;
                mScriptVBlur.invoke_setGamma(mGamma);
            } else if (seekBar == mSaturationSeekBar) {
                mSaturation = (float)progress / 50.0f;
                mScriptVBlur.invoke_setSaturation(mSaturation);
            }

            synchronized (mDisplayView) {
                if (mIsProcessing) {
                    return;
                }
                mIsProcessing = true;
            }

            mScript.invoke_filter();
=======
    private boolean mDoingBenchmark;

    private TestBase mTest;

    public void updateDisplay() {
            mTest.updateBitmap(mBitmapOut);
            mDisplayView.invalidate();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {

            if (seekBar == mBar1) {
                mTest.onBar1Changed(progress);
            } else if (seekBar == mBar2) {
                mTest.onBar2Changed(progress);
            } else if (seekBar == mBar3) {
                mTest.onBar3Changed(progress);
            } else if (seekBar == mBar4) {
                mTest.onBar4Changed(progress);
            } else if (seekBar == mBar5) {
                mTest.onBar5Changed(progress);
            }

            mTest.runTest();
            updateDisplay();
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

<<<<<<< HEAD
=======
    void setupBars() {
        mSpinner.setVisibility(View.VISIBLE);
        mTest.onSpinner1Setup(mSpinner);

        mBar1.setVisibility(View.VISIBLE);
        mText1.setVisibility(View.VISIBLE);
        mTest.onBar1Setup(mBar1, mText1);

        mBar2.setVisibility(View.VISIBLE);
        mText2.setVisibility(View.VISIBLE);
        mTest.onBar2Setup(mBar2, mText2);

        mBar3.setVisibility(View.VISIBLE);
        mText3.setVisibility(View.VISIBLE);
        mTest.onBar3Setup(mBar3, mText3);

        mBar4.setVisibility(View.VISIBLE);
        mText4.setVisibility(View.VISIBLE);
        mTest.onBar4Setup(mBar4, mText4);

        mBar5.setVisibility(View.VISIBLE);
        mText5.setVisibility(View.VISIBLE);
        mTest.onBar5Setup(mBar5, mText5);
    }


    void changeTest(int testID) {
        if (mTest != null) {
            mTest.destroy();
        }
        switch(testID) {
        case 0:
            mTest = new LevelsV4(false, false);
            break;
        case 1:
            mTest = new LevelsV4(false, true);
            break;
        case 2:
            mTest = new LevelsV4(true, false);
            break;
        case 3:
            mTest = new LevelsV4(true, true);
            break;
        case 4:
            mTest = new Blur25(false);
            break;
        case 5:
            mTest = new Blur25(true);
            break;
        case 6:
            mTest = new Greyscale();
            break;
        case 7:
            mTest = new Grain();
            break;
        case 8:
            mTest = new Fisheye(false, false);
            break;
        case 9:
            mTest = new Fisheye(false, true);
            break;
        case 10:
            mTest = new Fisheye(true, false);
            break;
        case 11:
            mTest = new Fisheye(true, true);
            break;
        case 12:
            mTest = new Vignette(false, false);
            break;
        case 13:
            mTest = new Vignette(false, true);
            break;
        case 14:
            mTest = new Vignette(true, false);
            break;
        case 15:
            mTest = new Vignette(true, true);
            break;
        case 16:
            mTest = new GroupTest(false);
            break;
        case 17:
            mTest = new GroupTest(true);
            break;
        case 18:
            mTest = new Convolve3x3(false);
            break;
        case 19:
            mTest = new Convolve3x3(true);
            break;
        case 20:
            mTest = new ColorMatrix(false, false);
            break;
        case 21:
            mTest = new ColorMatrix(true, false);
            break;
        case 22:
            mTest = new ColorMatrix(true, true);
            break;
        case 23:
            mTest = new Copy();
            break;
        case 24:
            mTest = new CrossProcess();
            break;
        case 25:
            mTest = new Convolve5x5(false);
            break;
        case 26:
            mTest = new Convolve5x5(true);
            break;
        case 27:
            mTest = new Mandelbrot();
            break;
        case 28:
            mTest = new Blend();
            break;
        }

        mTest.createBaseTest(this, mBitmapIn, mBitmapIn2);
        setupBars();

        mTest.runTest();
        updateDisplay();
        mBenchmarkResult.setText("Result: not run");
    }

    void setupTests() {
        mTestNames = new String[29];
        mTestNames[0] = "Levels Vec3 Relaxed";
        mTestNames[1] = "Levels Vec4 Relaxed";
        mTestNames[2] = "Levels Vec3 Full";
        mTestNames[3] = "Levels Vec4 Full";
        mTestNames[4] = "Blur radius 25";
        mTestNames[5] = "Intrinsic Blur radius 25";
        mTestNames[6] = "Greyscale";
        mTestNames[7] = "Grain";
        mTestNames[8] = "Fisheye Full";
        mTestNames[9] = "Fisheye Relaxed";
        mTestNames[10] = "Fisheye Approximate Full";
        mTestNames[11] = "Fisheye Approximate Relaxed";
        mTestNames[12] = "Vignette Full";
        mTestNames[13] = "Vignette Relaxed";
        mTestNames[14] = "Vignette Approximate Full";
        mTestNames[15] = "Vignette Approximate Relaxed";
        mTestNames[16] = "Group Test (emulated)";
        mTestNames[17] = "Group Test (native)";
        mTestNames[18] = "Convolve 3x3";
        mTestNames[19] = "Intrinsics Convolve 3x3";
        mTestNames[20] = "ColorMatrix";
        mTestNames[21] = "Intrinsics ColorMatrix";
        mTestNames[22] = "Intrinsics ColorMatrix Grey";
        mTestNames[23] = "Copy";
        mTestNames[24] = "CrossProcess (using LUT)";
        mTestNames[25] = "Convolve 5x5";
        mTestNames[26] = "Intrinsics Convolve 5x5";
        mTestNames[27] = "Mandelbrot";
        mTestNames[28] = "Intrinsics Blend";

        mTestSpinner.setAdapter(new ArrayAdapter<String>(
            this, R.layout.spinner_layout, mTestNames));
    }

    private AdapterView.OnItemSelectedListener mTestSpinnerListener =
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    changeTest(pos);
                }

                public void onNothingSelected(AdapterView parent) {

                }
            };

>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

<<<<<<< HEAD
        mBitmapIn = loadBitmap(R.drawable.city);
        mBitmapOut = loadBitmap(R.drawable.city);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(this);
=======
        mBitmapIn = loadBitmap(R.drawable.img1600x1067);
        mBitmapIn2 = loadBitmap(R.drawable.img1600x1067b);
        mBitmapOut = loadBitmap(R.drawable.img1600x1067);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a

        mDisplayView = (ImageView) findViewById(R.id.display);
        mDisplayView.setImageBitmap(mBitmapOut);

<<<<<<< HEAD
        mRadiusSeekBar = (SeekBar) findViewById(R.id.radius);
        mRadiusSeekBar.setOnSeekBarChangeListener(this);

        mInBlackSeekBar = (SeekBar)findViewById(R.id.inBlack);
        mInBlackSeekBar.setOnSeekBarChangeListener(this);
        mInBlackSeekBar.setMax(128);
        mInBlackSeekBar.setProgress(0);
        mOutBlackSeekBar = (SeekBar)findViewById(R.id.outBlack);
        mOutBlackSeekBar.setOnSeekBarChangeListener(this);
        mOutBlackSeekBar.setMax(128);
        mOutBlackSeekBar.setProgress(0);

        mInWhiteSeekBar = (SeekBar)findViewById(R.id.inWhite);
        mInWhiteSeekBar.setOnSeekBarChangeListener(this);
        mInWhiteSeekBar.setMax(128);
        mInWhiteSeekBar.setProgress(128);
        mOutWhiteSeekBar = (SeekBar)findViewById(R.id.outWhite);
        mOutWhiteSeekBar.setOnSeekBarChangeListener(this);
        mOutWhiteSeekBar.setMax(128);
        mOutWhiteSeekBar.setProgress(128);

        mGammaSeekBar = (SeekBar)findViewById(R.id.inGamma);
        mGammaSeekBar.setOnSeekBarChangeListener(this);
        mGammaSeekBar.setMax(150);
        mGammaSeekBar.setProgress(100);

        mSaturationSeekBar = (SeekBar)findViewById(R.id.inSaturation);
        mSaturationSeekBar.setOnSeekBarChangeListener(this);
        mSaturationSeekBar.setProgress(50);

        mBenchmarkResult = (TextView) findViewById(R.id.benchmarkText);
        mBenchmarkResult.setText("Result: not run");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        createScript();
        mScript.invoke_filter();
        mOutPixelsAllocation.copyTo(mBitmapOut);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void createScript() {
        mRS = RenderScript.create(this);
        mRS.setMessageHandler(new FilterCallback());

        mInPixelsAllocation = Allocation.createFromBitmap(mRS, mBitmapIn,
                                                          Allocation.MipmapControl.MIPMAP_NONE,
                                                          Allocation.USAGE_SCRIPT);
        mOutPixelsAllocation = Allocation.createFromBitmap(mRS, mBitmapOut,
                                                           Allocation.MipmapControl.MIPMAP_NONE,
                                                           Allocation.USAGE_SCRIPT);

        Type.Builder tb = new Type.Builder(mRS, Element.F32_4(mRS));
        tb.setX(mBitmapIn.getWidth());
        tb.setY(mBitmapIn.getHeight());
        mScratchPixelsAllocation1 = Allocation.createTyped(mRS, tb.create());
        mScratchPixelsAllocation2 = Allocation.createTyped(mRS, tb.create());

        mScriptVBlur = new ScriptC_vertical_blur(mRS, getResources(), R.raw.vertical_blur);
        mScriptHBlur = new ScriptC_horizontal_blur(mRS, getResources(), R.raw.horizontal_blur);

        mScript = new ScriptC_threshold(mRS, getResources(), R.raw.threshold);
        mScript.set_width(mBitmapIn.getWidth());
        mScript.set_height(mBitmapIn.getHeight());
        mScript.set_radius(mRadius);

        mScriptVBlur.invoke_setLevels(mInBlack, mOutBlack, mInWhite, mOutWhite);
        mScriptVBlur.invoke_setGamma(mGamma);
        mScriptVBlur.invoke_setSaturation(mSaturation);

        mScript.bind_InPixel(mInPixelsAllocation);
        mScript.bind_OutPixel(mOutPixelsAllocation);
        mScript.bind_ScratchPixel1(mScratchPixelsAllocation1);
        mScript.bind_ScratchPixel2(mScratchPixelsAllocation2);

        mScript.set_vBlurScript(mScriptVBlur);
        mScript.set_hBlurScript(mScriptHBlur);
    }

=======
        mSpinner = (Spinner) findViewById(R.id.spinner1);

        mBar1 = (SeekBar) findViewById(R.id.slider1);
        mBar2 = (SeekBar) findViewById(R.id.slider2);
        mBar3 = (SeekBar) findViewById(R.id.slider3);
        mBar4 = (SeekBar) findViewById(R.id.slider4);
        mBar5 = (SeekBar) findViewById(R.id.slider5);

        mBar1.setOnSeekBarChangeListener(this);
        mBar2.setOnSeekBarChangeListener(this);
        mBar3.setOnSeekBarChangeListener(this);
        mBar4.setOnSeekBarChangeListener(this);
        mBar5.setOnSeekBarChangeListener(this);

        mText1 = (TextView) findViewById(R.id.slider1Text);
        mText2 = (TextView) findViewById(R.id.slider2Text);
        mText3 = (TextView) findViewById(R.id.slider3Text);
        mText4 = (TextView) findViewById(R.id.slider4Text);
        mText5 = (TextView) findViewById(R.id.slider5Text);

        mTestSpinner = (Spinner) findViewById(R.id.filterselection);
        mTestSpinner.setOnItemSelectedListener(mTestSpinnerListener);

        mBenchmarkResult = (TextView) findViewById(R.id.benchmarkText);
        mBenchmarkResult.setText("Result: not run");

        setupTests();
        changeTest(0);
    }


>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    private Bitmap loadBitmap(int resource) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return copyBitmap(BitmapFactory.decodeResource(getResources(), resource, options));
    }

    private static Bitmap copyBitmap(Bitmap source) {
        Bitmap b = Bitmap.createBitmap(source.getWidth(), source.getHeight(), source.getConfig());
        Canvas c = new Canvas(b);
        c.drawBitmap(source, 0, 0, null);
        source.recycle();
        return b;
    }

    // button hook
    public void benchmark(View v) {
<<<<<<< HEAD
        long t = getBenchmark();
        //long javaTime = javaFilter();
        //mBenchmarkResult.setText("RS: " + t + " ms  Java: " + javaTime + " ms");
        mBenchmarkResult.setText("Result: " + t + " ms");
    }

    // For benchmark test
    public long getBenchmark() {
        Log.v(TAG, "Benchmarking");
        int oldRadius = mRadius;
        mRadius = MAX_RADIUS;
        mScript.set_radius(mRadius);

        mScript.invoke_filter();
        mRS.finish();

        long t = java.lang.System.currentTimeMillis();

        mScript.invoke_filter();
        mOutPixelsAllocation.copyTo(mBitmapOut);

        t = java.lang.System.currentTimeMillis() - t;
        Log.v(TAG, "getBenchmark: Renderscript frame time core ms " + t);
        mRadius = oldRadius;
        mScript.set_radius(mRadius);

        mScript.invoke_filter();
        mOutPixelsAllocation.copyTo(mBitmapOut);
        return t;
=======
        float t = getBenchmark();
        //long javaTime = javaFilter();
        //mBenchmarkResult.setText("RS: " + t + " ms  Java: " + javaTime + " ms");
        mBenchmarkResult.setText("Result: " + t + " ms");
        Log.v(TAG, "getBenchmark: Renderscript frame time core ms " + t);
    }

    public void benchmark_all(View v) {
        // write result into a file
        File externalStorage = Environment.getExternalStorageDirectory();
        if (!externalStorage.canWrite()) {
            Log.v(TAG, "sdcard is not writable");
            return;
        }
        File resultFile = new File(externalStorage, RESULT_FILE);
        resultFile.setWritable(true, false);
        try {
            BufferedWriter rsWriter = new BufferedWriter(new FileWriter(resultFile));
            Log.v(TAG, "Saved results in: " + resultFile.getAbsolutePath());
            for (int i = 0; i < mTestNames.length; i++ ) {
                changeTest(i);
                float t = getBenchmark();
                String s = new String("" + mTestNames[i] + ", " + t);
                rsWriter.write(s + "\n");
                Log.v(TAG, "Test " + s + "ms\n");
            }
            rsWriter.close();
        } catch (IOException e) {
            Log.v(TAG, "Unable to write result file " + e.getMessage());
        }
        changeTest(0);
    }

    // For benchmark test
    public float getBenchmark() {
        mDoingBenchmark = true;

        mTest.setupBenchmark();
        long result = 0;

        //Log.v(TAG, "Warming");
        long t = java.lang.System.currentTimeMillis() + 250;
        do {
            mTest.runTest();
            mTest.finish();
        } while (t > java.lang.System.currentTimeMillis());


        //Log.v(TAG, "Benchmarking");
        int ct = 0;
        t = java.lang.System.currentTimeMillis();
        do {
            mTest.runTest();
            mTest.finish();
            ct++;
        } while ((t+1000) > java.lang.System.currentTimeMillis());
        t = java.lang.System.currentTimeMillis() - t;
        float ft = (float)t;
        ft /= ct;

        mTest.exitBenchmark();
        mDoingBenchmark = false;

        return ft;
>>>>>>> 6457d361a7e38464d2679a053e8b417123e00c6a
    }
}
