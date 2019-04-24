package com.ideacrest.hireup.helper;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import com.ideacrest.app.bean.EurekaInstance;
import com.ideacrest.app.bean.RegisterEurekaInstance;
import com.ideacrest.app.configuration.ConfigurationProperty;

@Singleton
public class EurekaInstaceBeanHelper {

	public static RegisterEurekaInstance getEurekaInstanceBean() {
		EurekaInstance instance = new EurekaInstance();
		instance.setHostName(ConfigurationProperty.HOST_NAME_PROPERTY.get());
		instance.setApp(ConfigurationProperty.APP_NAME_PORPERTY.get());
		instance.setVipAddress(ConfigurationProperty.APP_NAME_PORPERTY.get());
		instance.setIpAddr(ConfigurationProperty.IP_ADDRESS_PROPERTY.get());
		instance.setSecureVipAddress(ConfigurationProperty.APP_NAME_PORPERTY.get());
		instance.setStatus("UP");

		Map<String, String> portMapData = new HashMap<>();
		portMapData.put("$", ConfigurationProperty.PORT_PROPERTY.get());
		portMapData.put("@enabled", "true");
		instance.setPort(portMapData);

		Map<String, String> securePortMapData = new HashMap<>();
		securePortMapData.put("$", "8443");
		securePortMapData.put("@enabled", "false");
		instance.setSecurePort(securePortMapData);

		instance.setHealthCheckUrl(ConfigurationProperty.HOST_URL_PROPERTY.get());
		instance.setStatusPageUrl(ConfigurationProperty.HOST_URL_PROPERTY.get());
		instance.setHomePageUrl(ConfigurationProperty.HOST_URL_PROPERTY.get());

		Map<String, String> mapData = new HashMap<>();
		mapData.put("@class", "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo");
		mapData.put("name", "MyOwn");
		instance.setDataCenterInfo(mapData);

		RegisterEurekaInstance bean = new RegisterEurekaInstance();
		bean.setInstance(instance);
		return bean;
	}

}
