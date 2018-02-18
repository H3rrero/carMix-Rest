package com.carmix;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarmixApplication {

	public static void main(String[] args) {
//		if (SystemUtils.IS_OS_WINDOWS) {
			System.setProperty("server.ssl.keyStore", "src/main/resources/keyStoreSW.jks");
			ClassLoader classLoader = new CarmixApplication().getClass().getClassLoader();
//			File file = new File(classLoader.getResource("trustStoreWS.ts").getFile());
//			System.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
//		} else {
//			System.setProperty("javax.net.ssl.trustStore", "/home/pablo/microservicios/certificados/trustStoreTFG.ts");
//			System.setProperty("server.ssl.keyStore", "/home/pablo/microservicios/certificados/keyStoreTFG.jks");
//		}
		System.setProperty("server.ssl.keyStorePassword", "clave1234");
		System.setProperty("server.ssl.keyPassword", "clave1234");
//		System.setProperty("javax.net.ssl.trust-store-password", "clave1234");
		
		SpringApplication.run(CarmixApplication.class, args);
	}
}
