package com.swagger.config;

import springfox.documentation.service.Contact;

//@Component
//@ConfigurationProperties(prefix="swagger")
public class ApiInfo extends springfox.documentation.service.ApiInfo{

	public ApiInfo(String title, String description, String version, String termsOfServiceUrl, Contact contact,
			String license, String licenseUrl) {
		super(title, description, version, termsOfServiceUrl, contact, license, licenseUrl);
	}

}
