package com.castingiq.castingiq.response;

public class UnauthorizedResponse {
    private String message;
    public UnauthorizedResponse(String message){
        this.message=message;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public String getMessage(){
       return message;
    }

}
