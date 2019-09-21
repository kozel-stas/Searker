package com.searker.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.searker.rest.service.URLResolver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

public class Document {

    @JsonProperty
    @Null
    private String id;

    @JsonProperty
    @NotNull
    private String title;

    @JsonProperty
    private Date date;

    @JsonProperty
    @NotNull
    private String description;

    @JsonProperty
    @NotNull
    private String location;

    public Document() {
    }

    public Document(com.searker.search.engine.model.Document document, URLResolver urlResolver) {
        setId(document.getId());
        setDate(document.getDate());
        setTitle(document.getTitle());
        setDescription(document.getDescription());
        setLocation(urlResolver.resolveOriginalDocumentLocationURL(document));
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
