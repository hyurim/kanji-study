package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.dto.KunSentenceDto;
import com.hyuri.kanji_study.dto.KunyomiDto;
import com.hyuri.kanji_study.dto.OnSentenceDto;
import com.hyuri.kanji_study.dto.OnyomiDto;
import com.hyuri.kanji_study.dto.UserDto;
import com.hyuri.kanji_study.entity.KanjiEntity;
import com.hyuri.kanji_study.entity.KunSentenceEntity;
import com.hyuri.kanji_study.entity.KunyomiEntity;
import com.hyuri.kanji_study.entity.OnSentenceEntity;
import com.hyuri.kanji_study.entity.OnyomiEntity;
import com.hyuri.kanji_study.entity.UserEntity;
import com.hyuri.kanji_study.repository.KanjiRepository;
import com.hyuri.kanji_study.repository.KunSentenceRepository;
import com.hyuri.kanji_study.repository.KunyomiRepository;
import com.hyuri.kanji_study.repository.OnSentenceRepository;
import com.hyuri.kanji_study.repository.OnyomiRepository;
import com.hyuri.kanji_study.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KanjiServiceImpl implements KanjiService {

    private final KanjiRepository kanjiRepo;
    private final KunyomiRepository kunRepo;
    private final KunSentenceRepository kunSentRepo;
    private final OnyomiRepository onRepo;
    private final OnSentenceRepository onSentRepo;
    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    // kanji
    @Override
    public KanjiDto readByKanjiId(Long id) {
        KanjiEntity k = kanjiRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kanji not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KanjiDto(
                k.getId(),
                k.getGlyph(),
                k.getMeaning(),
                k.getReading(),
                k.getKunyomi(),
                k.getOnyomi()
        );
    }
    @Override
    public List<KanjiDto> getKanjiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KanjiEntity> entityList = kanjiRepo.findAll(sort);

        // entity 데이터를 dto 데이터로 옮겨담기
        List<KanjiDto> dtoList = new ArrayList<>();
        for (KanjiEntity entity : entityList) {
            KanjiDto dto = KanjiDto.builder()
                    .id(entity.getId())
                    .glyph(entity.getGlyph())
                    .meaning(entity.getMeaning())
                    .reading(entity.getReading())
                    .kunyomi(entity.getKunyomi())
                    .onyomi(entity.getOnyomi())
                    .build();
            dtoList.add(dto);
            log.debug("DTO = {}", dto);
        }

        // dto 리턴
        return dtoList;
    }


    // kunyomi
    @Override
    public KunyomiDto readByKunyomiId(Long id) {
        KunyomiEntity ky = kunRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kunyomi not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KunyomiDto(
                ky.getId(),
                ky.getKanji().getId(),
                ky.getKunGlyph(),
                ky.getKunKana(),
                ky.getKunMeaning()
        );
    }

    @Override
    public List<KunyomiDto> getKunyomiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KunyomiEntity> entityList = kunRepo.findAll(sort);

        List<KunyomiDto> dtoList = new ArrayList<>();
        for (KunyomiEntity entity : entityList) {
            KunyomiDto dto = KunyomiDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .kunGlyph(entity.getKunGlyph())
                    .kunKana(entity.getKunKana())
                    .kunMeaning(entity.getKunMeaning())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // kunSentence
    @Override
    public KunSentenceDto readByKunSenId(Long id) {
        KunSentenceEntity ks = kunSentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KunSentence not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KunSentenceDto(
                ks.getId(),
                ks.getKanji().getId(),
                ks.getKunJpText(),
                ks.getKunKrText()
        );
    }

    @Override
    public List<KunSentenceDto> getKunSenListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KunSentenceEntity> entityList = kunSentRepo.findAll(sort);

        List<KunSentenceDto> dtoList = new ArrayList<>();
        for (KunSentenceEntity entity : entityList) {
            KunSentenceDto dto = KunSentenceDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .kunJpText(entity.getKunJpText())
                    .kunKrText(entity.getKunKrText())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // onyomi
    @Override
    public OnyomiDto readByOnyomiId(Long id) {
        OnyomiEntity oy = onRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Onyomi not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new OnyomiDto(
                oy.getId(),
                oy.getKanji().getId(),
                oy.getOnGlyph(),
                oy.getOnKana(),
                oy.getOnMeaning()
        );

    }

    @Override
    public List<OnyomiDto> getOnyomiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<OnyomiEntity> entityList = onRepo.findAll(sort);

        List<OnyomiDto> dtoList = new ArrayList<>();
        for (OnyomiEntity entity : entityList) {
            OnyomiDto dto = OnyomiDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .onGlyph(entity.getOnGlyph())
                    .onKana(entity.getOnKana())
                    .onMeaning(entity.getOnMeaning())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // onSentence
    @Override
    public OnSentenceDto readByOnSenId(Long id) {
        OnSentenceEntity os = onSentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OnSentence not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new OnSentenceDto(
                os.getId(),
                os.getKanji().getId(),
                os.getOnJpText(),
                os.getOnKrText()
        );
    }

    @Override
    public List<OnSentenceDto> getOnSenListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<OnSentenceEntity> entityList = onSentRepo.findAll(sort);

        List<OnSentenceDto> dtoList = new ArrayList<>();
        for (OnSentenceEntity entity : entityList) {
            OnSentenceDto dto = OnSentenceDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .onJpText(entity.getOnJpText())
                    .onKrText(entity.getOnKrText())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // user
    @Override
    public boolean idCheck(String searchId) {
        return !userRepo.existsByLoginId(searchId);
    }

    @Override
    public UserDto join(UserDto user) {

        if (userRepo.existsByLoginId(user.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserEntity entity = UserEntity.builder()
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();

        userRepo.save(entity);
        return user;
    }

    /**
     // 현재는 필요 없음. (아이디 조회 로직)
    @Override
    public UserDto getMember(String loginId) {
        UserEntity entity = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException(
                        loginId + " : 아이디가 없습니다."));
        UserDto dto = UserDto.builder()
                .loginId(entity.getLoginId())
                .nickname(entity.getNickname())
                .role(entity.getRole())
                .build();
        return dto;
    }
    */

    @Transactional
    public void updateLoginSuccess(UserEntity user) {
        LocalDate today = LocalDate.now();

        if (user.getLastLoginAt() != null) {
            LocalDate lastLoginDate = user.getLastLoginAt().toLocalDate();

            // 어제 로그인했다면 streakDays +1
            if (lastLoginDate.plusDays(1).equals(today)) {
                user.setStreakDays(user.getStreakDays() + 1);
            }
            // 오늘 이미 로그인했으면 streakDays 유지
            else if (lastLoginDate.equals(today)) {
                // 아무 처리 안 함
            }
            // 며칠 건너뛰었을 경우 streak 리셋
            else {
                user.setStreakDays(1);
            }
        } else {
            // 첫 로그인
            user.setStreakDays(1);
        }

        // 마지막 로그인 시간 갱신
        user.setLastLoginAt(LocalDateTime.now());
        userRepo.save(user);
    }
    // save
}
