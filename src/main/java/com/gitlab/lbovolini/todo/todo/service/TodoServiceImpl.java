package com.gitlab.lbovolini.todo.todo.service;

import com.gitlab.lbovolini.todo.todo.model.Attachment;
import com.gitlab.lbovolini.todo.storage.RemoteStorageService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import com.gitlab.lbovolini.todo.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final RemoteStorageService remoteStorageService;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, RemoteStorageService remoteStorageService) {
        this.todoRepository = todoRepository;
        this.remoteStorageService = remoteStorageService;
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

    public void delete(String id, String loggedUserid) {
        todoRepository.delete(id, loggedUserid);
    }

    @Override
    public Optional<List<Resource>> downloadAttachments(String id) {
        return todoRepository.findById(id)
                .stream()
                .map(todo ->
                        todo.getAttachments()
                                .stream()
                                .map(attachment -> remoteStorageService.download(todo.getUserId(), attachment.getPath()))
                                .collect(Collectors.toList()))
                .findFirst();
    }

    @Override
    @PostAuthorize("isOwnerById(returnObject.userId)")
    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    @PreAuthorize("isOwnerById(#todo.userId)")
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    @PreAuthorize("isOwnerById(#todo.userId)")
    public Todo save(Todo todo, MultipartFile multipartFile) {

        try {
            File file = convertToFile(multipartFile);
            String owner = todo.getUserId();
            String path = remoteStorageService.upload(owner, file);

            Attachment attachment = new Attachment();
            attachment.setPath(path);

            todo.setAttachments(List.of(attachment));

            return todoRepository.save(todo);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    @Override
    @PreAuthorize("isOwnerById(#todo.userId)")
    public Optional<Todo> update(Todo todo) {
        return todoRepository.update(todo);
    }

    @Override
    @PreAuthorize("isOwnerById(#todo.userId)")
    public Optional<Todo> update(Todo todo, MultipartFile multipartFile) {
        try {
            File file = convertToFile(multipartFile);
            String owner = todo.getUserId();
            String path = remoteStorageService.upload(owner, file);

            Attachment attachment = new Attachment();
            attachment.setPath(path);

            todo.setAttachments(List.of(attachment));

            return todoRepository.update(todo);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    // !todo mover para outra classe
    private File convertToFile(MultipartFile multipartFile) throws IOException {

        // This may contain path information depending on the browser used, but it typically will not with any other than Opera.
        Optional<String> nameOptional = Optional.ofNullable(multipartFile.getOriginalFilename())
                .stream()
                .map(fullName -> {
                    String[] names = fullName.split(File.separator);
                    return names[names.length - 1];
                })
                .findFirst()
                .map(resultedName -> resultedName.replaceAll(" ", "-"))
                .map(resultedName -> resultedName.replaceAll(RemoteStorageService.notAllowedCharactersRegex, ""));

        if (nameOptional.isPresent()) {
            File file = new File(nameOptional.get());

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(multipartFile.getBytes());
                return file;
            }
        }

        throw new IOException("Error creating file");
    }
}
