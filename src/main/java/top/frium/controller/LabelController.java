package top.frium.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.frium.common.R;
import top.frium.pojo.entity.Label;
import top.frium.service.LabelService;

import java.util.List;
import java.util.Map;

/**
 *
 * @date 2024-06-20 15:12:58
 * @description
 */
@Validated
@RestController("LabelController")
@RequestMapping("/label")
public class LabelController {
    @Autowired
    LabelService labelService;

    @ApiOperation("添加标签")
    @PostMapping("/admin/addLabel")
    @PreAuthorize("hasAnyAuthority('admin')")
    public R<?> addLabel(String labelName) {
        labelService.addLabel(labelName);
        return R.success();
    }

    @ApiOperation("删除标签")
    @PostMapping("/admin/deleteLabel")
    @PreAuthorize("hasAnyAuthority('admin')")
    public R<?> deleteLabel(Label label) {
        labelService.deleteLabel(label);
        return R.success();
    }

    @ApiOperation("查看所有标签")
    @GetMapping("/getAllLabel")
    public R<List<String>> getAllLabel() {
        return R.success(labelService.getAllLabel());
    }

    @ApiOperation("查看所有文章的所有标签")
    @GetMapping("/getArticlesAllLabel")
    public R<Map<String,Integer>> getArticlesAllLabel() {
        return R.success(labelService.getArticlesAllLabel());
    }
}
