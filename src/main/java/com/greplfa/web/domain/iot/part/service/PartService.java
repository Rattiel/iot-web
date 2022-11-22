package com.greplfa.web.domain.iot.part.service;

import com.greplfa.web.domain.member.dto.MemberDetails;

public interface PartService {
    void action(long partId, Object option, MemberDetails memberDetails);
}
