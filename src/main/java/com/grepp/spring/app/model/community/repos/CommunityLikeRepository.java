package com.grepp.spring.app.model.community.repos;

import com.grepp.spring.app.model.community.domain.CommunityLike;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {

    // 커뮤니티 활성화된 좋아요 개수
    int countByPost_PostIdAndActivatedTrueAndMember_ActivatedTrue(Long postId);

    // 커뮤니티 좋아요 활성화 여부
    boolean existsByPost_PostIdAndMember_MemberIdAndActivatedTrueAndMember_ActivatedTrue(Long postId, Long memberId);

    // 커뮤니티 현재 사용자의 활성화된 좋아요가 존재하는지 확인
    Optional<CommunityLike> findByPost_PostIdAndMember_MemberIdAndMember_ActivatedTrue(Long postId, Long memberId);

    // 내가 좋아요한 게시글 ID 목록 일괄 조회
    @Query("""
        SELECT l.post.postId FROM CommunityLike l 
        WHERE l.member.memberId = :memberId 
        AND l.post.postId IN :postIds 
        AND l.activated = true 
        AND l.member.activated = true
    """)
    Set<Long> findLikedPostIdsByMemberId(@Param("memberId") Long memberId, @Param("postIds") List<Long> postIds);

    // 커뮤니티 활성화된 좋아야 개수 (createdat이 이번달이여야함)
    @Query("""
            SELECT COUNT(c) FROM CommunityLike c
            WHERE c.member.memberId = :memberId
            AND c.activated = true AND c.createdAt BETWEEN :start AND :end
            """)
    int countAchievedLike(Long memberId, LocalDateTime start, LocalDateTime end);
}
