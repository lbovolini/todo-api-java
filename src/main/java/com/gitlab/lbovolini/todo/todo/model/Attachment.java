package com.gitlab.lbovolini.todo.todo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

public class Attachment {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = READ_ONLY)
    private String name;
    @NotBlank
    private String path;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = READ_ONLY)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = READ_ONLY)
    private String extension;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = READ_ONLY)
    private Long size;

    public Attachment() {}

    public Attachment(String path) {
        this.path = path;
    }

    public Attachment(String name, String path, String type, String extension, Long size) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.extension = extension;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(name, that.name)
                && Objects.equals(path, that.path)
                && Objects.equals(type, that.type)
                && Objects.equals(extension, that.extension)
                && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, type, extension, size);
    }
}
