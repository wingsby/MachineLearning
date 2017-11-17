package general;

import naivebayes.dict.MailDict;
import org.apache.commons.io.FileUtils;
import util.PropertiesUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by wingsby on 2017/8/7.
 */
public class StandardData {
    int length;
    float [] x=new float[length];
    int ylabel;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public float[] getX() {
        return x;
    }

    public void setX(float[] x) {
        this.x = x;
    }

    public int getYlabel() {
        return ylabel;
    }

    public void setYlabel(int ylabel) {
        this.ylabel = ylabel;
    }

    public float getXx(int idx) {
        return x[idx];
    }

    public void setXx(int idx, Float xx) {
        x[idx] = xx;
    }
}
