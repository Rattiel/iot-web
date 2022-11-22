package com.greplfa.web.domain.iot.part.hander;

import com.greplfa.iot.sdk.device.part.PartFeedback;
import com.greplfa.iot.sdk.device.part.PartUpdatedCallBack;
import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.part.helper.PartMessageSender;
import com.greplfa.web.domain.iot.part.repository.PartRepository;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DefaultPartUpdatedCallBack implements PartUpdatedCallBack {
    private final PartRepository partRepository;

    private final PartMessageSender partMessageSender;

    @Transactional
    @Override
    public void execute(PartFeedback feedback) {
        Optional<Part> partOptional = partRepository.findByUuid(feedback.getChannelId());

        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            part.feedback(feedback.getData());
            partMessageSender.update(part.getDevice().getId(), part, MemberDetails.from(part.getOwner()));
        }
    }
}

