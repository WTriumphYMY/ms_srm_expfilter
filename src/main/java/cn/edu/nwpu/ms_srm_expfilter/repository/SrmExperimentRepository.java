package cn.edu.nwpu.ms_srm_expfilter.repository;

import cn.edu.nwpu.ms_srm_expfilter.domain.SrmExperiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @InterfaceName SrmExperimentRepository
 * @Author: gd
 * @Date: 2019/4/26 17:37
 * @Version: v1.0
 * @Description:
 */
public interface SrmExperimentRepository extends JpaRepository<SrmExperiment, Integer> {
    SrmExperiment findSrmExperimentByPkId(Integer id);
    SrmExperiment findBySrmName(String srmName);
    SrmExperiment save(SrmExperiment srmExperiment);
    List<SrmExperiment> findBySrmNameLike(String srmName);

}
