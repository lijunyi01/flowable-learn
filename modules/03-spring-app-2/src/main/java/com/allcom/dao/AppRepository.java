package com.allcom.dao;

import com.allcom.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AppRepository {

    @Select("select * from person")
    List<Person> findAllPerson();

    @Select("select * from person where username = #{username}")
    Person findByUsername(@Param("username") String username);

    @Insert("insert into person (" +
            "  username,"+
            "  firstname,"+
            "  lastname,"+
            "  birthdate"+
            ") values(" +
            "  #{username},"+
            "  #{firstname},"+
            "  #{lastname},"+
            "  #{birthdate}"+
            ")")
    int addPerson(Person person);
}
