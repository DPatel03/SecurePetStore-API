package com.example.pet_store.controllers;

import com.example.pet_store.models.Tag;
import com.example.pet_store.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    // POST: Create a new tag
    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    // GET: Retrieve all tags
//    @GetMapping
//    public List<Tag> getAllTags() {
//        return tagRepository.findAll();
//    }
//    @GetMapping
//    public Tag getAllTags() {
//        return tagRepository.findAll();
//    }

    // GET: Retrieve tag by ID
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable int id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));


    }

    // DELETE: Delete a tag by ID
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id) {

        tagRepository.deleteById(id);
    }
}