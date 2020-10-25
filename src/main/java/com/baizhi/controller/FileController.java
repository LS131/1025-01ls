package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.entity.UserFile;
import com.baizhi.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("file")
@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserFileService userFileService;

    /**
     * 更新下载次数
     * @param session
     * @return
     */

    @GetMapping("findAllJSON")
    @ResponseBody
    public List<UserFile> findAllJSON(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());



        return userFiles;
    }

    /**
     * 删除文件信息
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) throws FileNotFoundException {
        //根据id文件信息
        UserFile userFile = userFileService.findByUserFileId(id);
        //删除文件
        String realPath = ResourceUtils.getURL("classpath:").getPath()+"/static"+userFile.getPath();
        File file = new File(realPath,userFile.getNewFileName());
        if(file.exists())file.delete();//立即删除
        //删除数据库信息
        userFileService.delete(id);



        return "redirect:/file/showAll";
    }


    /**
     *  展示所有文件信息
     * @param model
     * @param session
     * @return
     */
    @GetMapping("showAll")
    public String findAll(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        List<UserFile> userFiles = userFileService.findByUserId(user.getId());
        model.addAttribute("files",userFiles);
        return "showAll";
    }

    /**
     *  上传文件的处理 存入数据库
     * @param aaa
     * @param session
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    public String upload(MultipartFile aaa,HttpSession session) throws IOException {
        System.out.println("upload进来啦");
        User user = (User) session.getAttribute("user");
        //获取文件原始名称
        String oldFileName = aaa.getOriginalFilename();
        System.out.println("文件原始名称:"+oldFileName);
        //获取文件后缀
        String extension = "."+FilenameUtils.getExtension(aaa.getOriginalFilename());
        System.out.println("后缀:"+extension);
        //生成新的文件名称
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString().replace("-","")+extension;

        //文件的大小
        long size = aaa.getSize();

        //文件的类型
        String type = aaa.getContentType();

        //根据日期生成目录
        String realpath = ResourceUtils.getURL("classpath:").getPath()+"static/files";
        System.out.println("realpath:"+realpath);
        //新的文件夹
        String dateFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String dateDirPath = realpath+"/"+ dateFormat;
        System.out.println("dateDirPath:"+dateDirPath);//文件夹路经
        File dateDir = new File(dateDirPath);
        boolean flag = true;
        if(!dateDir.exists()){
            flag = dateDir.mkdirs();
            System.out.println("文件不存在"+flag);
        }
        //处理文件上传
        aaa.transferTo(new File(dateDir,newFileName));

        //放入数据库
        UserFile userFile = new UserFile();
        userFile.setOldFileName(oldFileName);
        userFile.setNewFileName(newFileName);
        userFile.setExt(extension);
        userFile.setSize(String.valueOf(size));
        userFile.setType(type);
        userFile.setPath("/files/"+dateFormat);
        userFile.setUserId(user.getId());

        userFileService.save(userFile);

        return "redirect:/file/showAll";
    }

    /**
     * 文件的下载与在线打开
     * @param openStyle  为null下载  不为null在线下载
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("download")
    public void download(String openStyle,Integer id, HttpServletResponse response) throws IOException {
        //获取打开方式
        openStyle = openStyle==null?"attachment":openStyle;

        //获取文件信息
        UserFile userFile = userFileService.findByUserFileId(id);
        logger.info(userFile.toString());
        //更新下载次数
        if("attachment".equals(openStyle)){
            userFile.setDowncounts(userFile.getDowncounts()+1);
            userFileService.update(userFile);
        }
        String realpath = ResourceUtils.getURL("classpath:").getPath()+"static/";
        logger.info(realpath);
        File file = new File(realpath+userFile.getPath()+"/"+userFile.getNewFileName());

        FileInputStream fis = new FileInputStream(file);

        //附件下载
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(userFile.getOldFileName(),"UTF-8"));//重新编码 防止中文乱码
        //获取响应式输出流
        ServletOutputStream fos = response.getOutputStream();

        IOUtils.copy(fis,fos);

        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(fos);

        logger.info("下载完成");
    }


}
