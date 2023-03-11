package com.neoris.user_flow_api.mapper;

import com.neoris.user_flow_api.domain.Movement;
import com.neoris.user_flow_api.dto.MovementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovementMapper {

  MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

  MovementDTO movementToMovementDTO(Movement movement);

  Movement movementDTOtoMovement(MovementDTO movementDTO);
}
