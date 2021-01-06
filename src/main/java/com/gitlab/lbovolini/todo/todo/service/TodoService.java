package com.gitlab.lbovolini.todo.todo;

import com.gitlab.lbovolini.todo.todo.model.Attachment;
import com.gitlab.lbovolini.todo.service.CrudService;
import com.gitlab.lbovolini.todo.service.RemoteStorageService;
import com.gitlab.lbovolini.todo.todo.model.Todo;
import com.gitlab.lbovolini.todo.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements CrudService<Todo> {

    private final TodoRepository todoRepository;
    private final RemoteStorageService remoteStorageService;

    @Autowired
    public TodoService(TodoRepository todoRepository, RemoteStorageService remoteStorageService) {
        this.todoRepository = todoRepository;
        this.remoteStorageService = remoteStorageService;
    }

    @Override
    public void delete(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    @Override
    public Todo save(Todo todo) {
        throw new UnsupportedOperationException();
    }

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
    public Optional<Todo> update(Todo todo) {
        throw new UnsupportedOperationException();
    }

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
