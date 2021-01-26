package com.udacity.jwdnd.course1.cloudstorage.model.dto;

public class CredentialDto {

    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private String clearPassword;
    private Integer userId;

    public CredentialDto(Integer credentialId, String url, String username, String key, String password, String clearPassword, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.clearPassword = clearPassword;
        this.userId = userId;
    }

    public CredentialDto() {
        super();
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClearPassword() {
        return clearPassword;
    }

    public void setClearPassword(String clearPassword) {
        this.clearPassword = clearPassword;
    }
}
