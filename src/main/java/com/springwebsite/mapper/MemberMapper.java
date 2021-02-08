package com.springwebsite.mapper;

import com.springwebsite.member.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("select member_idx, id, password, role, name " +
            "from member_table " +
            "where id=#{id}")
    Member findById(String id);

    @Insert("Insert into member_table (member_idx, id, password, name, role) " +
            "values (member_seq.nextval, #{id}, #{password}, #{name}, #{role})")
    void save(Member newMember);

    @Delete("delete from member_table")
    void deleteAll();

    @Select("select id from member_table where id = #{id}")
    String existById(String id);
}
