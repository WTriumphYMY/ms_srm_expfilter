package cn.edu.nwpu.ms_srm_expfilter.controller;

import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;
import cn.edu.nwpu.ms_srm_expfilter.repository.SrmExperimentRepository;
import cn.edu.nwpu.ms_srm_expfilter.service.impl.ExperimentDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName indexController
 * @Author: gd
 * @Date: 2019/4/26 17:34
 * @Version: v1.0
 * @Description:
 */
@Controller
public class indexController {

    @Autowired
    ExperimentDataServiceImpl experimentDataServiceImpl;
    @Autowired
    SrmExperimentRepository srmExperimentRepository;
    @GetMapping("/filterId")
    public String filterId(){
        return "filterId";
    }
    @PostMapping("/dataFilter")
    public @ResponseBody Map<String,double[]> smoothData(Integer pkId){

        SrmExperiment srmExperiment = experimentDataServiceImpl.smoothData(pkId);
        Map<String,double[]> srmMap = new HashMap<>();
        srmMap = srmToMap(srmExperiment);
        return srmMap;
    }

    @PostMapping("/getChartData")
    public @ResponseBody Map<String,double[]> getChartData(Integer pkId){
        SrmExperiment srmExperiment=  srmExperimentRepository.findSrmExperimentByPkId(pkId);

        Map<String,double[]> srmMap = new HashMap<>();
        srmMap = srmToMap(srmExperiment);
        return srmMap;
    }
    public  static Map<String,double[]> srmToMap(SrmExperiment srmExperiment){
        String[] expTime = srmExperiment.getExpTime().split(",");
        String[] expPressure = srmExperiment.getExpPressure().split(",");
        String[] expForce = srmExperiment.getExpForce().split(",");
        double[] expTimeD =new double[expTime.length];
        double[] expPressureD =new double[expTime.length];
        double[] expForceD =new double[expTime.length];
        for (int x = 0; x<expForceD.length ;x++){
            expTimeD[x]=Double.parseDouble(expTime[x]);
            expPressureD[x]=Double.parseDouble(expPressure[x]);
            expForceD[x]=Double.parseDouble(expForce[x]);
        }
        Map<String,double[]> srmMap = new HashMap<>();
        srmMap.put("t",expTimeD);
        srmMap.put("p",expPressureD);
        srmMap.put("f",expForceD);
//        model.addAttribute("srmMap",srmMap);
        System.out.println(srmMap.get("t"));
        return srmMap;
    }
//    @GetMapping("/toFile{pkId}")
//    public ModelAndView toFile(Model model, @PathVariable("pkId")Integer pkId){
//        model.addAttribute("pkId",pkId);
//        return new ModelAndView("toFile","srmModel",model);
//    }
//    @PostMapping("/saveAsFile")
//    public @ResponseBody String saveToFile(String path, String pkId,Model model){
//        experimentDataServiceImpl.toFile(path,Integer.parseInt(pkId));
//        String str = path;
////        Map<String,String> map = new HashMap<>();
////        map.put("path",path);
//        model.addAttribute("path",path);
//        return path;
//    }
@GetMapping("/listAll")
public ModelAndView list(Model model) {

    List<SrmExperiment> srmExperimentList = srmExperimentRepository.findAll();
    model.addAttribute("title", "已有数据");
    model.addAttribute("srmExperimentList", srmExperimentList);
    return new ModelAndView("listAll", "srmModel", model);
}
    @GetMapping("/dataProcess{pkId}")
    public ModelAndView process(Model model,@PathVariable("pkId")Integer pkId){
        model.addAttribute("pkId",pkId);
        return new ModelAndView("dataProcess","srmModel",model);
    }
    @GetMapping("/toFile/{pkId}")
    public ModelAndView toFile(Model model,@PathVariable("pkId")Integer pkId){
        model.addAttribute("pkId",pkId);
        return new ModelAndView("toFile","srmModel",model);
    }
    @PostMapping("/saveAsFile")
    public @ResponseBody String saveToFile(String path, String pkId,Model model){
        experimentDataServiceImpl.smoothToFile(path,Integer.parseInt(pkId));
        String str = path;
//        Map<String,String> map = new HashMap<>();
//        map.put("path",path);
        model.addAttribute("path",path);
        return path;
    }
}
