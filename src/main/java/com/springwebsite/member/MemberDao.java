package com.springwebsite.member;

import com.springwebsite.mapper.MemberMapper;
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

    public void deleteAll() {
        memberMapper.deleteAll();
    }

    public int findMemberIndexById(String id) {
        return memberMapper.findMemberIndexById(id);
    }
}

