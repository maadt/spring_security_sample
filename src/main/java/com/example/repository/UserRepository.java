package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// JpaRepository<User, Integer>：ジェネリクスに<テーブル名, 主キーの型>
    public User findByEmail(String email);
    // findByEmail()：引数に一致するユーザーエンティティをデータベースから取得する
    // String email：emailカラムを使ったクエリを自動的に生成
}

//エンティティ：データを表現し、操作するための要素の一つで、データベース内のテーブルの行（レコード）と関連付けられることが一般的

