package com.ccwme.bugmanager.Bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName(value = "bugs")
public class Bug {
    @TableId(type = IdType.AUTO)
    Integer id;
    String bianhao;
    String state;
    String shuxing;
    String bankuai;
    String content;
    String images;
    String timerequire;
    String reasonandfunc;
    String proposer;
    String filler;
    String startdate;
    String planoverdate;
    String devdirector;
    String devcompletetime;
    String counterpart;
    String tester;
    String upuattime;
    String upprodtime;
    String vertifytime;
    String remarks;
    String testvertifyimgs;
    String testuattime;
    String prodtestimgs;
    String prodtestdate;
    String requirereviewdate;
    String type;
    String del;
    String addtime;
    String state2;
}
