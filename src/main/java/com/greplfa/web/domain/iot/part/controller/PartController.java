package com.greplfa.web.domain.iot.part.controller;

import com.greplfa.web.domain.iot.part.dto.PartActionForm;
import com.greplfa.web.domain.iot.part.exception.PartNotFoundException;
import com.greplfa.web.domain.iot.part.service.PartService;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/iot/part")
@RestController
public class PartController {
    private final PartService partService;

    @PostMapping("/{partId}/action")
    public ResponseEntity<?> requestAction(
            @PathVariable Long partId,
            @Valid @ModelAttribute PartActionForm form,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        try {
            partService.action(partId, form.getOption(), memberDetails);
        } catch (PartNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
