package com.exadel.sandbox.team5.service.impl;

import com.exadel.sandbox.team5.dao.DiscountDAO;
import com.exadel.sandbox.team5.dao.ReviewDAO;
import com.exadel.sandbox.team5.dto.DiscountDto;
import com.exadel.sandbox.team5.dto.search.DiscountSearchCriteria;
import com.exadel.sandbox.team5.entity.Discount;
import com.exadel.sandbox.team5.mapper.MapperConverter;
import com.exadel.sandbox.team5.service.DiscountService;
import com.exadel.sandbox.team5.service.SnsService;
import com.exadel.sandbox.team5.util.Pair;
import com.exadel.sandbox.team5.util.ResultPage;
import com.exadel.sandbox.team5.util.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class DiscountServiceImpl extends CRUDServiceDtoImpl<DiscountDAO, Discount, DiscountDto> implements DiscountService {

    private final ReviewDAO reviewDAO;

    private SnsService snsService;

    public DiscountServiceImpl(DiscountDAO entityDao, MapperConverter mapper, ReviewDAO reviewDAO) {
        super(entityDao, Discount.class, DiscountDto.class, mapper);
        this.reviewDAO = reviewDAO;
    }

    @Override
    public ResultPage<DiscountDto> getAllByCriteria(SearchCriteria criteria) {
        Page<Discount> dis = entityDao.findAll(criteria.getPageRequest());
        ResultPage<DiscountDto> result = mapper.mapToPage(dis, DiscountDto.class);
        setRate(getRate(result.getContent()), result.getContent());
        return result;
    }

    @Override
    public DiscountDto getById(Long id) {
        DiscountDto discountDto = entityDao.findById(id)
                .map(discount -> mapper.map(discount, DiscountDto.class))
                .orElseThrow(NoSuchElementException::new);
        discountDto.setRate(reviewDAO.findRate(discountDto.getId()));
        return discountDto;
    }

    @Override
    public List<DiscountDto> getAll() {
        return getAllByCriteria(new SearchCriteria()).getContent();
    }

    @Override
    public DiscountDto save(DiscountDto discount) {
        if(discount.isNotifySubscribers()) snsService.sendToSubscribers(discount);
        Discount dis = mapper.map(discount, Discount.class);
        return mapper.map(entityDao.saveAndFlush(dis), DiscountDto.class);
    }

    @Override
    public DiscountDto update(DiscountDto discount) {
        return this.save(discount);
    }

    @Override
    public void delete(Long id) {
        entityDao.deleteById(id);
    }

    //fixme добавить стандартную сортировку по дате добавления после включения аудита
    @Override
    public ResultPage<DiscountDto> getByCriteria(DiscountSearchCriteria searchCriteria) {
        if (searchCriteria.isEmpty()) {
            return getAllByCriteria(searchCriteria);
        }
        var res = entityDao.findDiscountsByCriteria(searchCriteria.getSearchText(),
                searchCriteria.getTags(),
                searchCriteria.getLocationCriteria().getCountry(),
                searchCriteria.getLocationCriteria().getCities(),
                searchCriteria.getCompanies(),
                searchCriteria.getRate(),
                searchCriteria.getPageRequest());
        ResultPage<DiscountDto> result = mapper.mapToPage(res, DiscountDto.class);
        setRate(getRate(result.getContent()), result.getContent());
        if (searchCriteria.getOrders() != null && searchCriteria.getOrders().isEmpty()) {
            List<DiscountDto> sorted = new ArrayList<>(result.getContent());
            sorted.sort((o1, o2) -> (int) (o2.getRate() - o1.getRate()));
            return new ResultPage<>(sorted, result.getTotalElements());
        }
        return result;
    }

    private Map<Long, Double> getRate(List<DiscountDto> result) {
        Set<Long> discountIds = result.stream().map(DiscountDto::getId).collect(Collectors.toSet());
        return reviewDAO.getRateByDiscountId(discountIds).stream()
                .collect(Collectors.toMap(x -> Long.parseLong(x.getFirst()), y -> Double.parseDouble(y.getSecond())));
    }

    //реализовать сортировку по рейтингу
    private void setRate(Map<Long, Double> rateMap, List<DiscountDto> dtoList) {
        for (DiscountDto d : dtoList) {
            if (rateMap.get(d.getId()) == null) d.setRate(0.0);
            else d.setRate(rateMap.get(d.getId()));
        }
    }

    @Override
    public Map<String, String> getViewsByDiscounts() {
        return entityDao.getViewsByDiscounts().stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (o1, o2) -> o1, TreeMap::new));
    }

    @Override
    public void incrementViews(Long discountId) {
        entityDao.findById(discountId).orElseThrow(NoSuchElementException::new);
        entityDao.incrementViewsByDiscountId(discountId);
    }
}
