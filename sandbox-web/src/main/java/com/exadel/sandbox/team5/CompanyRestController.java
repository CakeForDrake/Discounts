package com.exadel.sandbox.team5;

import com.exadel.sandbox.team5.dto.CompanyDto;
import com.exadel.sandbox.team5.service.CompanyService;
import com.exadel.sandbox.team5.service.ImageClientService;
import com.exadel.sandbox.team5.service.OrderService;
import com.exadel.sandbox.team5.service.export.ExportService;
import com.exadel.sandbox.team5.service.export.FileNameGenerator;
import com.exadel.sandbox.team5.util.CompanySearchCriteria;
import com.exadel.sandbox.team5.util.ResultPage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;
    private final OrderService orderService;
    private final ImageClientService imageService;
    private final ExportService exportService;
    private final FileNameGenerator fileNameGenerator;


    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR')")
    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR')")
    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @PostMapping
    public CompanyDto save(@RequestBody CompanyDto company) {
        return companyService.save(company);
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable Long id, @RequestBody CompanyDto company) {
        company.setId(id);
        var snapshotCompanyFromDB = companyService.getById(id);
        company.getAddresses().addAll(snapshotCompanyFromDB.getAddresses());
        return companyService.update(company);
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @PutMapping("/{id}/uploadImage")
    public CompanyDto updateImage(@PathVariable Long id, @RequestBody MultipartFile file) {
        CompanyDto company = companyService.getById(id);
        company.setImageId(imageService.save(file));
        return companyService.update(company);
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }

    //fixme is need add pagination here? or unite with search method?
    @GetMapping("/{locationId}/companies")
    public List<CompanyDto> getCompaniesByLocation(@PathVariable Long locationId) {
        return companyService.getCompaniesByLocation(locationId);
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @GetMapping("/statistic/orders")
    public Map<String, String> getStatisticByOrders() {
        return orderService.getOrdersByCompanies();
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @GetMapping("/statistic/downloadCSVOrdersByCompanies")
    public ResponseEntity getOrdersByCompaniesCSVFile() {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileNameGenerator.csvFileNameGenerator("OrdersByCompanies"))
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(exportService.exportServiceCSV(orderService.getOrdersByCompanies(), "Companies", "Orders")));
    }

    @PreAuthorize("hasAnyAuthority('MODERATOR')")
    @GetMapping("/statistic/downloadXLSXOrdersByCompanies")
    public ResponseEntity getOrdersByCompaniesXLSXFile() {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileNameGenerator.xlsxFileNameGenerator("OrdersByCompanies"))
                .contentType(MediaType.parseMediaType("application/xlsx"))
                .body(new InputStreamResource(exportService.exportServiceXLSX(orderService.getOrdersByCompanies(), "Companies", "Orders")));
    }

    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR')")
    @PostMapping("/search")
    public ResultPage<CompanyDto> search(@RequestBody CompanySearchCriteria criteria) {
        return companyService.getByCriteria(criteria);
    }
}
