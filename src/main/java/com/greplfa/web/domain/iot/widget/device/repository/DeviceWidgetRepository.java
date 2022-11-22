package com.greplfa.web.domain.iot.widget.device.repository;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.iot.widget.device.DeviceWidget;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceWidgetRepository extends JpaRepository<DeviceWidget, Long> {
    boolean existsByDevice(Device device);

    Optional<DeviceWidget> findByIdAndOwner(Long id, Member owner);

    @Query("SELECT w.device as device FROM DeviceWidget w WHERE w.owner = :owner AND w.type = :type")
    List<Device> findAllByOwnerAndType(Member owner, WidgetType type);
}
