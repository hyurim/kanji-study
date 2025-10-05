package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.dto.KunSentenceDto;
import com.hyuri.kanji_study.dto.KunyomiDto;
import com.hyuri.kanji_study.dto.OnSentenceDto;
import com.hyuri.kanji_study.dto.OnyomiDto;
import com.hyuri.kanji_study.dto.SaveDto;
import com.hyuri.kanji_study.dto.UserDto;

import java.util.List;

public interface KanjiService {
    // kanjiDto
    KanjiDto readByKanjiId(Long id);
    List<KanjiDto> getKanjiListAll();

    // kunyomiDto
    KunyomiDto readByKunyomiId(Long id);
    List<KunyomiDto> getKunyomiListAll();

    // kunSentenceDto
    KunSentenceDto readByKunSenId(Long id);
    List<KunSentenceDto> getKunSenListAll();

    // onyomiDto
    OnyomiDto readByOnyomiId(Long id);
    List<OnyomiDto> getOnyomiListAll();

    // onSentenceDto
    OnSentenceDto readByOnSenId(Long id);
    List<OnSentenceDto> getOnSenListAll();

    //user
    UserDto join(UserDto user);
    boolean idCheck(String searchId);
//    UserDto getMember(String loginId); // 조회 로직

    //save
    SaveDto addSave(String loginId, Long kanjiId);
    List<SaveDto> getSaveList(String loginId);
    void removeSave(String loginId, Long kanjiId);

}
