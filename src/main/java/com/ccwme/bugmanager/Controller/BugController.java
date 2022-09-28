package com.ccwme.bugmanager.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ccwme.bugmanager.Bean.Bug;
import com.ccwme.bugmanager.Service.Bug.BugService;
import com.ccwme.bugmanager.Util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class BugController {
    BugController(){
        String path="/";
        String sysname = System.getProperty("os.name");
        if(sysname.substring(0,3).equals("Win")){
            path="\\";
        }
        this.path=path;
    }
    static String path;
    @Value("${filespath}")
    String filepath;
    @Autowired
    BugService bugService;
    @PostMapping("/getBugList")
    @ResponseBody
    @CrossOrigin
    public Message getBugList(String type,String page,String pageSize){
        QueryWrapper<Bug> bugQueryWrapper = new QueryWrapper();
        bugQueryWrapper.eq("type",type);
        bugQueryWrapper.orderByDesc("startdate");
        long count = bugService.count(bugQueryWrapper);
        int i = (Integer.valueOf(page)-1) * Integer.valueOf(pageSize);
        bugQueryWrapper.last("limit "+i+", "+pageSize);
        List<Bug> list = bugService.list(bugQueryWrapper);
        JSONObject alldata = new JSONObject();
        JSONObject pagejsondata = new JSONObject();
        pagejsondata.put("total",count);
        alldata.put("data", list);
        alldata.put("pagedata", pagejsondata);

        return Message.success(alldata);
    }
    @PostMapping("/addBug")
    @ResponseBody
    @CrossOrigin
    public Message addBug(@RequestBody Bug bug){
        System.out.println(bug);
        JSONArray images = JSON.parseArray(bug.getImages());
        for (Object image : images) {
            File file = new File(filepath + "BugImages" + path + (String) image);
            System.out.println(image);
            if(!file.exists()){
                //如果有一项不存在就删除所有的image
                for (Object image1 : images) {
                    File file1 = new File(filepath + "BugImages" + path + (String) image1);
                    file1.delete();
                }
                return Message.fail("files not match");
            }
        }
        boolean save = bugService.save(bug);
        if(save){
            return Message.success("add success");
        }
        for (Object image : images) {
            File file = new File(filepath + "BugImages" + path + (String) image + ".jpg");
            file.delete();
        }
        return Message.fail("add fail");
    }
    @PostMapping("/addColImage")
    @ResponseBody
    @CrossOrigin
    public Message addColImage(@RequestBody Map req){
        String images =(String) req.get("images");
        String col =(String) req.get("col");
        Integer id =(Integer) req.get("id");
        JSONArray imagesjson = JSON.parseArray(images);
        for (Object image : imagesjson) {
            File file = new File(filepath + "BugImages" + path + image);
            if (!file.exists()) {
                for (Object image1 : imagesjson) {
                    File file1 = new File(filepath + "BugImages" + path + (String) image1);
                    file1.delete();
                }
                return Message.fail("files not match");
            }
        }
        UpdateWrapper<Bug> bugUpdateWrapper = new UpdateWrapper<>();
        QueryWrapper<Bug> bugQueryWrapper = new QueryWrapper<>();
        bugUpdateWrapper.eq("id",id);
        bugQueryWrapper.eq("id",id);
        List<Bug> list = bugService.list(bugQueryWrapper);
        Bug bug = list.get(0);
        String images1 = bug.getImages();
        JSONArray oldimages = JSON.parseArray(images1);
        for (Object image : imagesjson) {
            oldimages.add(image);
        }
        String newimages = oldimages.toJSONString();
        bugUpdateWrapper.set(col,newimages);
        boolean update = bugService.update(bugUpdateWrapper);
        if(update){
            return Message.success("update success");
        }
        return Message.fail("update fail");
    }
    @PostMapping("/recivebugimg")
    @ResponseBody
    @CrossOrigin
    public Message recivebugimg(@RequestPart MultipartFile file,@RequestPart String filename) throws IOException {
        File file1 = new File(filepath + "BugImages" + path + filename+".jpg");
        file.transferTo(file1);
        return Message.success("recivedimg");
    }
    @PostMapping("/removeimg")
    @ResponseBody
    @CrossOrigin
    public Message removeimg(@RequestBody Map param) throws IOException {
        File file = new File(filepath + "BugImages" + path + param.get("filename")+".jpg");
        System.out.println(filepath + "BugImages" + path + param.get("filename")+".jpg");
        if (file.exists()) {
            if (file.delete()) {
                return Message.success("deleted");
            }
            return Message.fail("file delete error");
        }
        return Message.fail("file not exist");
    }
    @PostMapping("/updateColData")
    @ResponseBody
    @CrossOrigin
    public Message editTextArea(String id,String col,String data){
        UpdateWrapper<Bug> bugUpdateWrapper = new UpdateWrapper<>();
        bugUpdateWrapper.eq("id",id);
        bugUpdateWrapper.set(col,data);
        boolean update = bugService.update(bugUpdateWrapper);
        if(update){
            return Message.success("update success");
        }
        return Message.fail("update fail");
    }
    @PostMapping("/deleteColImgData")
    @ResponseBody
    @CrossOrigin
    public Message deleteColImgData(String id,String col,String data){
        UpdateWrapper<Bug> bugUpdateWrapper = new UpdateWrapper<>();
        QueryWrapper<Bug> bugQueryWrapper = new QueryWrapper<>();
        bugUpdateWrapper.eq("id",id);
        bugQueryWrapper.eq("id",id);
        List<Bug> list = bugService.list(bugQueryWrapper);
        Bug bug = list.get(0);
        String images1 = bug.getImages();
        ArrayList newimages=new ArrayList();
        for (Object image : JSON.parseArray(images1)) {
            System.out.println(image);
            if(!image.equals(data)){
                newimages.add(image);
            }
        }
        String newimagelist = JSON.toJSONString(newimages);
        bugUpdateWrapper.set(col,newimagelist);
        File file = new File(filepath + "BugImages" + path + data);
        if (file.exists()) {
            if (file.delete()) {
                boolean update = bugService.update(bugUpdateWrapper);
                if(update){
                    return Message.success("delete success");
                }
                return Message.fail("file deleted,data update fail");
            }
            return Message.fail("delete error");
        }
        boolean update = bugService.update(bugUpdateWrapper);
        if(update){
            return Message.success("delete success");
        }
        return Message.fail("file not exist,data update fail");
    }
}
