package pmm.pbm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("pmm.pbm.data")
public class PbmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PbmApplication.class, args);
	}
}
