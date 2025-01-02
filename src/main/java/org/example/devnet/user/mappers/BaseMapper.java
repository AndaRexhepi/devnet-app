package org.example.devnet.user.mappers;


import java.util.List;

public interface BaseMapper<TDto, TEntity > {

    TDto toDto(TEntity entity);
    TEntity toEntity(TDto dto);

    List<TDto> toDtoList(List<TEntity> entityList);
}
