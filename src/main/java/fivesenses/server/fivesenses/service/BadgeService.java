package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.BadgeRequestDto;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BadgeService {
    private final static
    String DIR_BEFORE = "images/badges_before/",
            DIR_AFTER = "images/badges_after/";

    private final S3Service s3Service;
    private final BadgeRepository badgeRepository;

    public List<Badge> findAll(){
        return badgeRepository.findAll();
    }
    public Badge findById(String id){
        return badgeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배지입니다."));
    }

    @Transactional
    public void createBadge(MultipartFile[] files, String dirName){
        Boolean isBefore;
        if(dirName.equals("before")) {
            isBefore = true;
            dirName = DIR_BEFORE;
        }
        else if(dirName.equals("after")) {
            isBefore = false;
            dirName = DIR_AFTER;
        }
        else throw new IllegalStateException("잘못된 dirName.");

        for (MultipartFile m : files){
            String imgUrl = s3Service.upload(m, dirName);

            String[] split = Objects.requireNonNull(m.getOriginalFilename()).split("_");
            Integer sequence = Integer.valueOf(Objects.requireNonNull(split[0]));

            Badge badge = Badge.builder()
                    .id(m.getOriginalFilename())
                    .imgUrl(imgUrl)
                    .seqNum(sequence)
                    .isBefore(isBefore)
                    .build();

            badgeRepository.save(badge);
        }
    }


    @Transactional
    public void deleteBadge(String id){
        Badge badge = findById(id);
        badgeRepository.delete(badge);
    }

    @Transactional
    public void updateBadge(BadgeRequestDto b){
        Badge badge = findById(b.getId());

        badge.update(b.getId(), b.getSeqNum(), b.getImgUrl(), b.getDescription(), b.getReqCondition(), b.getReqConditionShort(), b.getIsBefore(), b.getName());
    }

}
