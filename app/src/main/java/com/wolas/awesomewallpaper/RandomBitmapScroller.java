package com.wolas.awesomewallpaper;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by wolas on 16.2.23.
 */
public class RandomBitmapScroller {
    private static String TAG = "RandomBitmapScroller";

    private HashMap<String, Integer> fileNames;

    ///private List<AdjustedBitmap> imgs;
    List<AdjustedBitmap> imgs;

    private float frontImgMinSize = 0.2f;
    private float frontImgMaxSize = 0.3f;
    private float backImgMinSize = 0.1f;
    private float backImgMaxSize = 0.2f;

    private float frontTopLimitPercent = 0;
    private float frontBotLimitPercent = 0;
    private float backTopLimitPercent = 0;
    private float backBotLimitPercent = 0;

    private float xLimitLeft = -40;
    private float xLimitRight = 140;

    private float xOffscreeLenghtLimit = 10;

    private float frontScrollSpeed = 0;
    private float backScrollSpeed = 0;

    private float randomRotation = 0;

    private Random random;

    public RandomBitmapScroller(HashMap<String, Integer> fileNames) {
        this.fileNames = fileNames;
        random = new Random();

        imgs = new ArrayList<AdjustedBitmap>();
        AdjustedBitmap img;

        for(HashMap.Entry<String, Integer> entry: fileNames.entrySet()) {

            for (int i = 0; i < entry.getValue(); i++) {
                img = new AdjustedBitmap(entry.getKey());
                randomizeImg(img, randomFloat(xLimitLeft, xLimitRight));
                imgs.add(img);
            }
        }
    }

    public void randomizeImgs() {
        for (AdjustedBitmap img: imgs) {
            randomizeImg(img, randomFloat(xLimitLeft, xLimitRight));
        }
    }

    private float randomFloat(float min, float max) {
        return (max - min) * random.nextFloat() + min;
    }

    private int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public void drawFront(Canvas canvas) {

        for(AdjustedBitmap img : imgs) {
            PointF coordinates = img.getCoordinatesPercent();

            if (img.getLayer()  == 0 ) {                     //filter those above bunchie
                img.draw(canvas);

                img.setCoordinates(coordinates.x + frontScrollSpeed, coordinates.y, true);
                checkBoundsX(img);
            }
        }
        Collections.sort(imgs); //we only need to sort it once per whole frame drawn;
    }

    public void drawBack(Canvas canvas) {

        for(AdjustedBitmap img : imgs) {
            PointF coordinates = img.getCoordinatesPercent();

            if (img.getLayer() > 0 ) {                     //filter those below bunchie
                img.draw(canvas);

                img.setCoordinates(coordinates.x + backScrollSpeed, coordinates.y, true);
                checkBoundsX(img);
            }
        }
    }

    private void randomizeImg(AdjustedBitmap img, float x) {

        if (random.nextBoolean()) { // in front of bunchie
            img.setCoordinates(x + randomFloat(-xOffscreeLenghtLimit, xOffscreeLenghtLimit), randomFloat(frontTopLimitPercent, frontBotLimitPercent), true);
            img.setScaleToScreenRatio(randomFloat(frontImgMinSize, frontImgMaxSize));
            img.setLayer(0);

        } else {                    //back of bunchie
            img.setCoordinates(x + randomFloat(-xOffscreeLenghtLimit, xOffscreeLenghtLimit), randomFloat(backTopLimitPercent, backBotLimitPercent), true);
            img.setScaleToScreenRatio(randomFloat(backImgMinSize, backImgMaxSize));
            img.setLayer(1);
        }
        img.rotation = randomFloat(-randomRotation , randomRotation);
        img.getDepth();
    }

    private void checkBoundsX (AdjustedBitmap tree) {

        PointF coord = tree.getCoordinatesPercent();

        if (coord.x > xLimitRight ) {                                   //checking out of bounds
            randomizeImg(tree, xLimitLeft);
        } else if (coord.x < xLimitLeft) {
            randomizeImg(tree, xLimitRight);
        }
    }

    public void setFrontScrollSpeed(float speedPercent) {
        this.frontScrollSpeed = speedPercent;
    }

    public void setBackScrollSpeed(float backScrollSpeed) {
        this.backScrollSpeed = backScrollSpeed;
    }


    public void setFrontDrawingLimits(float topLimitPercent, float botLimitPercent) {
        frontBotLimitPercent = botLimitPercent;
        frontTopLimitPercent = topLimitPercent;
    }

    public void setBackDrawingLimits(float topLimitPercent, float botLimitPercent) {
        backBotLimitPercent = botLimitPercent;
        backTopLimitPercent = topLimitPercent;
    }

    public void setFrontImgMinMaxSize(float frontImgMinSize, float frontImgMaxSize) {
        this.frontImgMinSize = frontImgMinSize;
        this.frontImgMaxSize = frontImgMaxSize;
    }

    public void setBackImgMinMaxSize(float backImgMinSize, float backImgMaxSize) {
        this.backImgMaxSize = backImgMaxSize;
        this.backImgMinSize = backImgMinSize;
    }

    public void setRandomRotation(float randomRotation) {
        this.randomRotation = randomRotation;
    }
}


