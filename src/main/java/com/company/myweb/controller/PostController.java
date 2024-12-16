package com.company.myweb.controller;

import com.company.myweb.dto.PostDTO;
import com.company.myweb.entity.common.ApiPage;
import com.company.myweb.entity.common.ApiResponse;
import com.company.myweb.payload.request.CreatePostRequest;
import com.company.myweb.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final IPostService postService;

    @Autowired
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    // Get all posts with optional query and pagination
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<PostDTO>>> getAllPosts(
            @RequestParam(value = "query", required = false) String query,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(postService.getAllPost(query, pageable)));
    }

    // Get a single post by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getSinglePost(@PathVariable Long id) {
        PostDTO post = postService.getSinglePost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // Create a new post
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createPost(@RequestBody CreatePostRequest request) {
        Boolean isCreated = postService.createPost(request);
        if (isCreated) {
            return ResponseEntity.ok(ApiResponse.success("Tạo mới bài viết thành công"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.failed("Tạo mới bài viết thất bại"));
    }

    // Update an existing post by ID
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updatePost(@PathVariable Long id, @RequestBody CreatePostRequest request) {
        Boolean isUpdated = postService.updatePost(id, request);
        if (isUpdated) {
            return ResponseEntity.ok(ApiResponse.success("CẬp nhật bài viết thành công"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.failed("Cập nhật bài viết thất bại"));
    }

    // Delete a post by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long id) {
        Boolean isDeleted = postService.deletePost(id);
        if (isDeleted) {
            return ResponseEntity.ok(ApiResponse.success("Xóa bài viết thành công"));
        }
        return ResponseEntity.badRequest().body(ApiResponse.failed("Xóa bài viết thất bại"));
    }
}
