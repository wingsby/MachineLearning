package gradient;

import general.MLParameter;
import general.StandardData;

import java.util.List;

/**
 * Created by wingsby on 2017/11/15.
 */
public class GradientDescent {
    //stocastic gradient descent
    private float alpha;
    List<StandardData> dataList;
    MLParameter parameter;



    private void init(){
        for(int i=0;i<parameter.getWeights().size();i++){
            parameter.getWeights().set(i,1f);
        }
    }

    private void startUp(){
        init();
        int sz=parameter.getWeights().size();
        for(int i=0;i<dataList.size();i++){
            for(int j=0;j<sz;j++){
                float tmp=Float.valueOf(parameter.getWeights().get(j))
                        +alpha*((dataList.get(i).isYlabel()?1:0)-
                        hypothesis(dataList.get(i)))*dataList.get(i).getX()[j];
                        parameter.getWeights().set(j,tmp);
            }
        }

    }

    private float hypothesis(StandardData data){
        float tmp=0;
        for(int j=0;j<data.getLength();j++){
            tmp+=parameter.getWeights().get(j)*data.getX()[j];
        }

        float res= (float) (1/(1+Math.exp(-1*tmp)));
        return res;
    }

}
