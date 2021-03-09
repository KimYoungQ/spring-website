package com.springwebsite.mapper;

import com.springwebsite.member.Member;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {

    @Select("select member_idx, id, password, role, name, email, emailCheckToken, emailVerified " +
            "from member_table " +
            "where id=#{id}")
    Member findById(String id);

    @Select("select member_idx, id, password, role, name, email, emailCheckToken, emailVerified " +
            "from member_table " +
            "where email=#{email}")
    Member findByEmail(String email);

    @Insert("Insert into member_table (member_idx, id, password, name, role, email, emailCheckToken, emailVerified) " +
            "values (NEXTVAL(member_seq), #{id}, #{password}, #{name}, #{role}, #{email}, #{emailCheckToken}, #{emailVerified})")
    void save(Member newMember);

    @Delete("delete from member_table")
    void deleteAll();

    @Select("select id from member_table where id = #{id}")
    String existById(String id);

    @Select("select email from member_table where email = #{email}")
    String existByEmail(String email);

    @Select("select id from member_table where member_idx = #{content_idx}")
    String findWriterNameByContentWriterIndex(int content_idx);

    @Update("update member_table " +
            "set emailVerified = #{emailVerified}")
    void updateEmailVerified(String emailVerified);

    @Select("select emailVerified " +
            "from member_table " +
            "where id=#{id}")
    Boolean isEmailVerifiedById(String id);
}
