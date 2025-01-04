package org.example.devnet.api_v1.community;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.example.devnet.community.dtos.CommunityDto;
import org.example.devnet.community.services.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityApiController {
    public final CommunityService communityService;

    @GetMapping
    public List<CommunityDto> findAll() {
        return communityService.findAll();
    }

    @GetMapping("/{id}")
    public CommunityDto findById(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        return communityService.findById(id);
    }

    @GetMapping("/default")
    public CommunityDto getDefaultUser() {
        return new CommunityDto();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public CommunityDto add(@Valid @RequestBody CommunityDto communityDto) {
        return communityService.add(communityDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public CommunityDto modify(@Valid @RequestBody CommunityDto communityDto, @PathVariable Long id) {
        return communityService.modify(communityDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@Valid @PositiveOrZero(message = "Id cannot be negative") @PathVariable Long id) {
        communityService.delete(id);
    }



}
