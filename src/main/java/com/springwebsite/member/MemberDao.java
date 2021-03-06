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

    public Member findByEmail(String email) {
        return memberMapper.findByEmail(email);
    }

    public void deleteAll() {
        memberMapper.deleteAll();
    }

    public String existById(String id) {
        return memberMapper.existById(id);
    }

    public String existByEmail(String email) {
        return memberMapper.existByEmail(email);
    }

    public String findWriterNameByContentWriterIndex(int content_idx) {
        return memberMapper.findWriterNameByContentWriterIndex(content_idx);
    }

    public void updateEmailVerified(String emailVerified) {
        memberMapper.updateEmailVerified(emailVerified);
    }

    public Boolean isEmailVerifiedById(String id) {
        return memberMapper.isEmailVerifiedById(id);
    }
}

