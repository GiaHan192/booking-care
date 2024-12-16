package com.company.myweb.service.interfaces;

import com.company.myweb.dto.PostDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.payload.request.CreatePostRequest;
import org.springframework.data.domain.Pageable;

public interface IPostService {
    ApiPage<PostDTO> getAllPost(String query, Pageable pageable);

    PostDTO getSinglePost(Long id);

    Boolean createPost(CreatePostRequest request);

    Boolean updatePost(Long id, CreatePostRequest request);

    Boolean deletePost(Long id);
}
