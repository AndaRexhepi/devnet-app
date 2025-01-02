package org.example.devnet.reviews.mappers;

import org.example.devnet.user.mappers.BaseMapper;
import org.example.devnet.reviews.dtos.ReviewDto;
import org.example.devnet.reviews.models.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<ReviewDto, Review> {

    ReviewDto toDto(Review entity);
}
