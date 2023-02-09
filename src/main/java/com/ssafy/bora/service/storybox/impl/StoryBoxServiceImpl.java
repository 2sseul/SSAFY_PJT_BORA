package com.ssafy.bora.service.storybox.impl;

import com.ssafy.bora.dto.stroybox.ReqStoryBoxDTO;
import com.ssafy.bora.dto.stroybox.ResStoryBoxDTO;
import com.ssafy.bora.entity.StoryBox;
import com.ssafy.bora.entity.User;
import com.ssafy.bora.repository.storybox.IStoryBoxRepository;
import com.ssafy.bora.repository.user.IUserRepository;
import com.ssafy.bora.service.storybox.IStoryBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryBoxServiceImpl implements IStoryBoxService {

    private final IStoryBoxRepository storyBoxRepository;
    private final IUserRepository userRepository;

    /**
     * {@inhreitDoc}
     */
    @Override
    public ResStoryBoxDTO createStoryBox(ReqStoryBoxDTO reqStoryBoxDTO) {
        User dj = userRepository.findById(reqStoryBoxDTO.getDjId()).get();

        // 이미 사연을 신청했는지 먼저 확인
        StoryBox hasStoryBox = storyBoxRepository.findByDjAndViewerIdAndIsDeleteFalse(dj, reqStoryBoxDTO
                .getViewerId());

        // 없다면 등록 가능
        if (hasStoryBox == null) {
            // 엔티티 값 설정
            StoryBox storyBox = StoryBox.builder()
                    .dj(dj)
                    .viewerId(reqStoryBoxDTO.getViewerId())
                    .title(reqStoryBoxDTO.getTitle())
                    .contents(reqStoryBoxDTO.getContents())
                    .regDateTime(reqStoryBoxDTO.getRegDateTime())
                    .build();

            // DB에 넣고 Response DTO로 변환
            StoryBox registeredStoryBox = storyBoxRepository.save(storyBox);
            ResStoryBoxDTO resStoryBoxDTO = ResStoryBoxDTO.convertEntityToResDTO(registeredStoryBox);
            return resStoryBoxDTO;
        }
        return null;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public List<ResStoryBoxDTO> findAllStoryBox(String djId) {
        // Entity List 조회
        User dj = userRepository.findById(djId).get();
        List<StoryBox> storyBoxList = storyBoxRepository.findByDjAndIsDeleteFalse(dj);

        if (storyBoxList != null && !storyBoxList.isEmpty()) {
            // Entity List를 DTO List로 변환
            List<ResStoryBoxDTO> resStoryBoxDtoList = new ArrayList<>();
            for (StoryBox storyBox : storyBoxList) {
                resStoryBoxDtoList.add(ResStoryBoxDTO.convertEntityToResDTO(storyBox));
            }
            return resStoryBoxDtoList;
        }
        return null;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public ResStoryBoxDTO findByDjIdAndStoryBoxId(String djId, int storyBoxId) {
        User dj = userRepository.findById(djId).get();
        StoryBox findStoryBox = storyBoxRepository.findByDjAndIdAndIsDeleteFalse(dj, storyBoxId);
        if (findStoryBox != null) {
            ResStoryBoxDTO resStoryBoxDTO = ResStoryBoxDTO.convertEntityToResDTO(findStoryBox);
            return resStoryBoxDTO;
        }
        return null;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public ResStoryBoxDTO deleteOneStoryBoxByDj(int storyBoxId) {
        StoryBox storyBox = storyBoxRepository.findById(storyBoxId);
        if (storyBox != null) {
            storyBox.deleteStoryBox();
            ResStoryBoxDTO resStoryBoxDTO = ResStoryBoxDTO.convertEntityToResDTO(storyBox);
            return resStoryBoxDTO;
        }
        return null;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public int deleteStoryBoxListByDj() {
        return 0;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public ResStoryBoxDTO findMyStoryBoxOfDj(String djId, String viewerId) {
        User dj = userRepository.findById(djId).get();
        StoryBox storyBox = storyBoxRepository.findByDjAndViewerIdAndIsDeleteFalse(dj, viewerId);

        if (storyBox != null) {
            ResStoryBoxDTO resStoryBoxDTO = ResStoryBoxDTO.convertEntityToResDTO(storyBox);
            return resStoryBoxDTO;
        }
        return null;
    }

    /**
     * {@inhreitDoc}
     */
    @Override
    public ResStoryBoxDTO updateStoryBox(ReqStoryBoxDTO updateStoryBoxDTO) {
        String djId = updateStoryBoxDTO.getDjId();
        String viewerId = updateStoryBoxDTO.getViewerId();

        User dj = userRepository.findById(djId).get();
        StoryBox storyBox = storyBoxRepository.findByDjAndViewerIdAndIsDeleteFalse(dj, viewerId);

        // 사연함 수정 가능
        if (storyBox != null) {
            storyBox.updateStoryBox(updateStoryBoxDTO);
            ResStoryBoxDTO resStoryBoxDTO = ResStoryBoxDTO.convertEntityToResDTO(storyBox);
            return resStoryBoxDTO;
        }
        return null;
    }
}