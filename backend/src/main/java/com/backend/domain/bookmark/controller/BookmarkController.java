package com.backend.domain.bookmark.controller;

import com.backend.domain.bookmark.dto.BookmarkRequestDto;
import com.backend.domain.bookmark.dto.BookmarkResponseDto;
import com.backend.domain.bookmark.service.BookmarkService;
import com.backend.global.reponse.ApiResponse;
import com.backend.global.reponse.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * POST /api/bookmarks
     * body: { "placeId": 10 }
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BookmarkResponseDto>> create(
            @Valid @RequestBody BookmarkRequestDto request) {
        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        Long memberId = 1L;

        BookmarkResponseDto response = bookmarkService.create(request, memberId);
        return ResponseEntity.status(ResponseCode.CREATED.getStatus())
                .body(ApiResponse.created(response));
    }

    /**
     * GET /api/bookmarks
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookmarkResponseDto>>> list() {
        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        Long memberId = 1L;
        List<BookmarkResponseDto> list = bookmarkService.getList(memberId);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    /**
     * DELETE /api/bookmarks/{bookmarkId}
     */
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<ApiResponse<Long>> delete(@PathVariable Long bookmarkId) {

        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        Long memberId = 1L;
        bookmarkService.delete(memberId, bookmarkId);
        return ResponseEntity.ok(ApiResponse.success(bookmarkId));
    }
}
