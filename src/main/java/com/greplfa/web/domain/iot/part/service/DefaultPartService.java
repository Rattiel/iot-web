package com.greplfa.web.domain.iot.part.service;

import com.greplfa.iot.sdk.device.part.PartMethod;
import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.part.exception.PartNotFoundException;
import com.greplfa.web.domain.iot.part.helper.PartMessageSender;
import com.greplfa.web.domain.iot.part.repository.PartRepository;
import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultPartService implements PartService {
    private final PartRepository partRepository;

    private final PartMessageSender partMessageSender;

    @Transactional
    @Override
    public void action(long partId, Object option, MemberDetails memberDetails) {
        Member member = getMember(memberDetails);

        Part part = findPart(partId, member);

        PartMethod partMethod = PartMethod.builder()
                .uuid(part.getUuid())
                .label(part.getLabel())
                .build();

        partMethod.action(option, memberDetails.getId());

        //TODO 임시 테스트 코드
        part.feedback(option);
        partMessageSender.update(part.getDevice().getId(), part, memberDetails);
    }

    private Part findPart(long id, Member owner) {
        return partRepository.findByIdAndOwner(id, owner)
                .orElseThrow(PartNotFoundException::new);
    }


    private Member getMember(MemberDetails memberDetails) {
        return Member.from(memberDetails);
    }
}
