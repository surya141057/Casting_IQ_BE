package com.castingiq.castingiq.response;

public class JwtResponse {
    private String token;
    private String user;
    private String message;
    private long id;
    public JwtResponse(String message,long id,String user,String token) {
        this.token = token;
        this.id=id;
        this.user=user;
        this.message=message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

