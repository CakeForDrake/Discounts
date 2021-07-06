package com.exadel.sandbox.team5.service.impl;

import com.exadel.sandbox.team5.dao.TagDAO;
import com.exadel.sandbox.team5.dto.TagDto;
import com.exadel.sandbox.team5.entity.Tag;
import com.exadel.sandbox.team5.mapper.MapperConverter;
import com.exadel.sandbox.team5.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;
    private final MapperConverter mapper;

    @Override
    public TagDto getById(Long id) {
        return tagDAO.findById(id)
                .map(tag -> mapper.map(tag, TagDto.class))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<TagDto> getAll() {
        return mapper.mapAll(tagDAO.findAll(), TagDto.class);
    }

    @Override
    public TagDto save(TagDto tagDto) {
        Tag tag = mapper.map(tagDto, Tag.class);
        return mapper.map(tagDAO.save(tag), TagDto.class);
    }

    @Override
    public TagDto update(TagDto tagDto) {
        return this.save(tagDto);
    }

    @Override
    public void delete(Long id) {
        tagDAO.deleteById(id);
    }
}
