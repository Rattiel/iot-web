package com.greplfa.web.domain.iot.part.repository;

import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.part.dto.PartOption;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByIdAndOwner(Long id, Member owner);

    Optional<Part> findByUuid(String uuid);

    @Query("SELECT p.id as id, p.label as label FROM Part p WHERE p.owner = :owner AND p not in :parts")
    List<PartOption> findAllOptionByOwnerAndPartNotIn(Member owner, List<Part> parts);

    List<PartOption> findAllOptionByOwner(Member owner);
}
