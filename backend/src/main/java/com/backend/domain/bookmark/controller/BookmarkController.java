package com.backend.domain.bookmark.controller;

import com.backend.domain.bookmark.dto.BookmarkRequestDto;
import com.backend.domain.bookmark.dto.BookmarkResponseDto;
import com.backend.domain.bookmark.service.BookmarkService;
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
     * body: { "memberId": 1, "placeId": 10 }
     */
    @PostMapping
    public ResponseEntity<BookmarkResponseDto> create(@RequestBody BookmarkRequestDto request) {
        BookmarkResponseDto response = bookmarkService.create(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/bookmarks?memberId=1
     */
    @GetMapping
    public ResponseEntity<List<BookmarkResponseDto>> list(@RequestParam Long memberId) {
        List<BookmarkResponseDto> list = bookmarkService.getList(memberId);
        return ResponseEntity.ok(list);
    }

    /**
     * DELETE /api/bookmarks/{bookmarkId}?memberId=1
     */
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> delete(@PathVariable Long bookmarkId,
                                       @RequestParam Long memberId) {
        bookmarkService.delete(memberId, bookmarkId);
        return ResponseEntity.noContent().build();
    }
}
