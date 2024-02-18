package tr.com.anser.offiicematrix.officematrixmanagement.dto.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.anser.offiicematrix.officematrixmanagement.enums.RESPONSE_TYPE_ENUM;
import tr.com.anser.offiicematrix.officematrixmanagement.service.WeekDataService;

public class ResponseDTO {
    Logger logger = LoggerFactory.getLogger(ResponseDTO.class);
    private Object responseData;
    private String responseMessage;
    private RESPONSE_TYPE_ENUM responseType;

    public Object getResponseData(){
        return responseData;
    }
    public String getResponseMessage (){
        return responseMessage;
    }
    public RESPONSE_TYPE_ENUM getResponseType(){
        return responseType;
    }
    public void setResponseData(Object data, String message, RESPONSE_TYPE_ENUM type){
        responseData = data;
        responseMessage = message;
        responseType = type;
    }
    public void setResponseMessage(String message, RESPONSE_TYPE_ENUM type){
        logger.info("gelenmesaj: " + message);
        responseMessage = message;
        responseType = type;
    }
}
