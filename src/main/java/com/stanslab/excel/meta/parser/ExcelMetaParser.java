package com.stanslab.excel.meta.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.stanslab.excel.annotation.Column;
import com.stanslab.excel.annotation.Ignore;
import com.stanslab.excel.annotation.Sheet;
import com.stanslab.excel.meta.ExcelColumn;
import com.stanslab.excel.meta.ExcelSheet;
import com.stanslab.excel.pojo.parser.FieldValueParser;
import com.stanslab.excel.pojo.parser.MethodValueParser;

/**
 * 
 * @author Stalin
 *
 */
public class ExcelMetaParser {
	
	public static ExcelSheet parseExcelMeta(Class<?> clazz) {
		Sheet sheet = clazz.getAnnotation(Sheet.class);
		if(sheet == null) {
			throw new RuntimeException("@Sheet annotation not found at class " + clazz);
		}
		
		ExcelSheet excelSheet = new ExcelSheet(sheet);
		excelSheet.setColumns(new ArrayList<>(parseColumns(clazz)));
		
		return excelSheet;
	}
	
	private static Set<ExcelColumn> parseColumns(Class<?> clazz) {
		if(clazz != Object.class) {
			Set<ExcelColumn> excelColumns = parseColumns(clazz.getSuperclass());
			
			Ignore ignore = clazz.getAnnotation(Ignore.class);
			if(ignore != null) {
				return excelColumns;
			}
			
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields) {
				Column column = field.getAnnotation(Column.class);
				if(column != null) {
					if(!field.isAccessible()) {
						field.setAccessible(true);
					}
					excelColumns.add(new ExcelColumn(column, new FieldValueParser(field)));
				}
			}
			
			Method[] methods = clazz.getDeclaredMethods();
			for(Method method:methods) {
				Column column = method.getAnnotation(Column.class);
				if(column != null) {
					excelColumns.add(new ExcelColumn(column, new MethodValueParser(method)));
				}
			}
			
			return excelColumns;
		}
		return new LinkedHashSet<>();
	}
	
}
