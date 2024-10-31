package com.example.LesChef.Repository;

import com.example.LesChef.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Override
    Optional<Customer> findById(String id);

    // 아이디 찾기
    @Query(value = "select id from Customer where name = :name and tel = :tel ", nativeQuery = true)
    String findId(@Param("name") String name, @Param("tel") String tel);

    //비밀번호 찾기, 수정
    @Query(value = "select * from Customer where name = :name and tel = :tel and id = :id", nativeQuery = true)
    Customer findpw(@Param("name") String name, @Param("tel") String tel, @Param("id") String id);
}
