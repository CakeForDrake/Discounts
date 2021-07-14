package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.dto.CategoryDto;
import com.exadel.sandbox.team5.service.CategoryService;
import com.exadel.sandbox.team5.service.OrderService;
import com.exadel.sandbox.team5.service.export.ExportService;
import com.exadel.sandbox.team5.service.export.FileNameGenerator;
import com.exadel.sandbox.team5.util.CategoryWithTagWithoutIdCategory;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;
    private final ExportService exportService;
    private final OrderService orderService;
    private final FileNameGenerator fileNameGenerator;

    @GetMapping
    List<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @ApiOperation("Save category with tags")
    @PostMapping
    CategoryDto save(@RequestBody CategoryWithTagWithoutIdCategory categoryWithTagWithoutIdCategory) {
        return categoryService.save(categoryWithTagWithoutIdCategory);
    }

    @ApiOperation("Save category only")
    @PostMapping("/save")
    CategoryDto save(@RequestBody String categoryName) {
        return categoryService.save(categoryName);
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id, String category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setName(category);
        return categoryService.update(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @ApiOperation("Statistic of Orders By Categories")
    @GetMapping("/statistic/categories")
    public Map<String, String> getStatisticOrdersByCategories() {
        return orderService.getOrdersByCategories();
    }

    @GetMapping("/statistic/downloadCSVOrdersByCategories")
    public ResponseEntity getOrdersByCategoriesCSVFile() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileNameGenerator.csvFileNameGenerator("OrdersByCategories"))
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(exportService.exportServiceCSV(orderService.getOrdersByCategories(), "Categories", "Orders")));
    }

    @GetMapping("/statistic/downloadXLSXOrdersByCategories")
    public ResponseEntity getOrdersByCategoriesXLSXFile() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileNameGenerator.xlsxFileNameGenerator("OrdersByCategories"))
                .contentType(MediaType.parseMediaType("application/xlsx"))
                .body(new InputStreamResource(exportService.exportServiceXLSX(orderService.getOrdersByCategories(), "Categories", "Orders")));
    }
}
