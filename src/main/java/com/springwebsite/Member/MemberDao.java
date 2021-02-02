package com.springwebsite.Member;

import com.springwebsite.Mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final MemberMapper memberMapper;

    public void save(Member newMember) {
        memberMapper.save(newMember);
    }

    public Member findById(String id) {
        return memberMapper.findById(id);
    }
}
