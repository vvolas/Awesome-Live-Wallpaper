package com.wolas.awesomewallpaper;

import android.graphics.Canvas;
import android.graphics.Movie;

import java.io.IOException;

/**
 * Created by wolas on 16.2.14.
 */
public class AdjustedGIF extends CoordinateAdapter {
    private String TAG= "AdjustedGIF";

    private boolean drawByFrame = false;
    private int movieTime = 1;
    private int frameDuration = 0;

    protected Movie movie;

    public AdjustedGIF(String filename) {
        try {
            movie = Movie.decodeStream(Utils.getInstance().getContext().getResources().getAssets().open(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImgResolution(movie.width(),movie.height());
    }

    public void setDrawFrameByFrame(boolean drawByFrame, int frameDuration) {
        this.drawByFrame = drawByFrame;
        this.frameDuration = frameDuration;
    }

    public void draw(Canvas canvas) {

        if (visible) {
            if (drawByFrame) {
                movieTime += frameDuration;
                movie.setTime(movieTime % movie.duration());
            } else {
                movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
            }
            canvas.save();
            canvas.scale(ratioToScreen, ratioToScreen, centeredCoordinates.x, centeredCoordinates.y);
            movie.draw(canvas, coordinates.x, coordinates.y);
            canvas.restore();
        }
        updateScrollLocation();
    }
}
