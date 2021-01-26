package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFileByName(String filename) {
        return this.fileMapper.getFileByName(filename);
    }

    public boolean isDuplicateFile(String filename) {
        return fileMapper.getFileByName(filename) != null;
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFileById(fileId);
    }

    public int createFile(File file) {
        return fileMapper.insert(new File(null, file.getFilename(), file.getContentType(), file.getFileSize(), file.getUserId(), file.getFileData()));
    }

    public List<File> getUserFiles(User user) {
        return fileMapper.getUserFiles(user.getUserId());
    }
}
