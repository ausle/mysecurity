package com.asule.security.controller;


import com.asule.security.entity.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RequestMapping("/file")
@Controller
public class FileController {

    private String folder="E:\\springstudy\\startBySecurity\\asule-security-demo\\src\\main\\resources";

    @ResponseBody
    @PostMapping
    public FileInfo uploadFile(@RequestParam("uploadFile")MultipartFile file) throws IOException {
        File myFile=new File(folder,new Date().getTime()+".txt");
        file.transferTo(myFile);
        return new FileInfo(myFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void downloadFile(@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response){
        //java 7对异常的写法。
        try (
                InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
                OutputStream outputStream=response.getOutputStream()
        )
        {
            response.setContentType("application/x-download");
            //指定文件名
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
