package com.udacity.jwdnd.course1.cloudstorage.model.dto;

public class FileDto {

    private Integer fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public FileDto() {
        super();
    }

    public FileDto(Integer fileId, String filename, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
