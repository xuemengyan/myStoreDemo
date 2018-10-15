package com.yanxuemeng.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Date;
import java.util.Map;

public class MyBeanUtil {
	public static void populate(Object o,Map<String, String[]> map){
		try {
			//希望认识日期类型
			DateConverter dateConverter = new DateConverter();
			dateConverter.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dateConverter, Date.class);
			BeanUtils.populate(o, map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

}
