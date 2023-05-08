package com.common.system.service.impl;

import com.common.system.entity.RcMenu;
import com.common.system.entity.RcMenuExample;
import com.common.system.mapper.RcMenuMapper;
import com.common.system.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/8/2.
 * Time:14:01
 * ProjectName:Common-admin
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private RcMenuMapper menuMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(RcMenu record) throws Exception{
        return menuMapper.insert(record);
    }

    @Override
    public RcMenu selectByPrimaryKey(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(RcMenu record) {
        return menuMapper.updateByPrimaryKeySelective(record);
    }
    @Override
    public int updatePcode(String oldPcode,String newPcode) {
        return menuMapper.updatePcode(oldPcode,newPcode);
    }
    @Override
    public PageInfo<RcMenu> listForPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        RcMenuExample example = new RcMenuExample();
        RcMenuExample.Criteria criteria = example.createCriteria();
//        criteria.andPIdEqualTo("000000000000000000");
//        example.setOrderByClause("p_id");
        List<RcMenu> list = menuMapper.selectByExample(example);
//        List<RcMenu> finalList = new ArrayList<>();
//        if (list !=null && list.size()>0){
//            for (RcMenu m:list
//                 ) {
//                List<RcMenu> childs = getByParentId(m.getId());
//                finalList.add(m);
//                finalList.addAll(childs);
//                m.setChild(childs);
//            }
//        }
        PageInfo<RcMenu> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<RcMenu> getMenu() {
        List<RcMenu> list = menuMapper.selectByExample(new RcMenuExample());
        return list;
    }

    @Override
    public RcMenu selectCode(String code) {
        RcMenuExample example = new RcMenuExample();
        RcMenuExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);
        List<RcMenu> menus = menuMapper.selectByExample(example);
        if (menus != null && menus.size() > 0){
            return menus.get(0);
        }
        return null;
    }

    @Override
    public List<RcMenu> selectInIds(List<String> ids,List<Integer> wantLevel) {
        // Create a new instance of RcMenuExample, which will be used to specify(指定) the search criteria(条件)
        RcMenuExample example = new RcMenuExample();
        // Create a Criteria object from the example, which allows the selection(选择) criteria(条件) to be specified
        RcMenuExample.Criteria criteria = example.createCriteria();
        // Specify that search should only include menu items(菜单项) with ids in the given list (指定搜索应该只包括在给定列表中具有id的菜单项)
        criteria.andIdIn(ids);
        // If the desired(所需的) menu levels list is not null, specify that the search should only include menu items with levels in the given list
        if (wantLevel != null){
            criteria.andLevelIn(wantLevel);
        }
        // Set the sorting order(排序顺序) for the search results to be descending(降序) based on the "sort" field of the RcMenu objects
        example.setOrderByClause("sort desc");
        // Use the menuMapper object to execute(执行) the search and return the list of RcMenu objects that match the criteria
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<RcMenu> getByParentId(String pId) {
        RcMenuExample example = new RcMenuExample();
        RcMenuExample.Criteria criteria = example.createCriteria();
        criteria.andPIdEqualTo(pId);
        return menuMapper.selectByExample(example);
    }
}
