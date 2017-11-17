package general;

import java.util.List;

/**
 * Created by wingsby on 2017/11/17.
 */
public class MLParameter {
    List<String> parameters;
    List<Float> weights;

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }


    public List<Float> getWeights() {
        return weights;
    }

    public void setWeights(List<Float> weights) {
        this.weights = weights;
    }
}
