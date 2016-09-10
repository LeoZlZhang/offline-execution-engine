package leo.webapplication.dto;

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

    public static JsonResponse build(Object result){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setResult(result);
        return jsonResponse;
    }

}
