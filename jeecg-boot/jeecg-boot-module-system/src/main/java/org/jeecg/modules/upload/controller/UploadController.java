package org.jeecg.modules.upload.controller;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Zzzhangyxi
 * @Description
 * @date 2021/7/9
 */
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result<?> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("上传失败");
        }
        String filename = file.getOriginalFilename();
        File filepath = new File("/temp");
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        String fullName = filepath + "/" + filename;
        File dest = new File(fullName);

        try {
            dest.createNewFile();
            file.transferTo(dest);
            return Result.OK("上传成功", fullName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败，请稍后重试");
        }

    }

}
