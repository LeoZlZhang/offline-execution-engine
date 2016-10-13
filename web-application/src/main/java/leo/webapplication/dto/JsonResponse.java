package leo.webapplication.dto;

import com.google.api.client.json.Json;

/**
 * Created by leozhang on 9/11/16.
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class JsonResponse {

    private Boolean status = true;
    private String message;
    private Object result;


    public JsonResponse(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static JsonResponse success(Object result){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setResult(result);
        return jsonResponse;
    }

    public static JsonResponse fail(String message, Object result){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus(false);
        jsonResponse.setMessage(message);
        jsonResponse.setResult(result);
        return jsonResponse;
    }


    public static JsonResponse fail(String message){
        return JsonResponse.fail(message, null);
    }


}
