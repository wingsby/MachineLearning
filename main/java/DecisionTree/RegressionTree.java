package DecisionTree;

import java.util.*;

/**
 * Created by wingsby on 2017/10/26.
 */
public class RegressionTree {
    List<TrainingData> dataset = new ArrayList<TrainingData>();
    Map<Float,String[]> tmpmap=new HashMap<Float, String[]>();
    Map<String, List> featuremap = new HashMap();

    private void chooseBestInsert(CartNode node) {
        List<TrainingData> dataset=node.getData();
        if(dataset.size()<2)return;
        List splitvalues=new ArrayList();
        for(int i=0;i<TrainingData.features.size();i++){
            List fspace=featuremap.get(TrainingData.features.get(i));
            for(int j=1;j<fspace.size();j++){
                // 分成两个集合
                List[] lists=splitArray(TrainingData.features.get(i),fspace.get(i),dataset);
                // 求方差
                float var=variance(TrainingData.features.get(i),fspace.get(i),lists[0])+
                        variance(TrainingData.features.get(i),fspace.get(i),lists[1]);
                String splitValue= String.valueOf(fspace.get(i));
                //考虑重复问题 todo
                tmpmap.put(var,new String[]{TrainingData.features.get(i),splitValue });
                splitvalues.add(var);
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

    private float variance(String name,Object obj,List<TrainingData>dataset){
        //求平均值
        int sum=0;
        int cnt=0;
        for(TrainingData data:dataset){
            sum+=data.getClz();
            cnt++;
        }
        float avg=sum/cnt;
        float var=0;
        for(TrainingData data:dataset){
            var=(data.getClz()-avg)*(data.getClz()-avg);
        }
        return var;
    }



    private List[] splitArray(String name,Object obj,List<TrainingData>dataset) {
        List<TrainingData> left = new ArrayList<TrainingData>();
        List<TrainingData> right = new ArrayList<TrainingData>();
        for(TrainingData data:dataset){
            if(data.getValueByName(name).compareTo(obj)<0)
                left.add(data);
            else  right.add(data);
        }
        return new List[]{left,right};
    }

    private void getFeatureSpace() {

        for (TrainingData data : dataset) {
            for (String feature : data.features) {
                if (featuremap.containsKey(feature)) {
                    if (featuremap.get(feature).contains(data.getValueByName(feature)))
                        ;
                    else
                         featuremap.get(feature).
                                add(data.getValueByName(feature));
                } else {
                    List tmp = new ArrayList();
                    tmp.add(data.getValueByName(feature));
                    featuremap.put(feature, tmp);
                }
            }
        }
    }

}
