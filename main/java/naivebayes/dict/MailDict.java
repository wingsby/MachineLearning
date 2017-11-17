package naivebayes.dict;

import util.PropertiesUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wingsby on 2017/8/7.
 */
public class MailDict {
    public MailDict(){}

    public static Map<Character,Integer>map=new HashMap<Character, Integer>();

    public static void GenerateMailDict(){
        Map<String,String> propmap=PropertiesUtil.loadProperties("dict.properties");
        String path=propmap.get("dict.path");
        File[] files=new File(path).listFiles();
        for(File file:files){
            BufferedReader reader= null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String str=null;
                while((str=reader.readLine())!=null){
                    for(char ch:str.toCharArray()){
                        if(!map.containsKey(ch))map.put(ch,map.size()+1);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("dict ok");
    }

    public static Map<Character, Integer> getDictMap() {
        return map;
    }
}
