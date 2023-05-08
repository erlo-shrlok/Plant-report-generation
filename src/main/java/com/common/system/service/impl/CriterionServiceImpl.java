package com.common.system.service.impl;

import com.common.system.entity.*;
import com.common.system.mapper.CriterionMapper;
import com.common.system.mapper.ReportItemMapper;
import com.common.system.service.CriterionService;
import com.common.system.service.ReportItemService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CriterionServiceImpl implements CriterionService {

    @Autowired
    private CriterionMapper criterionMapper;

    @Autowired
    private ReportItemService reportItemService;

    @Autowired
    private ReportItemMapper reportItemMapper;

    @Override
    public void uploadExcel(MultipartFile file) {
        Criterion criterion = null;
        Row row = null;
        Workbook workbook = null;
        try {
            //根据file 创建workbook
            workbook = WorkbookFactory.create(file.getInputStream());

            /**
             * 先将第一个安全大类的数据导入数据库
             */
            // 获取工作表的数量
            int num = workbook.getNumberOfSheets();
            // 遍历所有的工作表
            for(int i = 1;i < num;i++) {
                Sheet sheet = workbook.getSheetAt(i);
                criterion = new Criterion();
                criterion.setCla(sheet.getSheetName());// 安全大类
                //获取sheet中的最后一行数据
                int lastRowNum = sheet.getLastRowNum();
                System.out.println(sheet.getSheetName());
                for (int j = 2; j <= lastRowNum; j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(1) != null) {
                        criterion.setType(row.getCell(1).getStringCellValue());// 安全子类
                    } else {
                        criterion.setType(null);
                    }
                    if (row.getCell(2) != null) {
                        criterion.setRefer(row.getCell(2).getStringCellValue());// 测评指标
                    }
                    criterionMapper.insert(criterion);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void uploadExcel2(MultipartFile file) {
        // 删除表中所有记录
        criterionMapper.deleteAll(null);
        // 导入指标
        Criterion criterion = null;
        Row row = null;
        Workbook workbook = null;
        try {
            //根据file 创建workbook
            workbook = WorkbookFactory.create(file.getInputStream());

            /**
             * 先将第一个安全大类的数据导入数据库
             */
            // 获取工作表的数量
            int num = workbook.getNumberOfSheets();
            // 遍历所有的工作表
            for(int i = 1;i < num;i++) {
                Sheet sheet = workbook.getSheetAt(i);
                criterion = new Criterion();
                criterion.setCla(sheet.getSheetName());// 安全大类
                //获取sheet中的最后一行数据
                int lastRowNum = sheet.getLastRowNum();
                System.out.println(sheet.getSheetName());
                for (int j = 2; j <= lastRowNum; j++) {
                    row = sheet.getRow(j);
                    if (row.getCell(1) != null) {
                        criterion.setType(row.getCell(1).getStringCellValue());// 安全子类
                    } else {
                        criterion.setType(null);
                    }
                    if (row.getCell(2) != null) {
                        criterion.setRefer(row.getCell(2).getStringCellValue());// 测评指标
                    }
                    criterionMapper.insert2(criterion);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<String> getClaList() {
        return criterionMapper.getClaList();
    }

    @Override
    public List<Cla> getClas(Integer rid) {
        List<Cla> clas = new ArrayList<>();
        Refer refer = null;

        // 获取所有大类
        List<String> scla = getClaList();
        // 装配List<Cla>，遍历大类
        for(String c:scla){
            Cla cla = new Cla();
            List<Type> ts = new ArrayList<>();
            cla.setClaName(c);
            // 获取当前类下的所有子类
            List<String> types = criterionMapper.getTypeListByCla(c);
            // 遍历子类
            for(String t:types){
                Type type = new Type();
                type.setTypeName(t);
                Map<String,Object> params = new HashMap<>();
                params.put("type",t);
                params.put("rid",rid);
                List<Refer> refers = criterionMapper.getReferByType(params);
                type.setRefers(refers);
                ts.add(type);
            }
            cla.setTypes(ts);
            clas.add(cla);
        }
        return clas;
    }

    @Override
    public List<String> getTypeListByCla(String cla) {
        return criterionMapper.getTypeListByCla(cla);
    }

    @Override
    public List<Criterion> findAll(Integer rid) {
        List<Criterion> criteria = criterionMapper.getAll();
        List<ReportItem> reportItems =  reportItemMapper.findByRid(rid);

        // 将 ReportItem 对象与对应的 Criterion 对象关联起来
        for(Criterion criterion: criteria){
            for(ReportItem reportItem : reportItems){
                if(criterion.getId().equals(reportItem.getcId())){
                    criterion.setReportItem(reportItem);
                    break;
                }
            }
        }
        return criteria;
    }

    @Override
    public List<Criterion> allCriterion() {
        return criterionMapper.selectList(null);
    }
//
//    @Override
//    public List<Refer> getReferByType(String type) {
//        return criterionMapper.getReferByType(type);
//    }
}
