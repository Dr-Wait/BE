//package com.DrWait.reference;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//import java.util.Optional;
//
//@org.springframework.stereotype.Service
//@RequiredArgsConstructor
//public class Service {
//
//    private final TeamRepository teamRepository;
//    private final MemberRepository memberRepository;
//    @Transactional
//    public void deleteByName(
//            String name
//    ){
//        teamRepository.deleteByName(name);
//    }
//
//    public TeamEntity findByName(String name) {
//        return teamRepository.findByName(name);
//    }
//
//    @Transactional
//    public TeamEntity updateByName(TeamDto teamDto, TeamEntity teamEntity) {
//
//        teamEntity.setName(teamDto.getName());
//        teamEntity.setPm(teamDto.getPm());
//        teamEntity.setMember1(teamDto.getMember1());
//        teamEntity.setMember2(teamDto.getMember2());
//        teamEntity.setMember3(teamDto.getMember3());
//
//        return teamRepository.save(teamEntity);
//    }
//
//
//    public Optional<MemberEntity> findById(Long id) {
//
//        return memberRepository.findById(id);
//    }
//
//    public List<MemberEntity> findAllByName(String name) {
//
//        return memberRepository.findAllByName(name);
//    }
//
//    public Boolean existsByName (String name) {
//        return memberRepository.existsByName(name);
//    }
//
//    public Long countByCountry (String country) {
//        return memberRepository.countByCountry(country);
//    }
//
//    @Transactional
//    public void deleteById (Long id) {
//        memberRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Long removeByName (String name) {
//        return memberRepository.removeByName(name);
//    }
//
//    public List<MemberEntity> findFirst5ByCountry (String country) {
//        return memberRepository.findFirst5ByCountry(country);
//    }
//
//    public List<MemberEntity> findTop3ByCountry (String country) {
//        return memberRepository.findTop3ByCountry(country);
//    }
//
//    public List<MemberEntity> findByCountryIsNot (String country) {
//        return memberRepository.findByCountryIsNot(country);
//    }
//
//    public List<MemberEntity> findByAddressIsSpace () {
//        return memberRepository.findByAddressIsNullOrAddress("");
//    }
//
//    public List<MemberEntity> findByCountryAndName(String country, String name) {
//        return memberRepository.findByCountryAndName(country, name);
//    }
//
//    public List<MemberEntity> findByAddressStartsWith(String address) {
//        return memberRepository.findByAddressStartsWith(address);
//    }
//
//    public List<MemberEntity> findByAddressContains(String address) {
//        return memberRepository.findByAddressContains(address);
//    }
//
//    public List<MemberEntity> findByCountryOrderByNameDesc(String country) {
//        return memberRepository.findByCountryOrderByNameDesc(country);
//    }
//
//
//
//
//}
