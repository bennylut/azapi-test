/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graphics.graph;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

/**
 *
 * @author Shl
 */
public class EdgeStroke {
    private final int width;
    private final StrokeLineCap lineCap;
    private final StrokeLineJoin lineJoin;
    private final Paint paint;

    EdgeStroke(int width, StrokeLineCap lineCap, StrokeLineJoin lineJoin, Paint paint) {
        this.width = width;
        this.lineCap = lineCap;
        this.lineJoin = lineJoin;
        this.paint = paint;
    }

    public int getWidth() {
        return width;
    }

    public StrokeLineCap getLineCap() {
        return lineCap;
    }

    public StrokeLineJoin getLineJoin() {
        return lineJoin;
    }

    public Paint getPaint() {
        return paint;
    }
    
    
    
    
}