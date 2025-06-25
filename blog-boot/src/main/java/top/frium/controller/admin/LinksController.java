package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.dto.LinkDTO;
import top.frium.service.LinkService;

import java.util.List;

/**
 * @date 2025-03-18 14:44:31
 * @description
 */
@Api("友链管理")
@RestController
@RequestMapping("/links")
@PreAuthorize("hasAuthority('superAdmin')")
public class LinksController {
    @Autowired
    LinkService linkService;

    @ApiOperation("添加友链")
    @PostMapping("/addLink")
    public R<?> addLink(@RequestBody LinkDTO linkDTO) {
        linkService.addLink(linkDTO);
        return R.success();
    }

    @ApiOperation("删除友链")
    @PostMapping("/deleteLink")
    public R<?> deleteLink(@RequestBody List<Long> linkIds) {
        linkService.deleteLink(linkIds);
        return R.success();
    }

    @ApiOperation("修改友链")
    @PostMapping("/updateLink")
    public R<?> updateLink(@RequestBody LinkDTO linkDTO) {
        linkService.updateLink(linkDTO);
        return R.success();
    }

    @ApiOperation("获取所有友链")
    @GetMapping("/getLinks")
    public R<?> getLinks() {
        return R.success(linkService.getLinks());
    }

    @ApiOperation("根据名称搜索友链")
    @PostMapping("/searchLinksByName")
    public R<?> searchLinksByName( String searchInfo) {
        return R.success(linkService.searchLinksByName(searchInfo));
    }
}
