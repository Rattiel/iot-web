package com.greplfa.web.domain.iot.widget.common.repository;

import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findAllByOwner(Member owner);
}
