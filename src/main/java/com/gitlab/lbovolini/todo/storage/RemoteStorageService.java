package com.gitlab.lbovolini.todo.storage;

import org.springframework.core.io.Resource;

import java.io.File;

public interface RemoteStorageService {

    // !todo remover
    String notAllowedCharactersRegex = "([^A-Za-z-.]+)";

    Resource download(String owner, String path);

    String upload(String owner, File file);

}
