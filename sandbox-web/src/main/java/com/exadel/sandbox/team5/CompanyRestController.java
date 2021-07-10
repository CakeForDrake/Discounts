package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.dto.CompanyDto;
import com.exadel.sandbox.team5.service.CompanyService;
import com.exadel.sandbox.team5.service.ImageClientService;
import com.exadel.sandbox.team5.service.OrderService;
import com.exadel.sandbox.team5.service.export.ExportCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;
    private final OrderService orderService;
    private final ImageClientService imageService;
    private final ExportCompany exportCompany;

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @PostMapping
    public CompanyDto save(@RequestBody CompanyDto company, MultipartFile file) {
        company.setImageId(imageService.save(file));
        return companyService.save(company);
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable Long id, @RequestBody CompanyDto company) {
        company.setId(id);
        return companyService.update(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }

    @GetMapping("/{locationId}/companies")
    public List<CompanyDto> getCompaniesByLocation(@PathVariable Long locationId) {
        return companyService.getCompaniesByLocation(locationId);
    }

    @GetMapping("/statistic")
    public Map<String, String> getStatistic() {
        return orderService.getOrdersByCompanies();
    }

    @GetMapping("/statistic/downloadCSVOrdersByCompanies")
    public ResponseEntity getOrderByStatisticCSVFile(){
        String filename = "report_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()) + "_OrdersByCompanies.csv";
        InputStreamResource file = new InputStreamResource(exportCompany.ordersByCompaniesCSV());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @GetMapping("/statistic/downloadXLSXOrdersByCompanies")
    public ResponseEntity getOrderByStatisticXLSXFile(){
        String filename = "report_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime()) + "_OrdersByCompanies.xlsx";
        InputStreamResource file = new InputStreamResource(exportCompany.ordersByCompaniesXLSX());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/xlsx"))
                .body(file);
    }
}
