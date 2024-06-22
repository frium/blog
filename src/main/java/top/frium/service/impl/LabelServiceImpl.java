package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.LabelMapper;
import top.frium.pojo.entity.Label;
import top.frium.service.LabelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024-06-20 15:18:42
 * @description
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {
    @Autowired
    LabelMapper labelMapper;

    @Override
    public void addLabel(String name) {
        if (lambdaQuery().eq(Label::getName, name).one() != null) throw new MyException(StatusCodeEnum.LABEL_EXIST);
        Label label = new Label(0L, name);
        save(label);
    }

    @Override
    public void deleteLabel(Label label) {
        if (lambdaQuery().eq(Label::getName, label.getName()).one() == null)
            throw new MyException(StatusCodeEnum.LABEL_NOT_EXIST);
        removeById(label.getId());
    }

    @Override
    public List<String> getAllLabel() {
        return labelMapper.getAllLabel();
    }

    @Override
    public Map<String, Integer> getArticlesAllLabel() {
        List<String> labelsNumber = labelMapper.getLabelsNumber();
        Map<String,Integer> map=new HashMap<>();
        for (String name:labelsNumber){
            Integer number = map.getOrDefault(name, 0);
            map.put(name,number+1);
        }
        return map;
    }
}
