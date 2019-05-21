package cn.edu.nwpu.ms_srm_expfilter.controller;

import cn.edu.nwpu.ms_srm_expfilter.client.ExpManageClient;
import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_expfilter.service.ExperimentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName indexController
 * @Author: gd
 * @Date: 2019/4/26 17:34
 * @Version: v1.0
 * @Description:
 */
@Controller
public class IndexController {

    @Autowired
    private ExperimentDataService experimentDataService;
    @Autowired
    private ExpManageClient expManageClient;

    @GetMapping("/index")
    public String showIndex(Model model){
        model.addAttribute("srmNames", expManageClient.getAllSrmName());
        return "newindex";
    }

    @PostMapping("/smoothData")
    @ResponseBody
    public List<Double> soothData(@RequestBody double[] doubleList) throws IOException {
        return experimentDataService.smoothData(doubleList);
    }

    @GetMapping("/smooth/{srmName}")
    public String soothData(@PathVariable String srmName, Model model) throws IOException {
        SrmExperiment srmExperiment = expManageClient.getExpByName(srmName);
        String[] tStrArr = srmExperiment.getExpTime().split(",");
        String[] pStrArr = srmExperiment.getExpPressure().split(",");
        String[] fStrArr = srmExperiment.getExpForce().split(",");
        double[] pArr = new double[pStrArr.length];
        double[] fArr = new double[fStrArr.length];
        for (int i = 0; i < pStrArr.length; i++) {
            pArr[i] = Double.parseDouble(pStrArr[i]);
            fArr[i] = Double.parseDouble(fStrArr[i]);
        }
        List<Double> pSmoothList = experimentDataService.smoothData(pArr);
        List<Double> fSmoothList = experimentDataService.smoothData(fArr);
        model.addAttribute("timeSeries", Arrays.asList(tStrArr));
        model.addAttribute("rawPSeries", Arrays.asList(pStrArr));
        model.addAttribute("rawFSeries", Arrays.asList(fStrArr));
        model.addAttribute("smoothPSeries", pSmoothList);
        model.addAttribute("smoothFSeries", fSmoothList);
        model.addAttribute("srmNames", expManageClient.getAllSrmName());
        return "newindex";
    }

}
