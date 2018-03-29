package ru.fx.develop.renovation.model.dto.mapper;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.House;
import ru.fx.develop.renovation.model.PrimaryRight;
import ru.fx.develop.renovation.model.VeteranOrg;
import ru.fx.develop.renovation.model.dto.DisabledPeopleDTO;
import ru.fx.develop.renovation.model.dto.HouseDTO;
import ru.fx.develop.renovation.model.dto.PrimaryRightDTO;
import ru.fx.develop.renovation.model.dto.VeteranOrgDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class HouseMapper {

    public static List<HouseDTO> makeDTOs(List<House> houses){
        return houses.stream().map(HouseMapper::makeDTO).collect(Collectors.toList());
    }

    public static HouseDTO makeDTO(House house){
        HouseDTO dto = new HouseDTO();
        dto.setUnom(house.getUnom());
        dto.setMr(house.getMr());
        dto.setAddress(house.getAddress());

        for (PrimaryRight primaryRight:house.getPrimaryRightSet() ) {
            PrimaryRightDTO primaryRightDTO = new PrimaryRightDTO();
            primaryRightDTO.setHouseDTO(dto);
        }

        for (VeteranOrg veteranOrg: house.getVeteranOrgSet()) {
            VeteranOrgDTO veteranOrgDTO = new VeteranOrgDTO();
            veteranOrgDTO.setHouseDTO(dto);
        }

        for (DisabledPeople disabledPeople: house.getDisabledPeopleSet()) {
            DisabledPeopleDTO disabledPeopleDTO = new DisabledPeopleDTO();
            disabledPeopleDTO.setHouseDto(dto);
        }
        return dto;
    }

    public static Page<HouseDTO> makePageDTO(Pageable page, Page<House> source){
        List<HouseDTO> dtos = makeDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }
}
