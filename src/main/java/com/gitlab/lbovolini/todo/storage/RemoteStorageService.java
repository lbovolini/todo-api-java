package com.gitlab.lbovolini.todo.storage;

import java.io.File;

public interface RemoteStorageService {

    // !todo remover
    String notAllowedCharactersRegex = "([^A-Za-z-.]+)";

    File download(String owner, String path);

    String upload(String owner, File file);

}
