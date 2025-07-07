package code.hotelservice.hotelservice.service.mapper;

import code.hotelservice.hotelservice.dto.RoomDto;
import code.hotelservice.hotelservice.model.Rooms;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper roomMapper= Mappers.getMapper(RoomMapper.class);

    RoomDto toDto(Rooms Room);
    Rooms toEntity(RoomDto roomDto);
    List<RoomDto> toDtoList(List<Rooms> rooms);

    List<Rooms> toEntityList (List<RoomDto> roomDtos);
}
