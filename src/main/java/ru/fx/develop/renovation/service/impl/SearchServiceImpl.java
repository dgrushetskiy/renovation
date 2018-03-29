package ru.fx.develop.renovation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.fx.develop.renovation.dao.HouseRepository;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.dto.HouseDTO;
import ru.fx.develop.renovation.model.dto.mapper.HouseMapper;
import ru.fx.develop.renovation.model.specifications.HouseSpecifications;
import ru.fx.develop.renovation.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
    private static final Logger LOG = LoggerFactory.getLogger(SearchServiceImpl.class);
    @Autowired
    private HouseRepository houseRepository;
    @Override
    public Page<HouseDTO> findBySearchTerm(String searchTerm, Pageable pageRequest) {
        LOG.info("finding house entities by search term: [] and page request: []", searchTerm,pageRequest);

        Page<House> searchResultPage = houseRepository.findAll(HouseSpecifications.mrOrAddressContainsIgnoreCase(searchTerm), pageRequest);
        LOG.info("Found {} house entries. Returned page {} contains {} house entries",
                searchResultPage.getTotalElements(),
                searchResultPage.getNumber(),
                searchResultPage.getNumberOfElements()
        );
        return HouseMapper.makePageDTO(pageRequest,searchResultPage);
    }
}
