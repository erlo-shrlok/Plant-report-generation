package com.common.system;

import com.common.system.entity.TreeGridNode;
import com.common.system.entity.TreeGridWrapper;
import com.common.system.service.RcBaseAreaService;
import com.common.system.service.TreeGridService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonAdminApplicationTests {

	Logger LOG = LoggerFactory.getLogger("CommonAdminApplicationTests");

	@Autowired
	private RcBaseAreaService baseAreaService;
	@Autowired
	private TreeGridService treeGridService;

	@Test
	public void contextLoads() {
		List<TreeGridNode> list = treeGridService.getMenuTreeGridNodes();
		TreeGridWrapper wrapper = new TreeGridWrapper();
		wrapper.setRows(list);
		wrapper.setTotal(list.size());
		LOG.info(wrapper.toString());
	}

	@Test
	public void testMd5(){
		String pwd = "123";
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		int times = 2; //加密次数：2
		String alogrithmName = "md5";//加密算法
		String encodePassword = new SimpleHash(alogrithmName,pwd,salt,times).toString();
		System.out.printf("原始密码是 %s , 盐是： %s, 运算次数是： %d, 运算出来的密文是：%s ",pwd,salt,times,encodePassword);
	}
//	@Test
//	public void Insert(){
//		System.out.println("sdadada");
//		JsonObject main = new JsonParser().parse(AreaData.AREA).getAsJsonObject();
//		JsonArray jsonArray = main.getAsJsonArray("children");
//		RcBaseArea baseArea = new Gson().fromJson(AreaData.AREA,new TypeToken<RcBaseArea>(){}.getType());
//		//第一级
//		baseArea.setCreateTime(new Date());
//		baseArea.setLevel(1);
//		baseArea.setParentCode("0");
//		baseAreaService.insert(baseArea);
//		//第二级
//		List<RcBaseArea> childList  = baseArea.getChildren();
//		if (childList != null){
//			for (RcBaseArea child:childList
//					) {
//				child.setCreateTime(new Date());
//				child.setParentCode(baseArea.getCode());
//				child.setLevel(2);
//				baseAreaService.insert(child);
//				//第三集
//				List<RcBaseArea> sunList = child.getChildren();
//				if (sunList != null){
//					for (RcBaseArea sun:sunList
//							) {
//						sun.setCreateTime(new Date());
//						sun.setParentCode(child.getCode());
//						sun.setLevel(3);
//						baseAreaService.insert(sun);
//					}
//				}
//
//			}
//		}
//
//
//	}
}
