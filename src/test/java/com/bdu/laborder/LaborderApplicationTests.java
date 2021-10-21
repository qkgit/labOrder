package com.bdu.laborder;

import com.bdu.laborder.entity.SDict;
import com.bdu.laborder.mapper.SDictMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class LaborderApplicationTests {

    @Autowired
    SDictMapper sDictMapper;
    @Test
    void contextLoads() {
        SDict sDict = new SDict();
        sDict.setCode("root");
        List<SDict> sDicts = sDictMapper.selectSDictList(sDict);
        System.out.println(sDicts);
    }


}
