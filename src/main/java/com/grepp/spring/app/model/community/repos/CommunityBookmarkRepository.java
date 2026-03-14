package com.grepp.spring.app.model.community.repos;

import com.grepp.spring.app.model.community.domain.CommunityBookmark;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityBookmarkRepository extends JpaRepository<CommunityBookmark, Long> {

    // 커뮤니티 현재 사용자의 활성화된 북마크가 존재하는지 확인
    Optional<CommunityBookmark> findByPost_PostIdAndMember_MemberIdAndMember_ActivatedTrue(Long postId, Long memberId);

    // 커뮤니티 북마크 활성화 여부
    boolean existsByPost_PostIdAndMember_MemberIdAndActivatedTrueAndMember_ActivatedTrue(Long postId, Long memberId);

    // 내가 북마크한 게시글 ID 목록 일괄 조회
    @Query("""
        SELECT b.post.postId FROM CommunityBookmark b 
        WHERE b.member.memberId = :memberId 
        AND b.post.postId IN :postIds 
        AND b.activated = true 
        AND b.member.activated = true
    """)
    Set<Long> findBookmarkedPostIdsByMemberId(@Param("memberId") Long memberId, @Param("postIds") List<Long> postIds);

    // 내가 북마크한 게시글 목록 조회 (최신순 정렬)
    List<CommunityBookmark> findByMember_MemberIdAndActivatedTrueAndMember_ActivatedTrueOrderByCreatedAtDesc(Long memberId);
}
