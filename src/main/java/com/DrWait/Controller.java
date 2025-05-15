package com.DrWait;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ch9")
public class Controller {

    private final Service c9Service;

    /***
     * http://localhost:8080/ch9/delete/team/7조
     * Talend API Tester 에서 DELETE API 로 삭제요청
     */
    @DeleteMapping(path = "/delete/team/{name}")
    public ResponseEntity<Void> deleteTeam(
            @PathVariable String name
    ) {
        c9Service.deleteByName(name);
        return ResponseEntity.noContent().build();
    }


    /***
     * http://localhost:8080/ch9/update
     * Talend API Tester 에서 JSON 으로 수정요청
     */
    @PostMapping(path = "/update")
    public ResponseEntity<TeamEntity> updateTeam(
            @RequestBody TeamDto teamDto
    ) {
        TeamEntity teamEntity = c9Service.findByName(teamDto.getName());

        if (teamEntity == null) {
            return ResponseEntity.notFound().build();
        } else {
            TeamEntity teamEntityNew = c9Service.updateByName(teamDto, teamEntity);
            return ResponseEntity.ok(teamEntityNew);
        }
    }


    /***
     * http://localhost:8080/ch9/member/findById/1001
     * GET 으로 id 가 1000 인 레코드를 가져온다
     */

    @GetMapping(path = "/member/findById/{id}")
    public ResponseEntity<?> getEntity(
            @PathVariable(name = "id") String sId
    ) {
        try {
            Long id = Long.parseLong(sId);

            Optional<MemberEntity> result = c9Service.findById(id);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID " + sId + "는 존재하지 않습니다.");
            } else {
                return ResponseEntity.ok(result.get());
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID값 " + sId + "이 잘 못 되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID " + sId + "는 존재하지 않네요.");
        }
    }


    @GetMapping(path = "/member/findByName/{name}")
    public List<?> findAllByName(
            @PathVariable String name
    ) {
            return c9Service.findAllByName(name);
    }


    @GetMapping(path="/member/existsByName/{name}")
    public Boolean existsByName(
            @PathVariable String name
    ) {
        return c9Service.existsByName(name);
    }


    @GetMapping(path="/member/countByCountry/{country}")
    public Long countByCountry(
            @PathVariable String country
    ) {
        return c9Service.countByCountry(country);
    }


    @GetMapping(path="/member/deleteById/{id}")
    public void deleteById(
            @PathVariable Long id
    ) {
        c9Service.deleteById(id);
    }


    @GetMapping(path="/member/removeByName/{name}")
    public Long removeByName(
            @PathVariable String name
    ) {
        return c9Service.removeByName(name);
    }


    @GetMapping(path="/member/findFirst5ByCountry/{country}")
    public List<MemberEntity> findFirst5ByCountry(
            @PathVariable String country
    ) {
        return c9Service.findFirst5ByCountry(country);
    }


    @GetMapping(path="/member/findTop3ByCountry/{country}")
    public List<MemberEntity> findTop3ByCountry(
            @PathVariable String country
    ) {
        return c9Service.findTop3ByCountry(country);
    }


    @GetMapping(path="/member/findByCountryIsNot/{country}")
    public List<MemberEntity> findByCountryIsNot(
            @PathVariable String country
    ) {
        return c9Service.findByCountryIsNot(country);
    }


    @GetMapping(path="/member/findByAddressIsSpace")
    public List<MemberEntity> findByAddressIsSpace() {
        return c9Service.findByAddressIsSpace();
    }


    @GetMapping(path="/member/findByCountryAndName/{country}/{name}")
    public List<MemberEntity> findByCountryAndName(
            @PathVariable String country,
            @PathVariable String name
    ) {
        return c9Service.findByCountryAndName(country, name);
    }


    @GetMapping(path="/member/findByAddressStartsWith/{address}")
    public List<MemberEntity> findByAddressStartsWith(
            @PathVariable String address
    ) {
        return c9Service.findByAddressStartsWith(address);
    }


    @GetMapping(path="/member/findByAddressContains/{address}")
    public List<MemberEntity> findByAddressContains(
            @PathVariable String address
    ) {
        return c9Service.findByAddressContains(address);
    }



    @GetMapping(path="/member/findByCountryOrderByNameDesc/{country}")
    public List<MemberEntity> findByCountryOrderByNameDesc(
            @PathVariable String country
    ) {
        return c9Service.findByCountryOrderByNameDesc(country);
    }

}
