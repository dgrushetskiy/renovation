package ru.fx.develop.renovation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.fx.develop.renovation.model.dto.HouseDTO;

public interface SearchService {

    Page<HouseDTO> findBySearchTerm(String searchTerm, Pageable pageable);
}
