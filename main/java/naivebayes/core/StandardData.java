package naivebayes.core;

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
    static List<StandardData> trainDataSet;
    static List<StandardData> testDataSet;
    static List<File>testFiles=new ArrayList<File>();

    public static void gerenateDataSet(){
        trainDataSet=loadDataSet("train.path");
        testDataSet=loadDataSet("test.path");
    }

    private static List<StandardData> loadDataSet(String dtype){
        List<StandardData>list=new ArrayList<StandardData>();
        Map<String,String> propmap= PropertiesUtil.loadProperties("core.properties");
        String path=propmap.get(dtype);
        File dir=new File(path);
        Collection<File> files= FileUtils.listFiles(dir,null,true);
        if(dtype.equals("test.path")&&testFiles.size()==0)testFiles.addAll(files);
        Map<Character,Integer>dictmap= MailDict.getDictMap();
        for(File file:files){
            StandardData sd=new StandardData();
            List<Integer>dictinx=new ArrayList<Integer>();
            BufferedReader reader= null;
            boolean flag=false;
            if(file.getParent().contains("true"))flag=true;
            try {
                reader = new BufferedReader(new FileReader(file));
                String str=null;
                while((str=reader.readLine())!=null){
                    for(char ch:str.toCharArray()){
                       if(dictmap.containsKey(ch)){
                           dictinx.add(dictmap.get(ch));
                       }else dictinx.add(999999);
                    }
                }
                int[]x=new int[dictinx.size()];
                for(int i=0;i<dictinx.size();i++){
                    x[i]=dictinx.get(i);
                }
                sd.setX(x);
                sd.setLength(x.length);
                sd.setYlabel(flag);
                list.add(sd);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    int length;
    int [] x=new int[length];
    boolean ylabel;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getX() {
        return x;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public boolean isYlabel() {
        return ylabel;
    }

    public void setYlabel(boolean ylabel) {
        this.ylabel = ylabel;
    }

    public static List<StandardData> getTrainDataSet() {
        return trainDataSet;
    }

    public static void setTrainDataSet(List<StandardData> trainDataSet) {
        StandardData.trainDataSet = trainDataSet;
    }

    public static List<StandardData> getTestDataSet() {
        return testDataSet;
    }

    public static void setTestDataSet(List<StandardData> testDataSet) {
        StandardData.testDataSet = testDataSet;
    }
}
