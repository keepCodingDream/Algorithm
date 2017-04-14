package gradientDescent;

/**
 * 批次梯度下降
 * f=ax+by+cZ
 * Created by lurenjie on 2017/4/11
 */
public class BGD {
    private double theta[] = {1, 1, 1};
    private double dataSet[][] = {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
    private double testSet[] = {4, 5, 6};
    private double result[] = {14, 20, 26};
    private double alpha = 0.01;
    private int times = 100000;

    private BGD() {

    }

    public BGD(double dataSet[][], double result[], double alpha) {
        this.dataSet = dataSet;
        this.alpha = alpha;
        this.result = result;
    }

    public void train() {
        while (times-- > 0) {
            //第一个循环是样本个数
            double loss = 0;
            for (int i = 0; i < dataSet.length; i++) {
                //第二个循环遍历所有特征
                for (int j = 0; j < theta.length; j++) {
                    double sum = 0.001 * (result[i] - hResult(dataSet[i])) * dataSet[i][j];
                    theta[j] += sum;
                }
            }
            loss += calculationLoss(dataSet[2], result[2]);
            System.out.println("loss=" + loss);
            if (loss < 0.0000001) {
                break;
            }
        }
        printT();
    }

    /**
     * 计算当前H(X)的值
     *
     * @param x 训练样本
     * @return hx
     */
    private double hResult(double x[]) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * theta[i];
        }
        return sum;
    }

    private double calculationLoss(double x[], double target) {
        return Math.pow(hResult(x) - target, 2);
    }

    private void printT() {
        for (double aTheta : theta) {
            System.out.println(aTheta + ", ");
        }
        System.out.println(hResult(testSet));
    }

    public static void main(String[] args) {
        BGD bgd = new BGD();
        bgd.train();
    }
}
