package ru.fx.develop.renovation.model.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.fx.develop.renovation.model.DisabledPeople;
import ru.fx.develop.renovation.model.dto.DisabledPeopleDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class DisabledPeopleMapper {

    public static List<DisabledPeopleDTO> mapEntitiesIntoDTOs(List<DisabledPeople> disabledPeopleList){
        return disabledPeopleList.stream()
                .map(DisabledPeopleMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static DisabledPeopleDTO mapEntityIntoDTO(DisabledPeople disabledPeople){
        DisabledPeopleDTO disabledPeopleDTO = new DisabledPeopleDTO();

        disabledPeopleDTO.setAddressFull(disabledPeople.getAddressFull());//Адрес
        disabledPeopleDTO.setGroupInvalid(disabledPeople.getGroupInvalid());//группа инвалидности
        disabledPeopleDTO.setArmchair(disabledPeople.isArmchair());//пользование кресло-коляской
        disabledPeopleDTO.setSingle(disabledPeople.isSingle());//одинокое проживание
        disabledPeopleDTO.setImprovement(disabledPeople.getImprovement());//улучшенные условия
        disabledPeopleDTO.setAdaptation(disabledPeople.getAdaptation());//приспособления
        disabledPeopleDTO.setTotals(disabledPeople.getTotals());

        return disabledPeopleDTO;
    }

    public static Page<DisabledPeopleDTO> mapEntityPageIntoDTOPage(Pageable page, Page<DisabledPeople> source){
        List<DisabledPeopleDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }
}
