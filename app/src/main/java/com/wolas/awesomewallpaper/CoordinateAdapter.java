package com.wolas.awesomewallpaper;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by wolas on 16.2.15.
 */
public class CoordinateAdapter implements Comparable <CoordinateAdapter> {

    private String TAG= "CoordinateAdapter";

    protected Utils utils;
    protected PointF deviceResolution;
    protected PointF imgResolution;

    protected PointF coordinates;           // if scaling applied becomes inacurate due layer overlaping
    protected PointF centeredCoordinates;   //actual coordinates pointing to img center
    protected PointF coordinatesPercent;    //coordinates in percent system
    protected boolean centeredBitmap = false;

    protected float ratioToScreen = 1; //image ratio to whole screen
    protected float scale = 1; //image zoom

    protected float scrollingSpeedPercentX =0;
    protected int scrollingLoopDelay= 0; //after finish loop how long wait;
    private long scrollingTimer = 0;

    private int layer = 0;
    private float depth;   //comparable parameter
    protected boolean visible = true;
    float imgPercentRez = 40; //how far to draw out of visibility
    protected float rotation;



    public CoordinateAdapter() {
        utils = Utils.getInstance();

        coordinates = new PointF(0,0);
        centeredCoordinates = new PointF(0,0);
        coordinatesPercent = new PointF(0,0);
        deviceResolution = utils.getDeviceResolution();

    }

    public void setImgResolution(int xResolution, int yResolution) {
        imgResolution = new PointF(xResolution, yResolution);
    }

    public void setCoordinates(float xPercent, float yPercent, boolean centeredBitmap) {

        coordinatesPercent.x = xPercent;
        coordinatesPercent.y = yPercent;
        this.centeredBitmap = centeredBitmap;

        centeredCoordinates.x=xPercent* deviceResolution.x/100;
        centeredCoordinates.y=yPercent* deviceResolution.y/100;

        if (centeredBitmap) {
            xPercent -= imgResolution.x/2 * 100 / deviceResolution.x;
            yPercent -= imgResolution.y/2 * 100 / deviceResolution.y;
        }

        coordinates.x = deviceResolution.x * xPercent / 100;
        coordinates.y = deviceResolution.y * yPercent / 100;

        calculateDepth();
    }

    public void setScaleToScreenRatio(float scale) {         //calculate img size ratioToScreen to screenResolution
        this.scale = scale;
        double screenFactor = Math.sqrt(deviceResolution.x * deviceResolution.y);
        double picFactor = Math.sqrt(imgResolution.x * imgResolution.y);
        ratioToScreen = (float)(scale / (picFactor/screenFactor));

        calculateDepth();
    }

    public void setScrollingSpeedPercentX(float xPercent) {
        this.scrollingSpeedPercentX = xPercent;
    }

    public void setScrollingDelay(int time) {
        this.scrollingLoopDelay = time;
    }

    public void updateScrollLocation() {
        if (scrollingSpeedPercentX != 0) {
            if (System.currentTimeMillis() - scrollingTimer > scrollingLoopDelay) {
                float newCoord = coordinatesPercent.x + scrollingSpeedPercentX;
                visible = false;
                if (newCoord > 100 + imgPercentRez) {
                    setCoordinates(-imgPercentRez, coordinatesPercent.y, centeredBitmap);
                    scrollingTimer = System.currentTimeMillis();
                } else if (newCoord < -imgPercentRez) {
                    setCoordinates(100 + imgPercentRez, coordinatesPercent.y, centeredBitmap);
                    scrollingTimer = System.currentTimeMillis();
                } else {
                    setCoordinates(newCoord, coordinatesPercent.y, centeredBitmap);
                    visible = true;
                }
            }
        }
    }

    private void calculateDepth() { // not particulary meaningful just for comparing with other imgs so we can know which image is in front
        if (centeredBitmap) {
            depth = centeredCoordinates.y + (imgResolution.y / 2 * ratioToScreen);
        } else {
            depth = centeredCoordinates.y + (imgResolution.y * ratioToScreen);
        }
    }

    public PointF getCoordinatesPercent() {
        return coordinatesPercent;
    }

    public float getDepth () {
        return depth;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public int compareTo(CoordinateAdapter another) {
        return (int)((depth-another.getDepth()) * 100);
    }
}
