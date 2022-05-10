package com.example.demo.common.support;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基药解析器
 *
 * @author wujl2
 * @date 2021/03/11 10:58
 */
@Slf4j
@Component
public class EssDrugResolver extends AbstractDataIsuExecuteStrategy<User> {

    @Autowired
    private UserService bidprcuEssdrugBO;

    @Override
    public void executeIssue(List sourceList, String isuTaskId) {

        bidprcuEssdrugBO.saveOrUpdateBatch(sourceList);
    }

    @Override
    public String supports() {
        return "user";
    }

    @Override
    public List<User> executeQuery(String dataBizId, String provCode) {
        return null;
        //return bidprcuEssdrugBO.queryByIsuTaskId(dataBizId);
    }
}
