package guru.springframework.msscbeerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//SpringBoot scans for @Component anno inside the package where the main() class is
//In this example it's the package in which this class is : guru.springframework.msscbeerservice
//Classes outside of this package will not be scanned by SpringBoot even if they are annotated with @Component anno
public class MsscBeerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsscBeerServiceApplication.class, args);
    }

}
