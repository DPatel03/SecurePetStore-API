package com.example.pet_store.dto;

import java.util.List;

public class PetDTO {
    private Long id;
    private Long categoryId; // Only category ID
    private String name;
    private List<String> photoUrls;
    private List<Long> tagIds; // Only tag IDs
    private String status;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(List<String> photoUrls) { this.photoUrls = photoUrls; }

    public List<Long> getTagIds() { return tagIds; }
    public void setTagIds(List<Long> tagIds) { this.tagIds = tagIds; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
