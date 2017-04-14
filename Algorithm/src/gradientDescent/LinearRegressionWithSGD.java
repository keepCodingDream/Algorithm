package gradientDescent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lurenjie on 2017/4/11
 */
public class LinearRegressionWithSGD {
    /**
     * 训练数据集合,每行表示一组数据。
     */
    private double[][] TrainData;
    /**
     * 训练数据集合列数。
     */
    private int col;
    /**
     * 训练数据集合行数。
     */
    private int row;
    /**
     * 训练数据集合,每行对应的Y值。
     */
    private double[] Y;
    /**
     * 训练数据集合的系数权重集合。
     */
    private double[] Weights;
    /**
     * 迭代次数,默认100次
     */
    private int numIterations = 100;
    /**
     * 梯度下降的步数，控制收敛速度。默认为0.01
     */
    private double stepSize = 0.01;
    /**
     * 每次训练使用的数据份数，默认使用1份
     */
    private int miniBatchFraction = 1;
    /**
     * 存放随机数据的下标
     */
    private List<Integer> RandomList;
    /**
     * 误差阀值
     */
    private double Loss = -1.0;

    /**
     * @param trainData 训练的数据，一行表示一组数据。第一个数据为对应的Y值
     */
    public LinearRegressionWithSGD(double[][] trainData) {
        setTrainData(trainData);
        InitWeights();
        checkData();
    }

    /**
     * @param trainData     训练的数据，一行表示一组数据。第一个数据为对应的Y值
     * @param numIterations 迭代次数，默认为100
     */
    public LinearRegressionWithSGD(double[][] trainData, int numIterations) {
        setTrainData(trainData);
        InitWeights();
        this.numIterations = numIterations;
        checkData();
    }

    /**
     * @param trainData     训练的数据，一行表示一组数据。第一个数据为对应的Y值
     * @param numIterations 迭代次数，默认为100
     * @param stepSize      每次迭代的步数，默认0.01
     */
    public LinearRegressionWithSGD(double[][] trainData, int numIterations,
                                   double stepSize) {
        setTrainData(trainData);
        InitWeights();
        this.numIterations = numIterations;
        this.stepSize = stepSize;
        checkData();
    }

    /**
     * @param trainData         训练的数据，一行表示一组数据。第一个数据为对应的Y值
     * @param numIterations     迭代次数，默认为100
     * @param stepSize          每次迭代的步数，默认0.01
     * @param miniBatchFraction 每次迭代使用的数据份数
     */
    public LinearRegressionWithSGD(double[][] trainData, int numIterations,
                                   double stepSize, int miniBatchFraction) {
        setTrainData(trainData);
        InitWeights();
        this.numIterations = numIterations;
        this.stepSize = stepSize;
        this.miniBatchFraction = miniBatchFraction;
        checkData();
    }

    /**
     * @param trainData         训练的数据，一行表示一组数据。第一个数据为对应的Y值
     * @param numIterations     迭代次数，默认为100
     * @param stepSize          每次迭代的步数，默认0.01
     * @param miniBatchFraction 每次迭代使用的数据份数
     * @param initialWeights    初始化权重集合，数组大小与数据向对应
     */
    public LinearRegressionWithSGD(double[][] trainData, int numIterations,
                                   double stepSize, int miniBatchFraction, double[] initialWeights) {
        setTrainData(trainData);
        this.numIterations = numIterations;
        this.stepSize = stepSize;
        this.miniBatchFraction = miniBatchFraction;
        this.Weights = initialWeights;
        checkData();
    }

    /**
     * @param trainData         训练的数据，一行表示一组数据。第一个数据为对应的Y值
     * @param numIterations     迭代次数，默认为100
     * @param stepSize          每次迭代的步数，默认0.01
     * @param miniBatchFraction 每次迭代使用的数据份数
     * @param initialWeights    初始化权重集合，数组大小与数据向对应
     * @param Loss              误差阀值
     */
    public LinearRegressionWithSGD(double[][] trainData, int numIterations,
                                   double stepSize, int miniBatchFraction, double[] initialWeights,
                                   double Loss) {
        setTrainData(trainData);
        this.numIterations = numIterations;
        this.stepSize = stepSize;
        this.miniBatchFraction = miniBatchFraction;
        this.Weights = initialWeights;
        this.Loss = Loss;
        checkData();
    }

    /**
     * 校验参数的正确性
     */
    private void checkData() {
        if (this.numIterations <= 0) {
            System.out.println("迭代次数输入必须大于0");
            System.exit(0);
        }
        if (this.stepSize <= 0) {
            System.out.println("每次迭代的步数必须大于0");
            System.exit(0);
        }
        if (this.miniBatchFraction <= 0) {
            System.out.println("每次迭代使用的数据份数必须大于0");
            System.exit(0);
        }
        if (this.Weights.length != this.TrainData[0].length) {
            System.out.println("输入的初始化权重集合，数组大小必须与训练集合大小相同");
            System.exit(0);
        }
        if ((this.Loss < 0 || Loss > 1) && Loss != -1) {
            System.out.println("输入的误差阀值必须在0和1之间");
            System.exit(0);
        }
    }

    /**
     * 初始化训练集合，将输入数据转化为指定格式。 将每行的数据的Y值存放在集合 double [] Y中， 其余数据组合成新的TrainData集合
     *
     * @param trainData 训练的数据，一行表示一组数据。第一个数据为对应的Y值
     */
    private void setTrainData(double[][] trainData) {
        this.row = trainData.length;
        this.col = trainData[0].length;
        this.Y = new double[this.row];
        this.TrainData = new double[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            this.Y[i] = trainData[i][0];
            this.TrainData[i][0] = 1.0;
            for (int j = 1; j < this.col; j++) {
                this.TrainData[i][j] = trainData[i][j];
            }
        }
    }

    /**
     * 初始化权重集合，默认全为1.0
     */
    private void InitWeights() {
        this.Weights = new double[this.col];
        for (int i = 0; i < this.col; i++) {
            this.Weights[i] = 1.0;
        }
    }

    /**
     * 训练数据
     */
    public void train() {
        double Loss = 1.0;
        int count = this.numIterations;
        while (count > 0 && (this.Loss != -1 && Loss > this.Loss)) {
            // 更新权重集合 Weights
            UpdateWeights();
            // 获取当前误差
            Loss = CalculationLoss();
            count--;
        }
        System.out.println("误差:" + Loss);
        System.out.println("迭代次数:" + (this.numIterations - count));
    }

    private double CalculationLoss() {
        double Loss = 0.0;
        for (int i = 0; i < this.row; i++) {
            double sum = 0.0;
            for (int j = 0; j < this.col; j++) {
                sum += this.Weights[j] * this.TrainData[i][j];
            }
            Loss += Math.pow((sum - this.Y[i]), 2);
        }

        return Loss;
    }

    /**
     * 更新权重集合 Weights
     */
    private void UpdateWeights() {
        getRandom(this.miniBatchFraction);
        for (int i = 0; i < this.col; i++) {
            double dev = 0.0;
            for (Integer rd : this.RandomList) {
                double sum = 0.0;
                for (int j = 0; j < this.col; j++) {
                    sum += this.Weights[j] * this.TrainData[rd][j];
                }
                dev += (sum - this.Y[rd]) * this.TrainData[rd][i];
            }
            this.Weights[i] -= this.stepSize * (dev / this.RandomList.size());
        }
    }

    /**
     * 获取不同的随机数字 范围:[0,this.row)
     *
     * @param num 随机数字的个数
     */
    private void getRandom(int num) {
        this.RandomList = new ArrayList<Integer>();
        int rd = new Random().nextInt(this.row);
        for (int i = 0; i < num; i++) {
            while (this.RandomList.contains(rd)) {
                rd = new Random().nextInt(this.row);
            }
        }
        this.RandomList.add(rd);
    }

    /**
     * @return 返回权重集合
     */
    public double[] getWeights() {
        return this.Weights;
    }

    /**
     * @param testData 测试数据集合，一行表示一组数据，第一个数据为对应的Y值。
     * @return 测试结果和实际结果组成的集合
     */
    public double[][] predict(double[][] testData) {
        double[][] val = new double[testData.length][2];
        for (int i = 0; i < this.row; i++) {
            val[i][0] = testData[i][0];
            val[i][1] = Weights[0];
            for (int j = 1; j < col; j++) {
                val[i][1] += Weights[j] * testData[i][j - 1];
            }
        }
        return val;
    }
}
