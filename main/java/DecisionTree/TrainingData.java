package DecisionTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wingsby on 2017/10/26.
 */
public class TrainingData {
    Map<String,Comparable> map=new HashMap();
    public static List<String>features=new ArrayList<String>();

    public int getClz() {
        return clz;
    }

    public void setClz(int clz) {
        this.clz = clz;
    }

    private int clz;

    public Comparable getValueByName(String name){
        return map.get(name);
    }



}
