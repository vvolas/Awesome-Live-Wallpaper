package com.wolas.awesomewallpaper;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by wolas on 16.2.9.
 */
public class Animator {
    private static String TAG = "Animator";
    //TODO Move to XML

    private static String bunchieFileName = "Bunchie.gif";
    private float bunchieScale = 0.25f;
    private float bunchieXCoordinate = 30;
    private float bunchieYcoordinate = 70;

    private static String cloudFileName = "Cloud.png";
    private int cloudLimit = 2;
    private static String cloudFileName2 = "Cloud2.png";
    private int cloudLimit2 = 1;
    private float cloudFrontMinSize = 0.15f;
    private float cloudFrontMaxSize = 0.2f;
    private float cloudBackMinSize = 0.1f;
    private float cloudBackMaxSize = 0.15f;
    private float cloudFrontTopLimit = 5;
    private float cloudFrontBotLimit = 40;
    private float cloudBackTopLimit = 5;
    private float cloudBackBotLimit = 30;
    private float cloudFrontScrollingSpeed = 0.06f;
    private float cloudBackScrollingSpeed = 0.03f;

    private static String iceCreamCherryFileName = "IceCreamCherry.png";
    private int iceCreamCherryLimit = 2;
    private static String iceCreamCherryFileName2 = "IceCreamCherry2.png";
    private int iceCreamCherryLimit2 = 2;
    private static String iceCreamPinkFileName = "PinkIceCream.png";
    private int iceCreamPinkLimit = 1;
    private float mountainFrontMinSize = 0.3f;
    private float mountainFrontMaxSize = 0.4f;
    private float mountainBackMinSize = 0.2f;
    private float mountainBackMaxSize = 0.3f;
    private float mountainFrontTopLimit = 50;
    private float mountainFrontBotLimit = 60;
    private float mountainBackTopLimit = 45;
    private float mountainBackBotLimit = 49;
    private float mountainFrontScrollingSpeed = -0.05f;
    private float mountainBackScrollingSpeed = -0.03f;

    private static String nyanCatFileName = "NyanLongSmall.gif";
    private float nyanCatRatio = 0.2f;
    private float nyanCatXcoordinate = 10;
    private float nyanCatYcoordinate = 40;
    private int nyanCatScrollingDelay = 16000;
    private float nyanCatScrollingSpeed = 2f;

    private static String candyStickFileName = "CandyLazda.png";
    private static int candyStickLimit = 3;
    private static String candyFileName = "Candy.png";
    private static int candyLimit = 3;
    private static String blueLolipopFileName = "BlueLolipop.png";
    private static int blueLolipoLimit = 2;
    private static String redLolipopFileName = "RedLolipop.png";
    private static int redLolipoLimit = 2;
    private static String yellowLolipopFileName = "YellowLolipop.png";
    private static int yellowLolipoLimit = 2;
    private float treeFrontMinSize = 0.20f;
    private float treeFrontMaxSize = 0.25f;
    private float treeBackMinSize = 0.1f;
    private float treeBackMaxSize = 0.15f;
    private float forestTreeRotation = 15;
    private float forestFrontTopLimit = bunchieYcoordinate + 2;
    private float forestFrontBotLimit = 95;
    private float forestBackTopLimit = bunchieYcoordinate - 15;
    private float forestBackBotLimit = bunchieYcoordinate - 5;

    private static String grassTileFileName = "GrassTile.png";
    private float grassTopLimit = forestBackTopLimit+5;
    private float grassBotLimit = 100;
    private float groundScrollingSpeedPortrait = -0.25f;
    private float groundScrollingSpeedLandscape = -0.1f;


    private Paint faraAwayPaint;


    private AwesomeSun awesomeSun = null;
    private RandomBitmapScroller forest = null;
    private RandomBitmapScroller clouds = null;
    private RandomBitmapScroller mountains = null;
    private AdjustedGIF bunchie = null;
    private AdjustedGIF nyanCat = null;
    private Background background = null;

    private TiledBitmap grass;


    private long timer=0;
    private boolean isLandscape = false;

    public Animator() {
        this.isLandscape = isLandscape;
        timer = System.currentTimeMillis();
        background = new Background();
        faraAwayPaint = new Paint();
        faraAwayPaint.setColor(Color.CYAN);
        faraAwayPaint.setAlpha(120);
    }

    private void setPortraitMode() {
        HashMap<String, Integer> treeFileNames = new HashMap<String, Integer>();
        treeFileNames.put(candyStickFileName, candyStickLimit);
        treeFileNames.put(candyFileName, candyLimit);
        treeFileNames.put(blueLolipopFileName, blueLolipoLimit);
        treeFileNames.put(redLolipopFileName, redLolipoLimit);
        treeFileNames.put(yellowLolipopFileName, yellowLolipoLimit);
        forest = new RandomBitmapScroller(treeFileNames);
        forest.setFrontScrollSpeed(groundScrollingSpeedPortrait);
        forest.setBackScrollSpeed(groundScrollingSpeedPortrait);
        forest.setFrontDrawingLimits(forestFrontTopLimit, forestFrontBotLimit);
        forest.setBackDrawingLimits(forestBackTopLimit, forestBackBotLimit);
        forest.setFrontImgMinMaxSize(treeFrontMinSize, treeFrontMaxSize);
        forest.setBackImgMinMaxSize(treeBackMinSize, treeBackMaxSize);
        forest.setRandomRotation(forestTreeRotation);
        forest.randomizeImgs();


        HashMap<String, Integer> cloudFileNames = new HashMap<String, Integer>();
        cloudFileNames.put(cloudFileName, cloudLimit);
        cloudFileNames.put(cloudFileName2, cloudLimit2);
        clouds = new RandomBitmapScroller(cloudFileNames);
        clouds.setFrontScrollSpeed(cloudFrontScrollingSpeed);
        clouds.setBackScrollSpeed(cloudBackScrollingSpeed);
        clouds.setFrontDrawingLimits(cloudFrontTopLimit, cloudFrontBotLimit);
        clouds.setBackDrawingLimits(cloudBackTopLimit, cloudBackBotLimit);
        clouds.setFrontImgMinMaxSize(cloudFrontMinSize, cloudFrontMaxSize);
        clouds.setBackImgMinMaxSize(cloudBackMinSize, cloudBackMaxSize);
        clouds.randomizeImgs();

        HashMap<String, Integer> mountainFileNames = new HashMap<String, Integer>();
        mountainFileNames.put(iceCreamCherryFileName, iceCreamCherryLimit);
        mountainFileNames.put(iceCreamCherryFileName2, iceCreamCherryLimit2);
        mountainFileNames.put(iceCreamPinkFileName, iceCreamPinkLimit);
        mountains = new RandomBitmapScroller(mountainFileNames);
        mountains.setFrontScrollSpeed(mountainFrontScrollingSpeed);
        mountains.setBackScrollSpeed(mountainBackScrollingSpeed);
        mountains.setFrontDrawingLimits(mountainFrontTopLimit, mountainFrontBotLimit);
        mountains.setBackDrawingLimits(mountainBackTopLimit, mountainBackBotLimit);
        mountains.setFrontImgMinMaxSize(mountainFrontMinSize, mountainFrontMaxSize);
        mountains.setBackImgMinMaxSize(mountainBackMinSize, mountainBackMaxSize);
        mountains.randomizeImgs();

        grass = new TiledBitmap(grassTileFileName);
        grass.setTilingBoundaries(grassTopLimit, grassBotLimit);
        grass.setScrollingSpeedPercentX(groundScrollingSpeedPortrait);

        bunchie = new AdjustedGIF(bunchieFileName);
        bunchie.setDrawFrameByFrame(true, 60);
        bunchie.setScaleToScreenRatio(bunchieScale);
        bunchie.setCoordinates(bunchieXCoordinate, bunchieYcoordinate, true);

        nyanCat= new AdjustedGIF(nyanCatFileName);
        nyanCat.setDrawFrameByFrame(true, 70);
        nyanCat.setScaleToScreenRatio(nyanCatRatio);
        nyanCat.setCoordinates(nyanCatXcoordinate, nyanCatYcoordinate, true);
        nyanCat.setScrollingDelay(nyanCatScrollingDelay);
        nyanCat.setScrollingSpeedPercentX(nyanCatScrollingSpeed);


        awesomeSun = new AwesomeSun();
        awesomeSun.setCoordinates(70, 20, true);
        awesomeSun.setScaleToScreenRatio(0.25f);
    }

    private void setLandscapeMode() {
        HashMap<String, Integer> treeFileNames = new HashMap<String, Integer>();
        treeFileNames.put(candyStickFileName, candyStickLimit);
        treeFileNames.put(candyFileName, candyLimit);
        treeFileNames.put(blueLolipopFileName, blueLolipoLimit);
        treeFileNames.put(redLolipopFileName, redLolipoLimit);
        treeFileNames.put(yellowLolipopFileName, yellowLolipoLimit);
        forest = new RandomBitmapScroller(treeFileNames);
        forest.setFrontScrollSpeed(groundScrollingSpeedLandscape);
        forest.setBackScrollSpeed(groundScrollingSpeedLandscape);
        forest.setFrontDrawingLimits(forestFrontTopLimit, forestFrontBotLimit);
        forest.setBackDrawingLimits(forestBackTopLimit, forestBackBotLimit);
        forest.setFrontImgMinMaxSize(treeFrontMinSize, treeFrontMaxSize);
        forest.setBackImgMinMaxSize(treeBackMinSize, treeBackMaxSize);
        forest.setRandomRotation(forestTreeRotation);
        forest.randomizeImgs();


        HashMap<String, Integer> cloudFileNames = new HashMap<String, Integer>();
        cloudFileNames.put(cloudFileName, cloudLimit);
        cloudFileNames.put(cloudFileName2, cloudLimit2);
        clouds = new RandomBitmapScroller(cloudFileNames);
        clouds.setFrontScrollSpeed(cloudFrontScrollingSpeed);
        clouds.setBackScrollSpeed(cloudBackScrollingSpeed);
        clouds.setFrontDrawingLimits(cloudFrontTopLimit, cloudFrontBotLimit);
        clouds.setBackDrawingLimits(cloudBackTopLimit, cloudBackBotLimit);
        clouds.setFrontImgMinMaxSize(cloudFrontMinSize, cloudFrontMaxSize);
        clouds.setBackImgMinMaxSize(cloudBackMinSize, cloudBackMaxSize);
        clouds.randomizeImgs();

        HashMap<String, Integer> mountainFileNames = new HashMap<String, Integer>();
        mountainFileNames.put(iceCreamCherryFileName, iceCreamCherryLimit);
        mountainFileNames.put(iceCreamCherryFileName2, iceCreamCherryLimit2);
        mountainFileNames.put(iceCreamPinkFileName, iceCreamPinkLimit);
        mountains = new RandomBitmapScroller(mountainFileNames);
        mountains.setFrontScrollSpeed(mountainFrontScrollingSpeed);
        mountains.setBackScrollSpeed(mountainBackScrollingSpeed);
        mountains.setFrontDrawingLimits(mountainFrontTopLimit, mountainFrontBotLimit);
        mountains.setBackDrawingLimits(mountainBackTopLimit, mountainBackBotLimit);
        mountains.setFrontImgMinMaxSize(mountainFrontMinSize, mountainFrontMaxSize);
        mountains.setBackImgMinMaxSize(mountainBackMinSize, mountainBackMaxSize);
        mountains.randomizeImgs();

        grass = new TiledBitmap(grassTileFileName);
        grass.setTilingBoundaries(grassTopLimit, grassBotLimit);
        grass.setScrollingSpeedPercentX(groundScrollingSpeedLandscape);

        bunchie = new AdjustedGIF(bunchieFileName);
        bunchie.setDrawFrameByFrame(true, 60);
        bunchie.setScaleToScreenRatio(bunchieScale);
        bunchie.setCoordinates(bunchieXCoordinate, bunchieYcoordinate, true);

        nyanCat= new AdjustedGIF(nyanCatFileName);
        nyanCat.setDrawFrameByFrame(true, 70);
        nyanCat.setScaleToScreenRatio(nyanCatRatio);
        nyanCat.setCoordinates(nyanCatXcoordinate, nyanCatYcoordinate, true);
        nyanCat.setScrollingDelay(nyanCatScrollingDelay);
        nyanCat.setScrollingSpeedPercentX(nyanCatScrollingSpeed);


        awesomeSun = new AwesomeSun();
        awesomeSun.setCoordinates(70, 20, true);
        awesomeSun.setScaleToScreenRatio(0.25f);
    }
    public void draw(Canvas canvas) {
//        if (System.currentTimeMillis() - timer > 15000) {
//            background.setTransitionTime(10000);
//            timer = System.currentTimeMillis();
//        }
        background.draw(canvas);
        mountains.drawBack(canvas);
        clouds.drawBack(canvas);
        canvas.drawPaint(faraAwayPaint);

        mountains.drawFront(canvas);

        awesomeSun.draw(canvas);
        clouds.drawFront(canvas);
        nyanCat.draw(canvas);


        grass.drawTiled(canvas);

        forest.drawBack(canvas);
        bunchie.draw(canvas);
        forest.drawFront(canvas);
    }

    public void setScreenMode(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "Orientation set to Landscape ");
            setLandscapeMode();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG, "Orientation set to Portrait ");
            setPortraitMode();
        }
    }
}
