package DecisionTree;

import java.util.*;

/**
 * Created by wingsby on 2017/10/27.
 */
public class CartTree {
    //gini 指数划分
    Map<Float,String[]> tmpmap=new HashMap<Float, String[]>();
    Map<String, List> featuremap = new HashMap();
    int threSample=5;
    float threGini=0.02f;


    private void chooseBestInsert(CartNode node) {
        List<TrainingData> dataset=node.getData();
        //todo
        if(dataset.size()<threSample||
                getGiniIdx(dataset)<threGini)return;
        List splitvalues=new ArrayList();
        for(int i=0;i<TrainingData.features.size();i++){
            List fspace=featuremap.get(TrainingData.features.get(i));
            for(int j=1;j<fspace.size();j++){
                // 分成两个集合
                List[] lists=splitArray(TrainingData.features.get(i),fspace.get(i),dataset);
                // 求基尼指数
                float gini=getColGini(lists[0],lists[1]);
                String splitValue= String.valueOf(fspace.get(i));
                //考虑重复问题 todo
                tmpmap.put(gini,new String[]{TrainingData.features.get(i),splitValue });
                splitvalues.add(gini);
            }
        }
        Collections.sort(splitvalues);
        String[]str=tmpmap.get(splitvalues.get(0));
        String bestFeature=str[0];
        Comparable splitValue=str[1];//?
        List[] lists=splitArray(bestFeature,splitValue,dataset);
        if(lists!=null) {
            CartNode left=new CartNode(lists[0]);
            CartNode right=new CartNode(lists[1]);
            chooseBestInsert(left);
            chooseBestInsert(right);
        }
    }


    private List[] splitArray(String name,Object obj,List<TrainingData>dataset) {
        List<TrainingData> left = new ArrayList<TrainingData>();
        List<TrainingData> right = new ArrayList<TrainingData>();
        for(TrainingData data:dataset){
            if(data.getValueByName(name).equals(obj))
                left.add(data);
            else  right.add(data);
        }
        return new List[]{left,right};
    }


    private float getColGini(List<TrainingData>left,List<TrainingData>right){
        int totalsz=left.size()+right.size();
        return getGiniIdx(left)*left.size()/totalsz+getGiniIdx(right)*right.size()/totalsz;
    }



    public float getGiniIdx(List<TrainingData>dataList){
        //类
        Map<Number,List<TrainingData>> clzMap=new HashMap();
        for(TrainingData data:dataList){
            if(clzMap.containsKey(data.getClz())){
                clzMap.get(data.getClz()).add(data);
            }else{
                List list=new ArrayList();
                list.add(data);
                clzMap.put(data.getClz(),list);
            }
        }
        float tgini=0;
        int totalsz=dataList.size();
        for(Number number:clzMap.keySet()){
            tgini+=Math.pow(clzMap.get(number).size()/totalsz,2);
        }
        return 1-tgini;

    }

}
