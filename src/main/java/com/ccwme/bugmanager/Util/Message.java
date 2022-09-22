package com.ccwme.bugmanager.Util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private String msg;
    private Integer code;
    private Object res;
    public static Message success(Object res){
        Message message = new Message();
        message.setCode(200);
        message.setRes(res);
        message.setMsg("成功");
        return message;
    }
    public static Message fail(){
        Message message = new Message();
        message.setCode(400);
        message.setRes("");
        message.setMsg("失败");
        return message;
    }
    public static Message fail(Object msg){
        Message message = new Message();
        message.setCode(400);
        message.setRes(msg);
        message.setMsg("失败");
        return message;
    }
}
