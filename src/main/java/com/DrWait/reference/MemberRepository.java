//package com.DrWait.reference;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface MemberRepository extends JpaRepository <MemberEntity, Long> {
//
//    List<MemberEntity> findAllByName(String name);
//    Boolean existsByName(String name);
//
//    Long countByCountry(String country);
//
//    void deleteById(Long id);
//
//    Long removeByName(String name);
//
//    List<MemberEntity> findFirst5ByCountry(String country);
//
//    List<MemberEntity> findTop3ByCountry(String country);
//
//    List<MemberEntity> findByCountryIsNot(String country);
//
//    List<MemberEntity> findByAddressIsNullOrAddress(String address);
//
//    List<MemberEntity> findByCountryAndName(String country, String name);
//
//    List<MemberEntity> findByAddressStartsWith(String address);
//
//    List<MemberEntity> findByAddressContains(String address);
//
//    List<MemberEntity> findByCountryOrderByNameDesc(String country);
//
//
//
//}
