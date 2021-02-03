package com.springwebsite.Mapper;

import com.springwebsite.Member.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("select id, password, role " +
            "from member_table " +
            "where id=#{id}")
    Member findById(String id);

    @Insert("Insert into member_table (member_idx, id, password, name, role) " +
            "values (member_seq.nextval, #{id}, #{password}, #{name}, #{role})")
    void save(Member newMember);
}
