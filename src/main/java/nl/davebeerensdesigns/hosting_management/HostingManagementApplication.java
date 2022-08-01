package nl.davebeerensdesigns.hosting_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HostingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostingManagementApplication.class, args);
		System.out.println("Application started");
	}

}
