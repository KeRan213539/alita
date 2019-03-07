package top.klw8.test.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.klw8.alita.entitys.user.AlitaUserAccount;


/**
 * @ClassName: GetFiledTest
 * @Description: 获取类的字段测试
 * @author klw
 * @date 2018年10月1日 下午4:54:49
 */
public class GetFieldTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
	AlitaUserAccount bean = new AlitaUserAccount();
	bean.setId(111L);
	bean.setUserPhoneNum("138888888888");
	bean.setUserPwd("xxxxxxx");
	
	List<Field> fieldList = new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields()));
	for(Field field : fieldList) {
	    if(field.getName().equals("serialVersionUID")) {
		continue;
	    }
	    field.setAccessible(true);
	    Object obj = field.get(bean);
	    if(obj != null) {
		System.out.println(field.getName() + " = " + obj);
	    }
	}
    }

}
