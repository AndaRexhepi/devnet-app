package org.example.devnet.reviews.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.devnet.reviews.dtos.ReviewDto;
import org.example.devnet.reviews.mappers.ReviewMapper;
import org.example.devnet.reviews.repositories.ReviewRepository;
import org.example.devnet.reviews.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    public final ReviewRepository reviewRepository;
    public final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDto> findAll() {
        var reviews = reviewRepository.findAll();
        return reviewMapper.toDtoList(reviews);
    }

    @Override
    public ReviewDto findById(Long id) {
        if (reviewRepository.findById(id).isPresent()){
            return reviewMapper.toDto(reviewRepository.findById(id).get());
        }else{
            throw new EntityNotFoundException();
        }

    }

    @Override
    public ReviewDto add(ReviewDto reviewDto) {
        var entity = reviewMapper.toEntity(reviewDto);
        reviewRepository.save(entity);
        return reviewMapper.toDto(entity);
    }

    @Override
    public ReviewDto modify(ReviewDto reviewDto, Long id) {
        if (reviewRepository.findById(id).isPresent()){
            var entity = reviewMapper.toEntity(reviewDto);
            var savedEntity = reviewRepository.save(entity);
            return reviewMapper.toDto(savedEntity);
        }else {
            throw new EntityNotFoundException();
        }
    }


    @Override
    public void delete(Long id) {
        if (reviewRepository.findById(id).isPresent()){
            reviewRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException();
        }

    }
}
