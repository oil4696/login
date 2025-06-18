package com.korit.authstudy.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members_tb")
public class Member {

    // entity는 db에 연경된거기 때문에 컬럼명을 맞춰주고

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement 세팅
    @Column(name = "members_id")
    private Integer id;

    private String memberName;
    private String password;
    private String name;
    private String email;
}
