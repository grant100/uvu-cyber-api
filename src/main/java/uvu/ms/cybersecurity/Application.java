package uvu.ms.cybersecurity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.util.ArrayList;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Value("${server.port}")
    private Integer port;

    @Value("${server.servlet.contextPath}")
    private String path;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    // Bootstrap database
    @Bean
    public CommandLineRunner init() {

        return (args)->{

            try{
                String host = InetAddress.getLocalHost().getHostAddress();
                String appUrl = String.format("http://%s:%s%s", host, port, path);
                System.out.println("Application started at "+appUrl);
            }catch (Exception e){
                // squash
            }

        };
    }
}
