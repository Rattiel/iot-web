package com.greplfa.web.domain.iot.part.helper;

import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface PartMessageSender {
    void create(long deviceId, Part part, MemberDetails memberDetails);

    void update(long deviceId, Part part, MemberDetails memberDetails);

    void delete(long deviceId, long partId, MemberDetails memberDetails);
}
