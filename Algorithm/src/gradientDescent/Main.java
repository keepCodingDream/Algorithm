package gradientDescent;

/**
 * Created by lurenjie on 2017/4/11
 */
public class Main {
    public static void main(String[] args) {
        double[][] trainData = {{19, 1, 4}, {26, 2, 5}, {19, 5, 1},
                {20, 4, 2}};
        double[] Weights = {0, 2, 4};
        LinearRegressionWithSGD model = new LinearRegressionWithSGD(trainData,
                100000, 0.005, 1, Weights, 0.0001);
        model.train();
        double[] W = model.getWeights();
        System.out.println("当前系数权值为:");
        for (int i = 0; i < W.length; i++) {
            System.out.println(W[i]);
        }
    }
}
