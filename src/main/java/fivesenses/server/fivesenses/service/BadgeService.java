package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.BadgeRequestDto;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;

    public Badge findById(String id){
        return badgeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배지입니다."));
    }
    @Transactional
    public String createBadge(BadgeRequestDto b){
        Badge badge = Badge.builder()
                .id(b.getId())
                .imgUrl(b.getImgUrl())
                .build();

        return badgeRepository.save(badge).getId();
    }

    @Transactional
    public void deleteBadge(String id){
        Badge badge = findById(id);
        badgeRepository.delete(badge);
    }

    @Transactional
    public void updateBadge(BadgeRequestDto b){
        Badge badge = findById(b.getId());

        badge.update(b.getId(), b.getImgUrl());
    }

}
