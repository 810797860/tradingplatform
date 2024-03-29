package com.secondhand.tradingplatformgeccocontroller.monitor;

import com.secondhand.tradingplatformgeccocontroller.annotation.MBean;
import org.reflections.Reflections;
import org.weakref.jmx.MBeanExporter;

import java.lang.management.ManagementFactory;
import java.util.Set;

public class GeccoJmx {
	
	private static MBeanExporter exporter = new MBeanExporter(ManagementFactory.getPlatformMBeanServer());
	
	public static void export(String classpath) {
		Reflections reflections = new Reflections("com.secondhand.tradingplatformgeccocontroller.monitor");
		Set<Class<?>> mbeanClasses = reflections.getTypesAnnotatedWith(MBean.class);
		for(Class<?> mbeanClass : mbeanClasses) {
			MBean mbean = (MBean)mbeanClass.getAnnotation(MBean.class); 
			String name = mbean.value();
	    	try {
				exporter.export(classpath+":name="+name, mbeanClass.newInstance());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void unexport() {
		exporter.unexportAllAndReportMissing();
	}

}
