package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "rs_event")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RsEventEntity {
   @Id
   @GeneratedValue
    private Integer id;
   @Column(name = "name")
    private String eventName;
    private String keyWord;
//    private int userId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    public RsEventEntity merge(RsEventEntity rsEventEntity) {
        if (rsEventEntity.eventName != null) {
            this.eventName = rsEventEntity.eventName;
        }

        if (rsEventEntity.keyWord != null) {
            this.keyWord = rsEventEntity.keyWord;
        }

        return this;
    }

    public static RsEventEntity from(RsEvent rsEvent) {
        return RsEventEntity.builder()
                .id(rsEvent.getUserId())
                .eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord())
                .user(UserEntity.builder()
                        .id(rsEvent.getUserId())
                        .build())
                .build();
    }
}


