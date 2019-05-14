package cn.edu.nwpu.ms_srm_expfilter.service.impl;

import cn.edu.nwpu.ms_srm_expfilter.service.ExperimentDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExperimentDataServiceImpl
 * @Author: gd
 * @Date: 2019/4/26 17:41
 * @Version: v1.0
 * @Description: 滤波服务
 */
@Service
public class ExperimentDataServiceImpl implements ExperimentDataService {

    @Override
    public List<Double> smoothData(double[] rawArray)  {

        ArrayList<Double> smoothList = new ArrayList<>();

        int len = rawArray.length;
        int m=7;
        int k = 2;
        double[][] WC = getCoMatrix(m,k); //获得系数矩阵
        double sum;
        for(int x = 0; x<len; x++){
            if (x<m){
                sum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    sum+=WC[x][y]*rawArray[y];
                }
                smoothList.add(sum);
            }
            else if(x>=len-m){
                sum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    sum += WC[x-len+(2*m+1)][y]*rawArray[len-(2*m+1)+y];

                }
                smoothList.add(sum);
            }
            else {
                sum = 0.0;
                for(int y=0;y<(2*m+1);y++){
                    sum += WC[m][y]*rawArray[x-m+y];

                }
                smoothList.add(sum);
            }
        }

        return smoothList;
    }

    /**
     * 获得SG滤波算法中的X矩阵
     * @param m
     * @param k
     * @return
     */
    private double[][] getXMartrix(int m,int k) {
        int n = 2*m+1;
        double[][] XM = new double[n][k+1];
        for(int x = 0;x<n;x++){
            for(int y = 0;y<k+1;y++){
                XM[x][y]=Math.pow(x-m, y);
            }
        }
        return XM;
    }

    /**
     * 求转置矩阵
     * @param temp
     * @return
     */
    private double[][] reverse(double[][] temp) {
        double[][] temp1=new double[temp[0].length][temp.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp1[j][i] = temp[i][j];
            }
        }
        return temp1;
    }

    /**
     * 获得(h,v)坐标的余子式
     * @param data
     * @param h
     * @param v
     * @return
     */
    private double[][] getConfactor(double[][] data, int h, int v) {
        int H = data.length;
        int V = data[0].length;
        double[][] newdata = new double[H-1][V-1];
        for(int i=0; i<newdata.length; i++) {
            if(i < h-1) {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i][j];
                    }else {
                        newdata[i][j] = data[i][j+1];
                    }
                }
            }else {
                for(int j=0; j<newdata[i].length; j++) {
                    if(j < v-1) {
                        newdata[i][j] = data[i+1][j];
                    }else {
                        newdata[i][j] = data[i+1][j+1];
                    }
                }
            }
        }

        return newdata;
    }

    /**
     * 矩阵乘法运算
     * @param a
     * @param b
     * @return
     */
    private double[][] matrixResult(double a[][], double b[][]) {
        //当a的列数与矩阵b的行数不相等时，不能进行点乘，返回null
        if (a[0].length != b.length)
            return null;
        //c矩阵的行数y，与列数x
        int y = a.length;
        int x = b[0].length;
        double c[][] = new double[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++)
                //c矩阵的第i行第j列所对应的数值，等于a矩阵的第i行分别乘以b矩阵的第j列之和
                for (int k = 0; k < b.length; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    /**
     * 计算行列式的值
     * @param data
     * @return
     */
    private double getMartrixResult(double[][] data) {
        /*
         * 二维矩阵计算
         */
        if(data.length==1) return data[0][0];

        if(data.length == 2) {
            return data[0][0]*data[1][1] - data[0][1]*data[1][0];
        }
        /*
         * 二维以上的矩阵计算
         */
        double result = 0.0;
        int num = data.length;
        double[] nums = new double[num];
        for(int i=0; i<data.length; i++) {
            if(i%2 == 0) {
                nums[i] = data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }else {
                nums[i] = -data[0][i] * getMartrixResult(getConfactor(data, 1, i+1));
            }
        }
        for(int i=0; i<data.length; i++) {
            result += nums[i];
        }

//		System.out.println(result);
        return result;
    }

    /**
     * 获得系数矩阵
     * @param m
     * @param k
     * @return
     */
    private double[][] getCoMatrix(int m, int k){
        double[][] xM = getXMartrix(m,k);

        double[][] xx= matrixResult(reverse(xM),xM);

        double[][] xx_re=getReverseMatrix(xx);

        double[][] BM = matrixResult(matrixResult(xM, xx_re),reverse(xM));

        return BM;
    }

    /**
     * 获得逆矩阵
     * @param data
     * @return
     */
    private double[][] getReverseMatrix(double[][] data) {
        double[][] newdata = new double[data.length][data[0].length];
        double A = getMartrixResult(data);
    //		System.out.println(A);
        for(int i=0; i<data.length; i++) {
            for(int j=0; j<data[0].length; j++) {
                if((i+j)%2 == 0) {
                    newdata[i][j] = getMartrixResult(getConfactor(data, i+1, j+1)) / A;
                }else {
                    newdata[i][j] = -getMartrixResult(getConfactor(data, i+1, j+1)) / A;
                }
            }
        }
        newdata = reverse(newdata);//转置矩阵

    //		showMaxtrix(newdata);
        return newdata;
    }



}
