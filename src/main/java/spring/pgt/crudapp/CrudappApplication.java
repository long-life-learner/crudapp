package spring.pgt.crudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import spring.pgt.crudapp.controller.UserController;

@SpringBootApplication
public class CrudappApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext springContext = SpringApplication.run(CrudappApplication.class, args);

		// UserController userControllerBean =
		// springContext.getBean(UserController.class);
		// System.out.println("UserController Bean: " + userControllerBean);
	}

}
