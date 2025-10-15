package com.backend.domain.bookmark.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"place_id", "member_id"})
}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", nullable = false)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name="create_date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="delete_date")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public static Bookmark create(Member member, Place place) {
        Bookmark b = new Bookmark();
        b.setMember(member);
        b.setPlace(place);
        // createdAt는 @PrePersist에서 자동 설정됩니다.
        return b;
    }

}
