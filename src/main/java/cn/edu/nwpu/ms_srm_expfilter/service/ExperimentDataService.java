package cn.edu.nwpu.ms_srm_expfilter.service;

import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @InterfaceName SrmExperimentService
 * @Author: gd
 * @Date: 2019/4/26 17:38
 * @Version: v1.0
 * @Description:
 */
public interface ExperimentDataService {
    /**
     * 添加到数据库
     * @param expFile
     */
    void addExperimentData (File expFile, String srmName);

    /**
     * 根据ID删除实验数据
     * @param id
     */
    void deleteExperimentDataById(Integer id);

    /**
     * 根据ID修改数据
     * @param srmExperiment
     */
    void updateExperimentDataById(SrmExperiment srmExperiment);

    /**
     * 根据ID查询单个数据
     * @param id
     * @return
     */
    SrmExperiment findSrmExperimentByPkId(Integer id);

    /**
     * 根据名字查询结果集合
     * @param srmName
     * @return
     */
    SrmExperiment findSrmExperimentBySrmName(String srmName);

    /**
     * 查询所有结果
     * @return
     */
    List<SrmExperiment> findAll();

    /**
     * 对数据的滤波
     * @param id
     * @return
     */
    SrmExperiment smoothData(Integer id) throws IOException;

    /**
     * 将数据存到文件
     * @param path
     * @param id
     */
    void toFile(String path, Integer id);//如何加入文件路径？

    /**
     * 将滤波数据保存到文件
     * @param path
     * @param id
     */
    void smoothToFile(String path, Integer id);

    /**
     * 矩阵乘法运算
     * @param a
     * @param b
     * @return
     */
    double[][] matrixResult(double a[][], double b[][]);

    /**
     * 求转置矩阵
     * @param temp
     * @return
     */
    double[][] reverse(double temp [][]);

    /**
     * 获得SG滤波算法中的X矩阵
     * @param m
     * @param k
     * @return
     */
    double[][] getXMartrix(int m,int k);

    /**
     * 获得(h,v)坐标的余子式
     * @param data
     * @param h
     * @param v
     * @return
     */
    double[][] getConfactor(double[][] data, int h, int v);

    /**
     * 计算行列式的值
     * @param data
     * @return
     */
    double getMartrixResult(double[][] data);

    /**
     * 获得系数矩阵
     * @param m
     * @param k
     * @return
     */
    double[][] getCoMatrix(int m, int k);

    /**
     * 获得逆矩阵
     * @param data
     * @return
     */
    double[][] getReverseMatrix(double[][] data);
}

