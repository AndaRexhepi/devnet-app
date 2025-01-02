package org.example.devnet.api_v1.projectshowcase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.projectshowcase.dtos.ProjectDto;
import org.example.devnet.projectshowcase.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectApiController {
    public final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ProjectDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return projectService.findById(id);
    }

    @GetMapping("/default")
    public ProjectDto getDefaultUser() {
        return new ProjectDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ProjectDto add(@Valid @RequestBody ProjectDto projectDto) {
        return projectService.add(projectDto);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ProjectDto modify(@Valid @RequestBody ProjectDto projectDto) {
        return projectService.modify(projectDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        projectService.delete(id);
    }



}
