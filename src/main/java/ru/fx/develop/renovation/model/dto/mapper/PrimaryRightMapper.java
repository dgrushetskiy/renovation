package ru.fx.develop.renovation.model.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.model.dto.PrimaryRightDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class PrimaryRightMapper {

    public static List<PrimaryRightDTO> mapEntitiesIntoDTOs(List<PrimaryRight> entities){
        return entities.stream().map(PrimaryRightMapper::mapEntityIntoDTO).collect(Collectors.toList());
    }

    public static PrimaryRightDTO mapEntityIntoDTO(PrimaryRight entity){
        PrimaryRightDTO dto = new PrimaryRightDTO();
       // dto.setHouseDTO(entity.getHouse());
        dto.setVidPrava(entity.getVidPrava());
        dto.setSqrOForm(entity.getSqrOForm());

        return dto;
    }

    public static Page<PrimaryRightDTO> mapEntityPageIntoDTOPage(Pageable page, Page<PrimaryRight> source){
        List<PrimaryRightDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }
}
