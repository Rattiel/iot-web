package com.greplfa.web.domain.iot.widget.history.repository;

import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.iot.widget.history.HistoryWidget;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryWidgetRepository extends JpaRepository<HistoryWidget, Long> {
    Optional<HistoryWidget> findByIdAndOwner(Long id, Member owner);

    boolean existsByPart(Part part);

    @Query("SELECT w.part as part FROM HistoryWidget w WHERE w.owner = :owner AND w.type = :type")
    List<Part> findAllByOwnerAndType(Member owner, WidgetType type);
}
