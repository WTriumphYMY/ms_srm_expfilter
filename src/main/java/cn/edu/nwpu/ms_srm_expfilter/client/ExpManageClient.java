package cn.edu.nwpu.ms_srm_expfilter.client;

import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @InterfaceName ExpManageClient
 * @Author: wkx
 * @Date: 2019/5/7 14:21
 * @Version: v1.0
 * @Description:
 */
@FeignClient(value = "ms-srm-exp-manage")
public interface ExpManageClient {
    @PostMapping("/getExpByName")
    SrmExperiment getExpByName(@RequestParam String srmName);

    @PostMapping("/findAllSrmName")
    List<String> getAllSrmName();

}
