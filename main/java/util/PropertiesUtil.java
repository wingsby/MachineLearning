package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wingsby on 2017/8/7.
 */
public class PropertiesUtil {
    public static Map<String,String> loadProperties(String name){
        Map<String,String> proertiesMap=new HashMap<String, String>();
        try {
            System.out.println(PropertiesUtil.class.getClassLoader().getResource(name).getPath());
            BufferedReader reader=new BufferedReader(new FileReader(PropertiesUtil.class.getClassLoader().getResource(name).getPath()));
            String str=null;
            while((str=reader.readLine())!=null){
                String[] strs=str.split("@");
                if(strs!=null&&strs.length>=1){
                    String value="";
                    if(strs.length>1)value=strs[1];
                    proertiesMap.put(strs[0],value);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proertiesMap;
    }

}
