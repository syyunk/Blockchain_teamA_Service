package com.ateam.popserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@ToString(exclude = {"member"})
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pnum")
    private Long pnum;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mnum", nullable=false)
    private Member member;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "price", nullable=false)
    private Integer price;

    @Column(name = "thumb")
    private String thumb;

    @Column(name ="detail")
    private String detail;
    
    @Column(name ="achieved")
    private int achieved;
}
