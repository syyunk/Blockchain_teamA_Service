package com.ateam.popserver.persistence;

import com.ateam.popserver.model.Auth;
import com.ateam.popserver.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    @Query("select a.aname from Auth a  where a.member=:member")
    List<String> getAuthList(@Param("member") Member member);
}
