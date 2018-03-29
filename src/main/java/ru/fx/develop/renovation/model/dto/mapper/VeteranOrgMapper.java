package ru.fx.develop.renovation.model.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.fx.develop.renovation.model.VeteranOrg;
import ru.fx.develop.renovation.model.dto.VeteranOrgDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class VeteranOrgMapper {

    public static List<VeteranOrgDTO> mapEntitiesIntoDTOs(List<VeteranOrg> veteranOrgList){
        return veteranOrgList.stream()
                .map(VeteranOrgMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static VeteranOrgDTO mapEntityIntoDTO(VeteranOrg veteranOrg){
        VeteranOrgDTO veteranOrgDTO = new VeteranOrgDTO();

        veteranOrgDTO.setVidPrava(veteranOrg.getVidPrava());
        veteranOrgDTO.setSqrOForm(veteranOrg.getSqrOForm());

        return veteranOrgDTO;
    }

    public static Page<VeteranOrgDTO> mapEntityPageIntoDTOPage(Pageable page, Page<VeteranOrg> source){
        List<VeteranOrgDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }
}


