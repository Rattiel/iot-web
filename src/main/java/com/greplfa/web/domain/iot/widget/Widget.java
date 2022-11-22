package com.greplfa.web.domain.iot.widget;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greplfa.web.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false ,nullable = false, updatable = false, unique = true)
    protected Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    protected Member owner;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    protected WidgetType type;
}
