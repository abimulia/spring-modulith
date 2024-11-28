package com.abimulia.sm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class SpringModulithApplicationTests {

	@Test
	void contextLoads() {
		
		var modules = ApplicationModules.of(SpringModulithApplication.class);
		for (var m: modules)
			System.out.println("module: "+ m.getName()+ ":"+ m.getBasePackage());
		modules.verify();
		
		new Documenter(modules).writeIndividualModulesAsPlantUml().writeModulesAsPlantUml();
	}

}
