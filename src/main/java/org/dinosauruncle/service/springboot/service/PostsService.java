package org.dinosauruncle.service.springboot.service;

import lombok.RequiredArgsConstructor;
import org.dinosauruncle.service.springboot.domain.posts.Posts;
import org.dinosauruncle.service.springboot.domain.posts.PostsRepository;
import org.dinosauruncle.service.springboot.web.dto.PostsResponseDto;
import org.dinosauruncle.service.springboot.web.dto.PostsSaveRequestDto;
import org.dinosauruncle.service.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException
                        ("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}