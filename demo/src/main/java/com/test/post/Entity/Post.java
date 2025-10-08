package com.test.post.Entity;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Gender;
import com.test.Member.entity.Member;
import com.test.common.persistance.SoftDeleteEntity;
import com.test.matching.entity.Matching;
import com.test.post.dto.PostStatus;
import com.test.post.dto.PostUpdateReqDto;
import com.test.post.exception.PostUpdateNotAllowedException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "post")
public class Post extends SoftDeleteEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    private String title;

    @Enumerated(EnumType.STRING)
    private AssistanceType assistanceType;

    @Embedded
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private Collage collage;

    @Column(columnDefinition = "TEXT", length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus postStatus;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private DisabilityType disabilityType;

    private Integer age;

    @Embedded
    private AssistanceTime assistanceTime;

    // orphanRemoval 부모와 연결이 끊기면 자동삭제
//    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
//    private final List<Matching> matching = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<PostLike> postLikes = new ArrayList<>();



    public void validateUpdateBy(Member author) {
        if (!this.author.equals(author)) {
            throw PostUpdateNotAllowedException.EXCEPTION;
        }
    }


    public void updatePost(PostUpdateReqDto postUpdateReqDto){
        // 일정 값 생성
        Schedule updateSchedule = new Schedule(
            postUpdateReqDto.startDate(),
            postUpdateReqDto.endDate(),
            ScheduleType.schFromValue(postUpdateReqDto.scheduleType()),
            postUpdateReqDto.scheduleDetails()
        );
        // 봉사시간 값 생성
        AssistanceTime updateAssistanceTime = new AssistanceTime(
            postUpdateReqDto.assistanceStartTime(),
            postUpdateReqDto.assistanceEndTime()
        );

        // 엔티티 필드 갱신
        this.title = postUpdateReqDto.title();
        this.assistanceType = AssistanceType.assFromValue(postUpdateReqDto.assistanceType());
        this.schedule = updateSchedule;
        this.collage = postUpdateReqDto.collage();
        this.content = postUpdateReqDto.content();
        this.assistanceTime = updateAssistanceTime;
    }
}
