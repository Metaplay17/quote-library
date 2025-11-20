package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.dto.TagDto;
import org.example.models.Tag;
import org.example.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = new ArrayList<TagDto>();
        tags.forEach((Tag t) -> {
            tagDtos.add(new TagDto(t.getId(), t.getName()));
        });
        return tagDtos;
    }
}
