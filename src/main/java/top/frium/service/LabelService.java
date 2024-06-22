package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.entity.Label;

import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024-06-20 15:18:12
 * @description
 */
public interface LabelService extends IService<Label> {

    void addLabel(String name);
    void deleteLabel(Label label);

    List<String> getAllLabel();

    Map<String, Integer> getArticlesAllLabel();
}
