package com.greplfa.web.domain.iot.device.repository;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.device.dto.DeviceOption;
import com.greplfa.web.domain.iot.device.dto.DevicePreview;
import com.greplfa.web.domain.iot.device.dto.DeviceUpdateFormData;
import com.greplfa.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByIdAndOwner(Long id, Member owner);

    Optional<DeviceUpdateFormData> findUpdateFormDataByIdAndOwner(Long id, Member owner);

    @Query("SELECT d.id as id, d.name as name FROM Device d WHERE d.owner = :owner AND d not in :deviceList")
    List<DeviceOption> findAllOptionByOwnerAndDeviceNotIn(Member owner, List<Device> deviceList);

    List<DeviceOption> findAllOptionByOwner(Member owner);

    List<DevicePreview> findAllPreviewByOwner(Member owner);

    Boolean existsByUuid(String uuid);
}
