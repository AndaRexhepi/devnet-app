package org.example.devnet.projectshowcase.mappers;

import org.example.devnet.user.mappers.BaseMapper;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.models.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<ProjectDto, Project> {
    ProjectDto toDto(Project project);
}
