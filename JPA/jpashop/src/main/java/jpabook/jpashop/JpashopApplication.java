package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpashopApplication.class, args);

		Hello hello = new Hello();
		hello.setData("Hello");
		String data = hello.getData();
		System.out.println("data = "+data);

		SpringApplication.run(JpashopApplication.class, args);
	}
}
