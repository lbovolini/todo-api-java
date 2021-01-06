package com.gitlab.lbovolini.todo.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class AmazonS3RemoteStorageService implements RemoteStorageService {

    private final String bucket = "lbovolini-todo";
    // !todo
    private final String tmpdir = System.getProperty("java.io.tmpdir");

    private final AmazonS3 amazonS3;

    @Autowired
    public AmazonS3RemoteStorageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    // !todo se usuario mudar username?
    @Override
    public String upload(String owner, File file) {
        return upload(owner, file.getName(), file);
    }

    @Override
    public File download(String owner, String path) {
        File file = new File(tmpdir + File.separator + path);

        try( S3Object s3object = amazonS3.getObject(bucket, path);
             S3ObjectInputStream inputStream = s3object.getObjectContent();
             OutputStream outputStream = new FileOutputStream(file))
        {
            outputStream.write(inputStream.readAllBytes());
            return file;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public String upload(String owner, String name, File file) {
        String path = owner + File.separator + name;

        return s3upload(path, file);
    }

    private String s3upload(String path, File file) {
        amazonS3.putObject(new PutObjectRequest(bucket, path, file));

        return path;
    }
}
