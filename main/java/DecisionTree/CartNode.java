package DecisionTree;

import java.util.List;

/**
 * Created by wingsby on 2017/10/27.
 */
public class CartNode {
    CartNode left;
    CartNode right;
    List<TrainingData> data;

    public CartNode(List list) {
        this.data=list;
    }


    public CartNode getLeft() {
        return left;
    }

    public void setLeft(CartNode left) {
        this.left = left;
    }

    public CartNode getRight() {
        return right;
    }

    public void setRight(CartNode right) {
        this.right = right;
    }

    public List<TrainingData> getData() {
        return data;
    }

    public void setData(List<TrainingData> data) {
        this.data = data;
    }
}
