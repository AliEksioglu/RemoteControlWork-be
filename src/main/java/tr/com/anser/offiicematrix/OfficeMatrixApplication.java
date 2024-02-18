package tr.com.anser.offiicematrix;

import tr.com.anser.offiicematrix.officematrixmanagement.model.response.ResponseMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OfficeMatrixApplication {
	public static void main(String[] args) {
		ResponseMessage.LoadResponseMessageDictionary();
		SpringApplication.run(OfficeMatrixApplication.class, args);
	}
}
