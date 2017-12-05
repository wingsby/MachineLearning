import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * Created by wingsby on 2017/11/20.
 */
public class test {
    public static void main(String[] args) {
        int k = 10;
        double s = 0;
        for (int i = 2; i <= k; i++) {
            //计算只有i个相同的概率
            double tmp = 1;
            double ck = 1;
            double ir = 1; //i阶

            for (int j = 0; j < k - i; j++) {
                tmp *= (364 - j) / 365.0;
            }
            for (int n = 0; n < i; n++) {
                ck *= (k - n)/(n+1);
//                ir *= (n + 1);
            }
//            System.out.println(ck/ir*tmp);
            s += (ck * tmp / (Math.pow(365., i - 1)));
            System.out.println(s);
        }
        System.out.println(s);

    }


    static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
        Spliterator<T> s = a.spliterator();
        long targetBatchSize = s.estimateSize() / (ForkJoinPool.getCommonPoolParallelism() * 8);
        new ParEach(null, s, action, targetBatchSize).invoke();
    }

    static class ParEach<T> extends CountedCompleter<Void> {
        final Spliterator<T> spliterator;
        final Consumer<T> action;
        final long targetBatchSize;

        ParEach(ParEach<T> parent, Spliterator<T> spliterator,
                Consumer<T> action, long targetBatchSize) {
            super(parent);
            this.spliterator = spliterator;
            this.action = action;
            this.targetBatchSize = targetBatchSize;
        }

        public void compute() {
            Spliterator<T> sub;
            while (spliterator.estimateSize() > targetBatchSize &&
                    (sub = spliterator.trySplit()) != null) {
                addToPendingCount(1);
                new ParEach<>(this, sub, action, targetBatchSize).fork();
            }
            spliterator.forEachRemaining(action);
            propagateCompletion();
        }
    }

}


