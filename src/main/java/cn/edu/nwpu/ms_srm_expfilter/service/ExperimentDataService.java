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
     * 对数据的滤波
     * @param rawArray 原始数据
     * @return
     */
    List<Double> smoothData(double[] rawArray) throws IOException;



}

