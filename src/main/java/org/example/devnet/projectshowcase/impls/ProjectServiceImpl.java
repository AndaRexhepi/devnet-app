package org.example.devnet.projectshowcase.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.mappers.ProjectMapper;
import org.example.devnet.projectshowcase.repositories.ProjectRepository;
import org.example.devnet.projectshowcase.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    public final ProjectRepository projectRepository;
    public final ProjectMapper projectMapper;

    @Override
    public List<ProjectDto> findAll() {
        var projects = projectRepository.findAll();
        return projectMapper.toDtoList(projects);
    }

    @Override
    public ProjectDto findById(Long id) {
        if (projectRepository.findById(id).isPresent()){
            return projectMapper.toDto(projectRepository.findById(id).get());
        }else {
            throw new EntityNotFoundException();
        }

    }

    @Override
    public ProjectDto add(ProjectDto projectDto) {
        var project = projectMapper.toEntity(projectDto);
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDto modify(ProjectDto projectDto, Long id) {
        if (projectRepository.findById(id).isPresent()){
            var entity = projectMapper.toEntity(projectDto);
            projectRepository.save(entity);
            return projectMapper.toDto(entity);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (projectRepository.findById(id).isPresent()){
            projectRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException();
        }

    }
}
