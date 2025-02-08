package com.castingiq.castingiq.response;

public class JwtResponse {
    private String token;
    private String user;
    private long id;
    public JwtResponse(long id,String user,String token) {
        this.token = token;
        this.id=id;
        this.user=user;
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

}

