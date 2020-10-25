package com.baizhi.service;

import com.baizhi.dao.UserFileDao;
import com.baizhi.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileDao userFileDao;

    @Override
    public List<UserFile> findByUserId(Integer id) {
        return userFileDao.findByUserId(id);
    }

    @Override
    public void save(UserFile userFile) {
        String isImg = userFile.getType().startsWith("image")?"是":"否";
        userFile.setIsImg(isImg);

        userFile.setDowncounts(0);
        userFile.setUploadTime(new Date());

        userFileDao.save(userFile);
    }

    @Override
    public UserFile findByUserFileId(Integer id) {
        return userFileDao.findByUserFileId(id);
    }

    @Override
    public void update(UserFile userFile) {
        userFileDao.update(userFile);
    }

    @Override
    public void delete(Integer id) {
        userFileDao.delete(id);
    }
}
