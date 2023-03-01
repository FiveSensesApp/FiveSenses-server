package fivesenses.server.fivesenses.domain.badge.service;

import fivesenses.server.fivesenses.common.service.S3Service;
import fivesenses.server.fivesenses.domain.badge.dto.BadgeRequestDto;
import fivesenses.server.fivesenses.domain.badge.enitity.Badge;
import fivesenses.server.fivesenses.domain.badge.repository.BadgeRepository;
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
    private final static String DIR_PATH_BEFORE = "images/badges_before/";
    private final static String DIR_PATH_AFTER = "images/badges_after/";
    private final static String DIR_NAME_BEFORE = "before";
    private final static String DIR_NAME_AFTER = "after";

    private final S3Service s3Service;
    private final BadgeRepository badgeRepository;

    private Boolean isBefore = false;
    private String dirPath = "";

    public List<Badge> findAll() {
        return badgeRepository.findAll();
    }

    public Badge findById(String id) {
        return badgeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배지입니다."));
    }

    @Transactional
    public void createBadge(MultipartFile[] files, String dirName) {
        setDirPath(dirName);
        saveBadge(files);
    }

    private void saveBadge(MultipartFile[] files) {
        for (MultipartFile m : files) {
            String imgUrl = s3Service.upload(m, dirPath);
            Integer sequence = extractSequence(m);

            Badge badge = Badge.builder()
                    .id(m.getOriginalFilename())
                    .imgUrl(imgUrl)
                    .seqNum(sequence)
                    .isBefore(isBefore)
                    .build();

            badgeRepository.save(badge);
        }
    }

    private Integer extractSequence(MultipartFile m) {
        String[] split = Objects.requireNonNull(m.getOriginalFilename()).split("_");
        Integer sequence = Integer.valueOf(Objects.requireNonNull(split[0]));
        return sequence;
    }

    private void setDirPath(String dirName) {
        if (!dirName.equals(DIR_NAME_BEFORE) && !dirName.equals(DIR_NAME_AFTER)) {
            throw new IllegalStateException("잘못된 dirName.");
        }
        if (dirName.equals(DIR_NAME_BEFORE)) {
            isBefore = true;
            dirPath = DIR_PATH_BEFORE;
        }
        if (dirName.equals(DIR_NAME_AFTER)) {
            isBefore = false;
            dirPath = DIR_PATH_AFTER;
        }
    }

    @Transactional
    public void deleteBadge(String id) {
        Badge badge = findById(id);
        badgeRepository.delete(badge);
    }

    @Transactional
    public void updateBadge(BadgeRequestDto b) {
        Badge badge = findById(b.getId());

        badge.update(b.getId(), b.getSeqNum(), b.getImgUrl(), b.getDescription(), b.getReqCondition(), b.getReqConditionShort(), b.getIsBefore(), b.getName());
    }
}
