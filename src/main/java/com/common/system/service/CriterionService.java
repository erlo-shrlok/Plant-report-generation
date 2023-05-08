package com.common.system.service;

import com.common.system.entity.Cla;
import com.common.system.entity.Criterion;
import com.common.system.entity.Refer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CriterionService {
    void uploadExcel(MultipartFile file);
    void uploadExcel2(MultipartFile file);

    List<String> getClaList();

    List<Cla> getClas(Integer rid);

    List<String> getTypeListByCla(String cla);
//
//    List<Refer> getReferByType(String type);

    List<Criterion> findAll(Integer rid);

    List<Criterion> allCriterion();
}
