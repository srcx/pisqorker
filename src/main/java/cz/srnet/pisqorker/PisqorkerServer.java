package cz.srnet.pisqorker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cz.srnet.pisqorker.game")
@ComponentScan("cz.srnet.pisqorker.rest")
public class PisqorkerServer {

	public static void main(String[] args) {
		SpringApplication.run(PisqorkerServer.class, args);
	}

}