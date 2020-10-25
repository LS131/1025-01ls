package com.baizhi.service;

import com.baizhi.entity.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserFileService {

    List<UserFile> findByUserId(Integer id);

    void save(UserFile userFile);

    UserFile findByUserFileId(Integer id);

    void update(UserFile userFile);

    void delete(Integer id);

}
