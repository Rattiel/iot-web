package com.greplfa.web.domain.iot.widget.part.repository;

import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.iot.widget.part.PartWidget;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartWidgetRepository extends JpaRepository<PartWidget, Long> {
    Optional<PartWidget> findByIdAndOwner(Long id, Member owner);

    boolean existsByPart(Part part);

    @Query("SELECT w.part as part FROM PartWidget w WHERE w.owner = :owner AND w.type = :type")
    List<Part> findAllByOwnerAndType(Member owner, WidgetType type);
}
