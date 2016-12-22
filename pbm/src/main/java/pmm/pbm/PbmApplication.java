package pmm.pbm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SpringBootApplication
@MapperScan("pmm.pbm.data")
public class PbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbmApplication.class, args);
    }

    @GetMapping("/test/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/uploadx")
    public String doUpload(MultipartFile file) {
        System.out.println(file);
        return "redirect:/";
    }
}
