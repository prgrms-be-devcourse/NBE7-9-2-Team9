package com.backend.domain.plan.entity;

import com.backend.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(
        name = "plan_member", // 테이블 이름
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UC_MEMBER_PLAN",
                        columnNames = {"member_id", "plan_id"}
                )
        }
)
public class PlanMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Plan plan;

    private LocalDateTime addDate;
    private LocalDateTime updateDate;

    @ColumnDefault("0")
    private int isConfirmed;


    public PlanMember inviteAccept(){
        this.isConfirmed = 1;
        return this;
    }

    public PlanMember inviteDeny(){
        this.isConfirmed = -1;
        return this;
    }

    public boolean isConfirmed(){
        return this.isConfirmed == 1;
    }

    public String inviteStatusString(){
        if(isConfirmed == -1){
            return "거절함";
        }
        if(isConfirmed == 0){
            return "초대됨";
        }
        if(isConfirmed == 1){
            return "승낙함";
        }

        return "값이 올바르지 않습니다.";
    }

}
