package com.baizhi.dao;

import com.baizhi.entity.UserFile;

import java.util.List;

public interface UserFileDao {
    //根据登录的用户查询
    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findByUserFileId(Integer id);

    void update(UserFile userFile);

    void delete(Integer id);
}
