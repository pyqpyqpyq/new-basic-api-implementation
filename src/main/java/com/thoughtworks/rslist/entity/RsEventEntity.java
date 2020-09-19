package com.thoughtworks.rslist.entity;

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
    private int userId;
}
