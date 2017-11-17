package naivebayes.core;

import general.StandardData;
import naivebayes.dict.MailDict;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mutlinomial distribution event model using naive bayes
 * Created by wingsby on 2017/8/7.
 */

public class MutlinomialModel {
    //训练模型
//    static Map<String,double[]> probabilityModel=new HashMap<String, double[]>();
//
//    public void generateProbabilityModel(List<StandardData> trainSet){
//        // max m
//        int maxd=0;
//        for(StandardData sd:trainSet){
//            maxd=maxd>sd.getLength()?maxd:sd.getLength();
//        }
//        for(int j=1;j<maxd;j++){
//            for(int k=0;k< MailDict.getDictMap().size();k++){
//                String key=String.valueOf(j)+"@"+String.valueOf(k);
//                probabilityModel.put(key,getProbability(k));
//            }
//        }
//    }


    Map<String, Long> wordCount = new HashMap<String, Long>();
    double p1 = 0;
    long wordsum0 = 0;
    long wordsum1 = 0;


    public void GenerateWordCount(List<StandardData> trainSet) {
        int sum = 0;
        wordsum1 = 0;
        wordsum0 = 0;
        for (StandardData sd : trainSet) {
            for (int c : sd.getX()) {
                String key = c + "@" + (sd.isYlabel() ? 1 : 0);
                if (wordCount.containsKey(key)) {
                    wordCount.put(key, wordCount.get(key) + 1);
                } else {
                    wordCount.put(key, 1L);
                }
            }
            if (sd.isYlabel()) {
                sum++;
                wordsum1 += sd.getLength();
            } else wordsum0 += sd.getLength();
        }
        p1 = (sum + 0.0) / trainSet.size();
    }


    public double[] getProbability(int k) {
        double pk1 = 0;
        double pk0 = 0;
        double p = 0;
        String key1 = k + "@" + 1;
        String key0 = k + "@" + 0;
        Long ksum0 = wordCount.get(key0) == null ? 1L : wordCount.get(key0);
        Long ksum1 = wordCount.get(key1) == null ? 1L : wordCount.get(key1);
        // laplace smothing
        pk1 = (0.0 + ksum1 + 1) / (wordsum1 + MailDict.getDictMap().size());
        pk0 = (0.0 + ksum0 + 1) / (wordsum0 + MailDict.getDictMap().size());
        return new double[]{pk0, pk1, p1};
    }

    public List<Boolean> testModel(List<StandardData> testSet) {
        List<Boolean> testres = new ArrayList<Boolean>();
        for (StandardData sd : testSet) {
            testres.add(classify(sd));
        }
        return testres;
    }

    public boolean classify(StandardData data) {
        boolean res = false;
        double pk0 = 1;
        double pk1 = 1;
        double p = 0;
        double tmpP = 1;
        int scale = 0;
        for (int i = 0; i < data.getX().length; i++) {
            double[] probs = getProbability(data.getX()[i]);
            p = probs[2];
            pk0 = probs[0];
            pk1 = probs[1];
            int tmpScale = measureScale(tmpP);
//            if(i%20==1){
                System.out.println(tmpScale+"@@@@@@@@@@@"+scale);
//            }
            scale += tmpScale;
            tmpP *= (pk0 / pk1 * Math.pow(10, tmpScale));
            ;
            if(i%20==1){
                System.out.println(tmpP+"========="+tmpScale+"============="+scale);
            }
            if (tmpP < 10e-5) {
                System.out.println(tmpP);
                System.out.println(scale);
            }

        }
        // pyx-1=1+
        System.out.println();
        double pyx = pk1 * p / (pk1 * p + pk0 * (1 - p));
        if (pyx > 0.5) res = true;
        else res = false;
        return res;
    }

    public int measureScale(double x) {
        int scale = 0;
        if (x < 1) {
            while ((x *= 10) < 1) {
                scale++;
            }
        } else {
            while ((x /= 10) > 1) {
                scale--;
            }
        }
        return scale;
    }


    public void init() {
        //初始化词典
        MailDict.GenerateMailDict();
        //初始化数据集
        StandardData.gerenateDataSet();
        //训练样本
        GenerateWordCount(StandardData.getTrainDataSet());
        //测试样本
        List<Boolean> list = testModel(StandardData.getTestDataSet());
        List<File> files = StandardData.testFiles;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)) files.get(i).renameTo
                    (new File("d:/mail/true" + files.get(i).getName()));
        }
        System.out.println("ok");
    }

    public static void main(String[] args) {
        MutlinomialModel model = new MutlinomialModel();
        model.init();
    }

}
