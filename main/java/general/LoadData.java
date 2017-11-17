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
 * Created by wingsby on 2017/11/17.
 */
public class LoadData {

    private static List<StandardData> loadDataSet(String dtype){
        List<StandardData>list=new ArrayList<StandardData>();
        Map<String,String> propmap= PropertiesUtil.loadProperties("core.properties");
        String path=propmap.get(dtype);
        File dir=new File(path);
        Collection<File> files= FileUtils.listFiles(dir,null,true);
        //todo
//        if(dtype.equals("test.path")&&testFiles.size()==0)testFiles.addAll(files);
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
//                sd.setYlabel();
                list.add(sd);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static List<StandardData> loadDimissionData(String filename){
        MLParameter parameter=new MLParameter();
        List<String>paras=new ArrayList<String>();
        List<StandardData> dataList=new ArrayList<StandardData>();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(filename));
            String str=reader.readLine();
            String[]strs=str.split(",");
            for(String s:strs){
                paras.add(s);
            }
            parameter.setParameters(paras);
            while((str=reader.readLine())!=null){
                String[] tmpstrs=str.split(",");
                StandardData sd=new StandardData();
                for(int j=0;j<tmpstrs.length;j++){
                    if(tmpstrs[j].matches("\\d+?")){
                        sd.setXx(j,Float.valueOf(tmpstrs[j]));
                    }else {
                        if(tmpstrs[j].contains("Travel")){
                            if(tmpstrs[j].contains("Rarely"))
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
