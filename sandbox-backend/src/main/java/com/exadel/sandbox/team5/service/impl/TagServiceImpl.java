package com.exadel.sandbox.team5.service.impl;

import com.exadel.sandbox.team5.dao.TagDAO;
import com.exadel.sandbox.team5.dto.TagDto;
import com.exadel.sandbox.team5.entity.Tag;
import com.exadel.sandbox.team5.mapper.MapperConverter;
import com.exadel.sandbox.team5.service.OrderService;
import com.exadel.sandbox.team5.service.TagService;
import com.exadel.sandbox.team5.service.convertor.Convertor;
import com.exadel.sandbox.team5.service.export.ExportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@Transactional
public class TagServiceImpl extends CRUDServiceDtoImpl<TagDAO, Tag, TagDto> implements TagService, ExportService {
    private final OrderService orderService;

    public TagServiceImpl(TagDAO tagDAO, MapperConverter mapper, OrderService orderService) {
        super(tagDAO, Tag.class, TagDto.class, mapper);
        this.orderService = orderService;
    }

    @Override
    public InputStream exportServiceCSV() {

        return Convertor.createCSVFile(orderService.getOrdersByTags(), "Tags", "Orders");
    }

    @Override
    public InputStream exportServiceXLSX() {

        return Convertor.createXLSXFile(orderService.getOrdersByTags(), "Tags", "Orders");
    }
}
